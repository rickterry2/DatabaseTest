package com.terry.databasetest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = "Database_MainActivity";
    private static final long MAX = 10000;
    private static final long NANO_MSEC = 1000 * 1000;

    private SQLiteDatabase mDatabase;
    private TextView textResult;
    Switch sw;
    private Thread th = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.insert_result);

        sw = findViewById(R.id.switch1);

        Button mBtnInsert = findViewById(R.id.insert);
        mBtnInsert.setOnClickListener(MainActivity.this);
        Button mBtnDelete = findViewById(R.id.delete);
        mBtnDelete.setOnClickListener(MainActivity.this);
        Button mBtnUpdate = findViewById(R.id.update);
        mBtnUpdate.setOnClickListener(MainActivity.this);
        Button mBtnQuery = findViewById(R.id.query);
        mBtnQuery.setOnClickListener(MainActivity.this);

        DBHelper mHelper = new DBHelper(this);
        mDatabase = mHelper.getWritableDatabase();

        Button an = findViewById(R.id.another);
        an.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch1:
                Log.i(TAG, "Switch is changed " + sw.isChecked());
                break;
            case R.id.another:
                Intent intent = new Intent(this, CheckLocal.class);
                startActivity(intent);
                break;
            default:
                // nothing
        }


        if (th == null) {
            th = new testThread(view.getId());
            th.start();
        } else if (th.getState() == Thread.State.TERMINATED) {
            th = new testThread(view.getId());
            th.start();
        } else {
            Log.i(TAG, "Nothing to do.");
        }
    }

    // 表名
    // null。数据库如果插入的数据为null，会引起数据库不稳定。为了防止崩溃，需要传入第二个参数要求的对象
    // 如果插入的数据不为null，没有必要传入第二个参数避免崩溃，所以为null
    // 插入的数据
    private void insertData(int age) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, "gao");
        values.put(DBHelper.AGE, age);
        mDatabase.insert(DBHelper.TABLE_NAME, null, values);
//        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
    }

    private void insertDataSql(int age) {
        mDatabase.execSQL( "insert into " + DBHelper.TABLE_NAME
                + "(" + DBHelper.NAME + ", " + DBHelper.AGE + ") " +
                "values('gaoabc', " + age + ")" );
    }

    // 表名
    // 删除条件
    // 满足删除的值
    private void deleteData(int age) {
        int count = mDatabase.delete(DBHelper.TABLE_NAME,
                DBHelper.AGE + " = ?", new String[]{String.valueOf(age)});
//        Toast.makeText(this, "删除数量："+count, Toast.LENGTH_SHORT).show();
    }

    // 表名
    // 修改后的数据
    // 修改条件
    // 满足修改的值
    private void updateData(int age) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, "rui");
        values.put(DBHelper.AGE, age);
        int count = mDatabase
                .update(DBHelper.TABLE_NAME, values,
                        DBHelper.AGE + " = ?", new String[]{String.valueOf(age)});
//        Toast.makeText(this, "修改成功：" + count, Toast.LENGTH_SHORT).show();
    }

    // 表名
    // 查询字段
    // 查询条件
    // 满足查询的值
    // 分组
    // 分组筛选关键字
    // 排序
    private void queryData() {
        @SuppressLint("Recycle") Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.NAME, DBHelper.AGE},
                DBHelper.NAME + " > ?",
                new String[]{"0"},
                null,
                null,
                DBHelper.AGE + " desc");// 注意空格！

        int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
        int ageIndex = cursor.getColumnIndex(DBHelper.AGE);
        while (cursor.moveToNext()) {
            String name = cursor.getString(nameIndex);
            String age = cursor.getString(ageIndex);

//            Log.d(TAG, "name: " + name + ", age: " + age);
        }

        Log.d(TAG, "Query size: " + cursor.getCount());
    }

    private void queryDataSql() {
        mDatabase.rawQuery("select * from " + DBHelper.TABLE_NAME
                + " where " + DBHelper.NAME + " = 'gaoabc'", null);
//        Log.i(TAG, "Sql query " + cc.getString(cc.getColumnIndex(DBHelper.NAME)));
    }

    private class testThread extends Thread {
        private int method;
        private String show;

        testThread() {}
        testThread(int m) {
            this.method = m;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            long startTime = System.nanoTime();
            Log.d(TAG, "===> Start at " + startTime);

            for(int i = 1; i < MAX; i++) {
                switch(method) {
                    case R.id.insert:
                        show = "Insert";
                        if (!sw.isChecked()) insertData(i);
                        else insertDataSql(i);
                        break;
                    case R.id.update:
                        show = "Update";
                        updateData(i);
                        break;
                    case R.id.delete:
                        show = "Delete";
                        deleteData(i);
                        break;
                    default:
                        // Nothing to do
                }
            }

            long endTime = System.nanoTime();
            Log.d(TAG, "===> End at " + endTime
                    + ", eclipse=" + (endTime - startTime)/NANO_MSEC + "ms");
            textResult.setText(show + " eclipse " + (endTime - startTime)/NANO_MSEC + "ms");

            if (method ==  R.id.query) {
                if (!sw.isChecked()) queryData();
                else queryDataSql();
            }
        }


    }
}
