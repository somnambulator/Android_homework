package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PracticeActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Log.i(TAG,"create practice activity");

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        ImageButton ib1 = findViewById(R.id.imageButton1);//return
        ImageButton ib2 = findViewById(R.id.imageButton2);//sound
        Button bt1 = findViewById(R.id.button1);
        TextView tv1 = findViewById(R.id.textView2);
        TextView tv2 = findViewById(R.id.textView5);

        String str0 = mYear+"-"+(mMonth+1)+"-"+mDay+" 获得";
        tv1.setText(str0);

        String str = "表扬次数 0/50";
        tv2.setText(str);
        final int[] i = {0};

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PracticeActivity.this,"no sound",Toast.LENGTH_SHORT).show();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i[0] <50){
                    i[0]++;
                    String str = "表扬次数 "+ i[0] +"/50";
                    tv2.setText(str);
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "start practice activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "resume practice activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "restart practice activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "pause practice activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "stop practice activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "destroy practice activity");
    }
}
