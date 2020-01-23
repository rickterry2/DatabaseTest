package com.terry.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TestDemo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        findViewById(R.id.database_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDemo.this, DatabaseTest.class));
            }
        });

        findViewById(R.id.font_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDemo.this, FontTest.class));
            }
        });

        findViewById(R.id.res_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDemo.this, ResourceTest.class));
            }
        });

        findViewById(R.id.share_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDemo.this, SharePreTest.class));
            }
        });

        findViewById(R.id.notice_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDemo.this, NoticeTest.class));
            }
        });
    }
}
