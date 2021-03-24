package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDialog extends AppCompatActivity {
    //int mSelect=0;
    boolean[] mSelect = {false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_dialog);

        /*
        Button btn = (Button) findViewById(R.id.call);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDialog.this).setTitle("팀을 선택하시오.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setItems(R.array.team,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String[] teams = getResources().getStringArray(R.array.team);
                                        TextView text = (TextView) findViewById(R.id.text);
                                        text.setText("선택한 팀 = " + teams[which]);
                                    }
                                })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

         */

        /*
        Button btn = (Button) findViewById(R.id.call);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDialog.this)
                        .setTitle("팀을 선택하시오.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setSingleChoiceItems(R.array.team, mSelect,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mSelect = which;
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String[] teams = getResources().getStringArray(R.array.team);
                                TextView text = (TextView) findViewById(R.id.text);
                                text.setText("선택한 팀 = " + teams[mSelect]);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

         */

        Button btn = (Button) findViewById(R.id.call);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDialog.this)
                        .setTitle("팀을 선택하시오.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMultiChoiceItems(R.array.team, mSelect,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        mSelect[which] = isChecked;
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String[] teams = getResources().getStringArray(R.array.team);
                                TextView text = (TextView) findViewById(R.id.text);
                                String result = "선택한 팀 = ";
                                for (int i = 0; i < mSelect.length; i++) {
                                    if (mSelect[i]) {
                                        result += teams[i] + " ";
                                    }
                                }
                                text.setText(result);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

    }
}