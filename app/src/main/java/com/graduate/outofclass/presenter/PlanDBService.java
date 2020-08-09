package com.graduate.outofclass.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.graduate.outofclass.base.ClassPlanDatabaseHelper;
import com.graduate.outofclass.bean.PlanBean;

import java.util.ArrayList;
import java.util.List;

public class PlanDBService {

    private Context context;

    private SQLiteDatabase db;
    private ClassPlanDatabaseHelper classPlanDatabaseHelper;
    private Cursor cursor;

    public PlanDBService(Context context){

        this.context = context;

        //new一个数据库对象
        classPlanDatabaseHelper = new ClassPlanDatabaseHelper(this.context, "class_plan.db", null, 1);
        //读取数据库
        db = classPlanDatabaseHelper.getReadableDatabase();
        //查询数据库

    }

    //获取数据库的数据
    public List<PlanBean> getPlan() {

        List<PlanBean> list = new ArrayList<>();
        cursor = db.rawQuery("select * from class_plan", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();

            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                PlanBean planBean = new PlanBean();

                planBean.id = cursor.getInt(cursor.getColumnIndex("id"));
                planBean.number = cursor.getInt(cursor.getColumnIndex("number"));
                planBean.title = cursor.getString(cursor.getColumnIndex("title"));
                planBean.goal = cursor.getString(cursor.getColumnIndex("goal"));

                list.add(planBean);
            }
        }

        cursor.close();
        db.close();

        return list;
    }


}
