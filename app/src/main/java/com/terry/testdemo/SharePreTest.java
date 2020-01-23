package com.terry.testdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SharePreTest extends AppCompatActivity {
    private final static String MY_PRE_NAME = "gaoruitao";
    private final static String MY_PRE_STRING_KEY = "KEY1";
    private final static String MY_PRE_INT_KEY = "KEY2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_test);
        SharedPreferences preferences = getSharedPreferences(MY_PRE_NAME, Context.MODE_PRIVATE);

        System.out.println(MY_PRE_STRING_KEY + " " + preferences.getString(MY_PRE_STRING_KEY,
                "ruitao"));
        System.out.println(MY_PRE_INT_KEY + " " + preferences.getInt(MY_PRE_INT_KEY, 999));
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MY_PRE_STRING_KEY, this.toString());
        editor.putInt(MY_PRE_INT_KEY, this.hashCode());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences(MY_PRE_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(MY_PRE_STRING_KEY)) {
            System.out.println(MY_PRE_STRING_KEY + " " + preferences.getString(MY_PRE_STRING_KEY,
                    "gao"));
        }
        if (preferences.contains(MY_PRE_INT_KEY)) {
            System.out.println(MY_PRE_INT_KEY + " " + preferences.getInt(MY_PRE_INT_KEY, 888));
        }
    }
}
