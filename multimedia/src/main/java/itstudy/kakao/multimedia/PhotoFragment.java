package itstudy.kakao.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;

//컴파일 할 때 결정되는 상수 : 숫자형과 String 만 가능


public class PhotoFragment extends Fragment {
    private Uri uri;
    private static String ARG_URI = "uri";

    //Fragment를 생성할 때 호출되는 함수
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //번들에 저장된 값을 가져와서 uri 에 설정
        uri = (Uri) getArguments().getParcelable(ARG_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //uri 값을 읽어서 파일에 대한 정보를 가져오고 파일을 비트맵으로 가져온 후 imageView에 출력
        ParcelFileDescriptor descriptor = null;
        try {
            descriptor = requireContext().getContentResolver().openFileDescriptor(uri, "r");

            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor());
            ImageView imageView = getActivity().findViewById(R.id.imageView);
            Glide.with(this).load(bitmap).into(imageView);

        } catch (FileNotFoundException e) {
            Log.e("파일읽기 예외", e.getLocalizedMessage());
        }
    }
}

