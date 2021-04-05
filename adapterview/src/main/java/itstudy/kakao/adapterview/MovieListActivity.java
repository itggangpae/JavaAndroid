package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MovieListActivity extends AppCompatActivity {
    //문자열을 저장할 프로퍼티
    String json;
    //데이터 목록을 저장할 리스트
    List<Movie> movieList;
    //데이터 개수를 저장할 프로퍼티
    int count;
    //스레드 변수
    MovieThread th;

    //ListView에 출력하기 위한 Adapter
    //ArrayAdapter<Movie> movieAdapter;
    MovieAdapter movieAdapter;

    ListView listview;
    ProgressBar downloadview;

    Handler handler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            movieAdapter.notifyDataSetChanged();
            downloadview.setVisibility(View.GONE);
            th = null;
        }
    };

    //가장 하단에서 스크롤 했는지 확인하기 위한 프로퍼티
    boolean lastitemVisibleFlag;
    //페이지 번호를 저장하기 위한 프로퍼티
    int pageno = 1;


    class MovieThread extends Thread{
        public void run() {
            try {
                //다운로드 받을 주소 생성
                //java.net.URL url = new URL("http://cyberadam.cafe24.com/movie/list");
                URL url = new URL("http://cyberadam.cafe24.com/movie/list?page=" + pageno);

                //연결 객체 생성
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setUseCaches(false); //캐시 사용 여부 설정
                con.setConnectTimeout(30000); //접속 시도 시간 설정

                //문자열을 다운로드 받기 위한 스트림을 생성
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                //문자열을 읽어서 저장
                while (true) {
                    String line = br.readLine();
                    if(line == null) {
                        break;
                    }
                    sb.append(line.trim());
                }
                json = sb.toString();
                //읽은 데이터 확인
                Log.e("json", json);
                //사용한 스트림과 연결 해제
                br.close();
                con.disconnect();

                //json 파싱
                if(json.trim().length() > 0){
                    JSONObject data = new JSONObject(json);
                    count = data.getInt("count");
                    JSONArray list = data.getJSONArray("list");
                    int i = 0;
                    while (i < list.length()) {
                        JSONObject item = list.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setMovieid(item.getInt("movieid"));
                        movie.setTitle(item.getString("title"));
                        movie.setSubtitle(item.getString("subtitle"));
                        movie.setGenre(item.getString("genre"));
                        movie.setRating(item.getDouble("rating"));
                        movie.setThumbnail(item.getString("thumbnail"));
                        movie.setLink(item.getString("link"));
                        //movieList.add(movie);
                        movieList.add(0, movie);
                        i = i + 1;
                    }
                    Log.e("파싱 결과 - count", count+"");
                    Log.e("파싱 결과 - movieList", movieList.toString());
                    handler.sendEmptyMessage(0);
                }

            } catch (Exception e) {
                Log.e("다운로드 실패", e.getLocalizedMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieList = new ArrayList<>();
        if(th != null) {
            return;
        }
        th = new MovieThread();
        th.start();

        listview = (ListView)findViewById(R.id.listview);
        downloadview = (ProgressBar)findViewById(R.id.downloadview);

        //movieAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieAdapter = new MovieAdapter(this, movieList, R.layout.movie_cell);

        listview.setAdapter(movieAdapter);
        listview.setDivider(new ColorDrawable(Color.RED));
        listview.setDividerHeight(3);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieList.get(position);
                String link = movie.getLink();
                Intent intent = new Intent(MovieListActivity.this, LinkActivity.class);
                intent.putExtra("link", link);
                startActivity(intent);
            }
        });


        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    pageno = pageno + 1;
                    int cnt = 10;
                    if (pageno * cnt >= count) {
                        Snackbar.make(view, "더이상의 데이터가 없습니다.", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    if(th != null) {
                        return;
                    }
                    downloadview.setVisibility(View.VISIBLE);
                    th = new MovieThread();
                    th.start();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemVisibleFlag = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount;
            }
        });

        SwipeRefreshLayout swipe_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                pageno = pageno + 1;
                int cnt = 10;
                if (pageno * cnt >= count) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "더이상의 데이터가 없습니다.", Snackbar.LENGTH_LONG).show();
                } else {
                    if (th != null) {
                    } else {
                        downloadview.setVisibility(View.VISIBLE);
                        th = new MovieThread();
                        th.start();
                        swipe_layout.setRefreshing(false);
                    }
                }
            }
        });
    }
}