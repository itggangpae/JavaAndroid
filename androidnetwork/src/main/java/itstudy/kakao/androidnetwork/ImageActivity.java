package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageActivity extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = (ImageView)findViewById(R.id.result);
    }

    public void click(View v) {
        String imageurl;
        switch (v.getId()) {
            case R.id.btndraw:
                DownThread1 th = new DownThread1("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSARlFjrDFC6FElhTVpMln0kA3cLkeWUHF5Q05yDvoG_MU6hM3_Zg");
                th.start();
                break;
            case R.id.btndown:
                imageurl = "http://www.onlifezone.com/files/attach/images/962811/376/321/005/2.jpg";
                int idx = imageurl.lastIndexOf('/');
                String localimage = imageurl.substring(idx + 1);
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/itstudy.kakao.androidnetwork/files/" + localimage;

                if (new File(path).exists()) {
                    Toast.makeText(this, "bitmap is exist", Toast.LENGTH_LONG).show();
                    img.setImageBitmap(BitmapFactory.decodeFile(path));
                } else {
                    Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_LONG).show();
                    (new DownThread2(imageurl, localimage)).start();
                }

                break;
        }
    }

    class DownThread1 extends Thread {
        String mAddr;

        DownThread1(String addr) {
            mAddr = addr;
        }

        public void run() {
            try {
                InputStream is = new URL(mAddr).openStream();
                Bitmap bit = BitmapFactory.decodeStream(is);
                is.close();
                Message message = mAfterDown.obtainMessage();
                message.obj = bit;
                mAfterDown.sendMessage(message);
            } catch (Exception e) {;}
        }
    }

    Handler mAfterDown = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Bitmap bit = (Bitmap)msg.obj;
            if (bit == null) {
                Toast.makeText(ImageActivity.this, "bitmap is null", Toast.LENGTH_LONG).show();
            } else {
                img.setImageBitmap(bit);
            }
        }
    };

    class DownThread2 extends Thread {
        String mAddr;
        String mFile;

        DownThread2(String addr, String filename) {
            mAddr = addr;
            mFile = filename;
            Log.e("mAddr",mAddr);
        }
        public void run() {
            URL imageurl;
            int read;
            try {
                imageurl = new URL(mAddr);
                HttpURLConnection conn= (HttpURLConnection)imageurl.openConnection();
                int len = conn.getContentLength();
                byte[] raster = new byte[len];
                InputStream is = conn.getInputStream();
                FileOutputStream fos = openFileOutput(mFile, 0);
                while(true) {
                    read = is.read(raster);
                    if (read <= 0) {
                        break;
                    }
                    fos.write(raster,0, read);
                }
                is.close();
                fos.close();
                conn.disconnect();
            } catch (Exception e) {
                mFile = null;
            }
            Message message = mAfterDown.obtainMessage();
            message.obj = mFile;
            mAfterDown1.sendMessage(message);
        }
    }

    Handler mAfterDown1 = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/itstudy.kakao.androidnetwork/files/" + (String)msg.obj;
                img.setImageBitmap(BitmapFactory.decodeFile(path));
            } else {
                Toast.makeText(ImageActivity.this, "File not found", Toast.LENGTH_LONG).show();
            }
        }
    };

}