package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {

    EditText input01;
    String mes = "";

    Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            Toast.makeText(SocketActivity.this, mes, Toast.LENGTH_LONG).show();
        }
    };

    class ConnectThread extends Thread {
        String hostname;
        public ConnectThread(String addr) {
            hostname = addr;
        }
        public void run() {
            Socket sock = null;
            ObjectOutputStream outstream = null;
            ObjectInputStream instream = null;
            try {
                int port = 11001;
                sock = new Socket(hostname, port);
                outstream = new ObjectOutputStream(sock.getOutputStream());
                outstream.writeObject("Hello on Android");
                outstream.flush();
                instream = new ObjectInputStream(
                        sock.getInputStream());
                mes = (String) instream.readObject();
                mHandler.sendEmptyMessage(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally{
                try {
                    if(outstream != null)
                        outstream.close();
                    if(instream != null)
                        instream.close();
                    if(sock != null)
                        sock.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        input01 = (EditText) findViewById(R.id.input01);

        // 버튼 이벤트 처리
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addr = input01.getText().toString().trim();

                // 네트워킹을 사용하는 경우 스레드를 이용해야 합니다.
                ConnectThread thread = new ConnectThread(addr);
                thread.start();
            }
        });

    }
}