package com.graduate.outofclass.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClassPlanDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String SWORD = "SWORD";

    private static final String TABLE_PLAN = "CREATE TABLE class_plan ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "number integer,"
            + "title text,"
            + "goal text )";

    //三个不同参数的构造函数
    //带全部参数的构造函数，此构造函数必不可少
    public ClassPlanDatabaseHelper(Context context, String name, CursorFactory factory,
                                   int version) {
        super(context, name, factory, version);

    }

    //带两个参数的构造函数，调用的其实是带三个参数的构造函数
    public ClassPlanDatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    //带三个参数的构造函数，调用的是带所有参数的构造函数
    public ClassPlanDatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    //创建数据库
    public void onCreate(SQLiteDatabase db) {
        //执行创建数据库操作
        db.execSQL(TABLE_PLAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //创建成功，日志输出提示
        Log.i(SWORD, "update a Database");
    }

}
