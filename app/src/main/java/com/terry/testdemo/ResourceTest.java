package com.terry.testdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ResourceTest extends AppCompatActivity {
    private static final String TAG = "ResourceTest";
    TextView mShow;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restest_layout);

        mShow = findViewById(R.id.show_result);
        final EditText text = findViewById(R.id.edit);
        final RadioButton selRes = findViewById(R.id.sel_res);
        final RadioButton selIntent = findViewById(R.id.sel_intent);

//        float[] luxLevels = getLuxLevels(resources.getIntArray(
//                com.android.internal.R.array.config_autoBrightnessLevels));
//        int[] brightnessLevelsBacklight = resources.getIntArray(
//                com.android.internal.R.array.config_autoBrightnessLcdBacklightValues);
//        float[] brightnessLevelsNits = getFloatArray(resources.obtainTypedArray(
//                com.android.internal.R.array.config_autoBrightnessDisplayValuesNits));
//        float autoBrightnessAdjustmentMaxGamma = resources.getFraction(
//                com.android.internal.R.fraction.config_autoBrightnessAdjustmentMaxGamma,
//                1, 1);
//
//        float[] nitsRange = getFloatArray(resources.obtainTypedArray(
//                com.android.internal.R.array.config_screenBrightnessNits));
//        int[] backlightRange = resources.getIntArray(
//                com.android.internal.R.array.config_screenBrightnessBacklight);

        text.setText("vendor_required_apps_managed_device");
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = text.getText().toString();
                if(str.length() > 0) {
                    if(selRes.isChecked()) readResourceValue(str, "array");
                    if(selIntent.isChecked()) getDefaultApp(str);
                }
            }
        });
    }

    private void readResourceValue(String str, String type) {
        int id = getResources().getIdentifier(str, type, "android");
        Log.d(TAG, "getAndroidResourceId: id " + id + ", str " + str);

        try {
            switch (type.toLowerCase()) {
                case "array":
                    String[] result = getResources().getStringArray(id);
                    mShow.setText(Arrays.asList(result).toString());
                    break;
                case "int":
//                    float[] result = getResources().getStringArray(id);
//                    mShow.setText(Float.asList(result).toString());
                    break;

            }
        } catch (Resources.NotFoundException ex) {
            Log.w(TAG, "Can't find this config in " + type);
        }
    }

    @SuppressLint("WrongConstant")
    private void getDefaultApp(String str) {
        try {
            Intent var2 = new Intent(str);
            var2.addCategory("android.intent.category.DEFAULT");
            var2.addFlags(8);
            ResolveInfo var5 = getPackageManager().resolveActivity(var2, 0);
            assert var5 != null;
            this.mShow.setText(var5.activityInfo.applicationInfo.packageName);
        } catch (Exception var3) {
            Log.e(TAG, "getDefaultApp: NullPointerException :" + var3);
        }
    }

    private String convertToString(int[] ff) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ff.length; i++) {
            stringBuilder.append(ff[i] + " ");
        }
        return stringBuilder.toString();
    }
}