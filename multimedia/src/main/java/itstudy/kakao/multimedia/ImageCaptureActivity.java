package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ImageCaptureActivity extends AppCompatActivity implements AutoPermissionsListener, SurfaceHolder.Callback, View.OnClickListener {

    SurfaceHolder holder;

    HandlerThread thread;
    Handler handler;

    SurfaceView surfaceView;
    ImageView imageView;

    CameraManager manager;
    CameraDevice camera;

    //surface에서 사진데이터 추출
    ImageReader reader;
    //surface 화면을 preview 로 찍거나 사진추출 기능 제공..
    CameraCaptureSession session;
    //카메라 정보
    CameraCharacteristics characteristics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        surfaceView = (SurfaceView) findViewById(R.id.surface);
        imageView = (ImageView) findViewById(R.id.btn);
        imageView.setOnClickListener(this);

        holder = surfaceView.getHolder();
        holder.addCallback(this);

        thread = new HandlerThread("background");
        thread.start();
        handler = new Handler(thread.getLooper());

        manager = (CameraManager) getSystemService(CAMERA_SERVICE);

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                holder = surfaceView.getHolder();

                holder.addCallback(this);

                thread = new HandlerThread("background");
                thread.start();
                handler = new Handler(thread.getLooper());

                manager = (CameraManager) getSystemService(CAMERA_SERVICE);
            }
        }
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "permissions denied : " + permissions.length,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "permissions granted : " + permissions.length,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (session != null) {
            try {
                //add~~~~~~~~~~~~~~~~~~~~
                CaptureRequest.Builder requester = camera.createCaptureRequest(camera.TEMPLATE_STILL_CAPTURE);
                requester.addTarget(reader.getSurface());

                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                requester.set(CaptureRequest.JPEG_ORIENTATION, Camera2Util.getOrientation(rotation, characteristics));

                session.capture(requester.build(), null, null);

            } catch (Exception ex) {
            }
        } else {
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera == null) {
            try {

                for (String cameraId : manager.getCameraIdList()) {
                    characteristics = manager.getCameraCharacteristics(cameraId);
                    if (characteristics.get(characteristics.LENS_FACING) ==
                            CameraCharacteristics.LENS_FACING_BACK) {

                        Size largestSize = Camera2Util.getLargestImageSize(characteristics);
                        reader = ImageReader.newInstance(largestSize.getWidth(), largestSize.getHeight(), ImageFormat.JPEG, 2);
                        reader.setOnImageAvailableListener(captureListener, handler);

                        Size optimalSize = Camera2Util.chooseOptimalSize(this, characteristics, width, height);
                        holder.setFixedSize(optimalSize.getWidth(), optimalSize.getHeight());

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            manager.openCamera(cameraId, stateCallback, handler);
                        }
                        return;
                    }
                }
            } catch (Exception ex) {
            }
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        try {
            // 초기화
            holder.setFixedSize(/*width*/0, /*height*/0);
            if (session != null) {
                session.close();
                session = null;
            }
        } finally {
            if (camera != null) {
                camera.close();
                camera = null;
            }
        }
        if (reader != null) reader.close();
    }


    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            ImageCaptureActivity.this.camera = camera;
            try {
                //add~~~~~~~~~~~~~~
                List<Surface> outputs = Arrays.asList(holder.getSurface(), reader.getSurface());
                camera.createCaptureSession(outputs, sessionListener, handler);
            } catch (Exception ex) {
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
        }

        @Override
        public void onError(CameraDevice camera, int error) {
        }
    };


    CameraCaptureSession.StateCallback sessionListener = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            ImageCaptureActivity.this.session = session;
            if (holder != null) {
                try {

                    CaptureRequest.Builder requestBuilder = camera.createCaptureRequest(camera.TEMPLATE_PREVIEW);
                    requestBuilder.addTarget(holder.getSurface());
                    CaptureRequest previewRequest = requestBuilder.build();
                    try {
                        session.setRepeatingRequest(previewRequest, null, null);
                    } catch (CameraAccessException e) {
                    }
                } catch (CameraAccessException ex) {
                }
            } else {
            }
        }

        @Override
        public void onClosed(CameraCaptureSession session) {
            session = null;
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
        }
    };

    ImageReader.OnImageAvailableListener captureListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {

            handler.post(new CapturedImageSaver(reader.acquireNextImage()));

        }
    };


    class CapturedImageSaver implements Runnable {
        private Image mCapture;

        public CapturedImageSaver(Image capture) {
            mCapture = capture;
        }

        @Override
        public void run() {
            File file = null;
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                file = File.createTempFile("IMG", ".jpg", dir);
                FileOutputStream ostream = new FileOutputStream(file);

                ByteBuffer buffer = mCapture.getPlanes()[0].getBuffer();
                byte[] jpeg = new byte[buffer.remaining()];
                buffer.get(jpeg);
                ostream.write(jpeg);
                ostream.flush();

                Snackbar.make(getWindow().getDecorView().getRootView(), "file write ok : " + file.getAbsolutePath(), Snackbar.LENGTH_SHORT).show();

            } catch (Exception ex) {
            } finally {
                mCapture.close();
            }
        }
    }

}

