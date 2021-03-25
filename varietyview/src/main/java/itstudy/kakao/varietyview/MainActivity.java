package itstudy.kakao.varietyview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView spanView=(TextView)findViewById(R.id.spanView);
        spanView.setMovementMethod(new ScrollingMovementMethod());
        String data="대한민국 \n img \n 내가 살아온 자랑스런 나의 조국";
        SpannableStringBuilder builder=new SpannableStringBuilder(data);
        int start=data.indexOf("img");
        if(start>-1){
            int end=start+"img".length();
            Drawable dr= ResourcesCompat.getDrawable(getResources(),R.drawable.korea, null);
            dr.setBounds(0,0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            ImageSpan span=new ImageSpan(dr);
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        start=data.indexOf("대한민국");
        if(start>-1){
            int end=start+"대한민국".length();
            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            RelativeSizeSpan sizeSpan=new RelativeSizeSpan(2.0f);
            builder.setSpan(styleSpan, start, end+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan, start, end+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spanView.setText(builder);
    }
}