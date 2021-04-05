package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ItemPostActivity extends AppCompatActivity {
    //입력 뷰
    EditText itemnameinput;
    EditText priceinput;
    EditText descriptioninput;

    //결과를 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            String message;
            switch(msg.what) {
                case 0:
                    message = (String)msg.obj;
                    break;
                case 1:
                    boolean insertResult = (Boolean)msg.obj;
                    if (insertResult == true) {
                        message = "삽입 성공";

                        itemnameinput.setText("");
                        priceinput.setText("");
                        descriptioninput.setText("");
                        //키보드 관리 객체 가져오기
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(itemnameinput.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(priceinput.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(descriptioninput.getWindowToken(), 0);
                    }
                    else {
                        message = "삽입 실패";
                    }
                    break;
                case 2:
                    boolean deleteResult = (Boolean)msg.obj;
                    if (deleteResult == true) {
                        message = "삭제 성공";
                    }
                    else {
                        message = "삽입 실패";
                    }
                    break;
                default:
                    message = "결과 알 수 없음";
                    break;
            }
            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_post);

        //디자인 한 뷰 찾아오기
        itemnameinput = (EditText)findViewById(R.id.itemnameinput);
        priceinput = (EditText)findViewById(R.id.priceinput);
        descriptioninput = (EditText)findViewById(R.id.descriptioninput);

        Button insert = (Button)findViewById(R.id.insert);
        Button delete = (Button)findViewById(R.id.delete);

        //삽입 버튼의 클릭 이벤트 핸들러
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Message message = new Message();
                //데이터 유효성 검사
                message.what = 0;
                if (itemnameinput.getText().toString().trim().length() == 0) {
                    message.obj = "이름은 비어있을 수 없습니다.";
                    handler.sendMessage(message);
                    return;
                }
                if (priceinput.getText().toString().trim().length() == 0) {
                    message.obj = "수량은 비어있을 수 없습니다.";
                    handler.sendMessage(message);
                    return;
                }
                if (descriptioninput.getText().toString().trim().length() == 0) {
                    message.obj = "설명은 비어있을 수 없습니다.";
                    handler.sendMessage(message);
                    return;
                }

                //데이터 삽입 수행
                new Thread(){
                    String json = null;

                    @Override
                    public void run() {
                        try {
                            //다운로드 받을 주소 생성
                            URL url = new URL("http://cyberadam.cafe24.com/item/insert");
                            //URL에 연결
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            //updatedate 의 파라미터 값 만들기
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                            //파일으르 제외한 파라미터 이름 만들기
                            String[] dataName = {"itemname", "price", "description", "updatedate"};

                            //파일을 제외한 파라미터 만들기
                            String[] data = {itemnameinput.getText().toString(),
                                    priceinput.getText().toString(),
                                    descriptioninput.getText().toString(),
                                    sdf.format(date)};
                            String lineEnd = "\r\n";
                            String boundary = UUID.randomUUID().toString();

                            // 연결 객체 옵션 설정
                            con.setRequestMethod("POST");
                            con.setConnectTimeout(10000);
                            con.setUseCaches(false);

                            //파일 업로드가 있는 경우 설정
                            con.setRequestProperty("ENCTYPE", "multipart/form-data");
                            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                            //파라미터 생성
                            String delimiter = "--" + boundary + lineEnd;
                            StringBuilder postDataBuilder = new StringBuilder();
                            for (int i = 0; i<data.length; i++) {
                                postDataBuilder.append(delimiter);
                                postDataBuilder.append(
                                        "Content-Disposition: form-data; name=\"" + dataName[i] + "\"" + lineEnd + lineEnd + data[i] + lineEnd
                                );
                            }

                            //업로드할 파일이름 생성
                            String fileName = "ritchie.jpeg";

                            // 파일이 존재할 때에만 pictureurl 파라미터를 생성
                            if (fileName != null) {
                                postDataBuilder.append(delimiter);
                                postDataBuilder.append("Content-Disposition: form-data; name=\"pictureurl\";filename=\"" + fileName + "\"" + lineEnd);
                            }

                            //파라미터 전송
                            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
                            ds.write(postDataBuilder.toString().getBytes());

                            //파일 전송과 body 종료
                            //파일이 있는 경우에는 파일을 전송
                            if (fileName != null) {
                                ds.writeBytes(lineEnd);

                                //raw 디렉토리의 내용 읽기
                                InputStream fres = getResources().openRawResource(R.raw.ritchie);
                                byte [] buffer = new byte[fres.available()];
                                int length = -1;
                                while (true) {
                                    length = fres.read(buffer);
                                    if(length <= 0){
                                        break;
                                    }
                                    ds.write(buffer, 0, length);
                                }


                                //drawable 디렉토리의 파일 전송
                                /*
                                Drawable ritchie = getResources().getDrawable(R.drawable.ritchie, null);
                                Bitmap bitmap = ((BitmapDrawable)ritchie).getBitmap();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] buffer  = stream.toByteArray();
                                ds.write(buffer, 0, buffer.length);
                                */

                                ds.writeBytes(lineEnd);
                                ds.writeBytes(lineEnd);
                                ds.writeBytes("--" + boundary + "--" + lineEnd);
                                // requestbody end

                                //fres.close()
                            } else {
                                ds.writeBytes(lineEnd);
                                ds.writeBytes("--" + boundary + "--" + lineEnd);
                                // requestbody end
                            }
                            ds.flush();
                            ds.close();

                            //결과 문자열을 다운로드 받기 위한 스트림을 생성
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            //문자열을 읽어서 저장
                            while (true) {
                                String line = br.readLine();
                                if(line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }
                            //사용한 스트림과 연결 해제
                            br.close();
                            con.disconnect();
                            json = sb.toString();
                            Log.e("문자열", json);
                        } catch (Exception e) {
                            Log.e("삽입 예외", e.getLocalizedMessage());
                            message.obj = "삽입 에러로 파라미터 전송에 실패했거나 다운로드 실패\n서버를 확인하거나 파라미터 전송 부분을 확인하세요";
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                        if (json != null) {
                            try {
                                JSONObject object = new JSONObject(json);
                                boolean result = object.getBoolean("result");
                                message.obj = result;
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.e("삽입 예외", e.getLocalizedMessage());
                                message.obj = "삽입 에러로 파라미터 전송에 실패했거나 다운로드 실패\n서버를 확인하거나 파라미터 전송 부분을 확인하세요";
                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        } else {
                            Log.e("파싱 실패", "데이터가 포맷에 맞지 않음");
                            message.obj = "파싱 에러";
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                }.start();

            }
        });

        //삭제 버튼의 클릭 이벤트 핸들러
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Message message = new Message();
                //데이터 유효성 검사
                message.what = 0;

                //데이터 수정 수행
                new Thread(){
                    String json = null;

                    @Override
                    public void run() {
                        try {
                            //다운로드 받을 주소 생성
                            URL url = new URL("http://cyberadam.cafe24.com/item/delete");
                            //URL에 연결
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();

                            // 연결 객체 옵션 설정
                            con.setRequestMethod("POST");
                            con.setConnectTimeout(10000);
                            con.setUseCaches(false);

                            //POST 방식에서의 파라미터 생성 및 전송
                            String data  = URLEncoder.encode("itemid", "UTF-8").toString() + "=" +
                                    URLEncoder.encode("30".trim(), "UTF-8");
                            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                            wr.write(data);
                            wr.flush();

                            //결과 문자열을 다운로드 받기 위한 스트림을 생성
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            //문자열을 읽어서 저장
                            while (true) {
                                String line = br.readLine();
                                if(line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }
                            //사용한 스트림과 연결 해제
                            br.close();
                            con.disconnect();
                            json = sb.toString();
                        } catch (Exception e) {
                            Log.e("삭제 예외", e.getLocalizedMessage());
                            message.obj = "삭제 실패:" + e.getLocalizedMessage();
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                        if (json != null) {
                            try {
                                JSONObject object = new JSONObject(json);
                                boolean result = object.getBoolean("result");
                                message.obj = result;
                                message.what = 2;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.e("삭제 예외", e.getLocalizedMessage());
                                message.obj = "삭제 에러로 파라미터 전송에 실패했거나 다운로드 실패\n서버를 확인하거나 파라미터 전송 부분을 확인하세요";
                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        } else {
                            Log.e("파싱 실패", "데이터가 포맷에 맞지 않음");
                            message.obj = "파싱 에러";
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                }.start();
            }
        });
    }
}