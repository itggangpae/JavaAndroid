package itstudy.kakao.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NavigationActivity extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    //메뉴 사용을 위한 메소드를 재정의
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    //스낵바를 출력해주는 메소드
    private void showSnackbar(String message){
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        drawer=findViewById(R.id.main_drawer);
        toggle=new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.main_drawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.menu_drawer_home){
                    showSnackbar("NavigationDrawer... home...");
                }else if(id==R.id.menu_drawer_message){
                    showSnackbar("NavigationDrawer... message...");
                }else if(id==R.id.menu_drawer_add){
                    showSnackbar("NavigationDrawer... add...");
                }
                return false;
            }
        });
    }
}