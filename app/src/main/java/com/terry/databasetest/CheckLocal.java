package com.terry.databasetest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckLocal extends AppCompatActivity {
    private TextView tv;

    private String u1 = "\u1019\u102d\u102f";
    private String u2 = "\u1019\u102f\u102d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklocal);

        Button bt = findViewById(R.id.getvalue);
        tv = findViewById(R.id.textView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(u1 + "     " + u2);
            }
        });
    }
}