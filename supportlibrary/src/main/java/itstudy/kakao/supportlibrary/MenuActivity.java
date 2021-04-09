package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;

public class MenuActivity extends AppCompatActivity {
    SearchView searchView;

    private void showSnackbar(String message){
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView imageView=findViewById(R.id.imageView);
        registerForContextMenu(imageView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"서버전송");
        menu.add(0,1,0,"보관함에 보관");
        menu.add(0,2,0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showSnackbar("서버 전송이 선택되었습니다.");
                break;
            case 1:
                showSnackbar("보관함에 보관이 선택되었습니다.");
                break;
            case 2:
                showSnackbar("삭제가 선택되었습니다.");
                break;
        }
        return true;
    }

    SearchView.OnQueryTextListener queryTextListener=new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            showSnackbar(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_lab, menu);

        try{
            Method method=menu.getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            method.setAccessible(true);
            method.invoke(menu, true);
        }catch (Exception e){
            e.printStackTrace();
        }
        MenuItem menuItem=menu.findItem(R.id.menu_main_search);
        searchView=(SearchView)menuItem.getActionView();

        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }


}