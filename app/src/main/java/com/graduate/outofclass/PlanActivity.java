package com.graduate.outofclass;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.graduate.outofclass.base.ClassPlanDatabaseHelper;
import com.graduate.outofclass.bean.PlanBean;

public class PlanActivity extends AppCompatActivity {

    private PlanBean old;
    private EditText planTitle;
    private EditText planGoal;
    private SQLiteDatabase db1;
    private ClassPlanDatabaseHelper classPlanDatabaseHelper;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        planTitle = findViewById(R.id.plan_title);
        planGoal = findViewById(R.id.plan_goal);
        classPlanDatabaseHelper = new ClassPlanDatabaseHelper(PlanActivity.this, "class_plan.db", null, 1);
        db1 = classPlanDatabaseHelper.getWritableDatabase();
        values = new ContentValues();
        initData();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_save:
//                if (old != null) {
//                    updateData();
//                } else {
//                    saveData();
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 初始化页面布局，获取AfterSchoolFragment传递的数据
     */
    private void initData() {
        Intent intent = getIntent();
        old = (PlanBean) intent.getSerializableExtra("data");

        if (old != null) {
            planTitle.setText(old.title);
            planGoal.setText(old.goal);
        }
    }

    /**
     * 创建数据库并保存数据到数据库中
     */
    private void saveData() {
        Log.d("planActivity", "save data title:" + planTitle.getText().toString());
        if (!"".equals(planTitle.getText().toString())) {
            values.put("title", planTitle.getText().toString());
            values.put("goal", planGoal.getText().toString());
            db1.insert("class_plan", null, values);
            db1.close();

            setResult(AppCompatActivity.RESULT_OK);
            finish();
        }
    }

    private void updateData() {
        Log.d("planActivity", "update data title:" + planTitle.getText().toString());
        if (!"".equals(planTitle.getText().toString())) {
            values.put("title", planTitle.getText().toString());
            values.put("goal", planGoal.getText().toString());
            String where = "id = " + old.id;
            db1.update("class_plan", values, where, null);
            db1.close();
            setResult(AppCompatActivity.RESULT_OK);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 当按下返回键时所执行的命令
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (old != null) {
                updateData();
            } else {
                saveData();
            }
            // 此处写你按返回键之后要执行的事件的逻辑
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}

