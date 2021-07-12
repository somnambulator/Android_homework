package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.hit_me);
        TextView tv1 = findViewById(R.id.textView);
        ImageView iv1 = findViewById(R.id.image);
        Switch sw1 = findViewById(R.id.switch1);
        CalendarView cv1 = findViewById(R.id.calendarView6);

        int[] flag = {0};
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[0] == 1) {tv1.setText("Zhejiang University.");
                    flag[0] = 0;}
                else{tv1.setText("Hello World!");
                    flag[0] = 1;}
            }
        });

        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sw1.isChecked()){
                    iv1.setVisibility(View.VISIBLE);
                    cv1.setVisibility(View.INVISIBLE);
                }
                else{
                    iv1.setVisibility(View.INVISIBLE);
                    cv1.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}