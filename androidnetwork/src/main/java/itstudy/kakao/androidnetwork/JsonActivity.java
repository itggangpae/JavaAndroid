package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonActivity extends AppCompatActivity {
    TextView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        list = (TextView) findViewById(R.id.list);

    }

    /*
    public void click(View v) {
        String json = "[KIA, SAMSUNG, LG]";
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray ar = new JSONArray(json);
            for (int i = 0; i < ar.length(); i++) {
                sb.append(ar.getString(i) + "\n");
            }
            list.setText(sb.toString());
        } catch (JSONException e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

     */

    public void click(View v) {
        String json = "[{\"팀명\":\"KIA\", \"감독\":\"선동렬\", \"연고지\":\"광주\"},"
                + "{\"팀명\":\"SAMSUNG\", \"감독\":\"류중일\", \"연고지\":\"대구\"},"
                + "{\"팀명\":\"LG\", \"감독\":\"김기태\", \"연고지\":\"서울\"}]";
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray ar = new JSONArray(json);
            for (int i = 0; i < ar.length(); i++) {
                JSONObject team = ar.getJSONObject(i);
                sb.append("팀명:" + team.getString("팀명") +
                        ",감독:" + team.getString("감독") +
                        ",연고지" + team.getString("연고지") + "\n");
            }
            list.setText(sb.toString());
        } catch (JSONException e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}