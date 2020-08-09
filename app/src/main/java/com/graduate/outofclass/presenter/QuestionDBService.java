package com.graduate.outofclass.presenter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.graduate.outofclass.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class QuestionDBService {

    private SQLiteDatabase db;
    private String fileName;

    //构造方法
    public QuestionDBService(String fileName) {
        //连接数据库
        db = SQLiteDatabase.openDatabase("/data/data/com.graduate.outofclass/databases/" + fileName,
                null, SQLiteDatabase.OPEN_READWRITE);
        this.fileName = fileName;
    }

    //获取数据库的数据

    /**
     * 获取数据库的数据
     *
     * @param type 1-10
     * @return QuestionBean List
     */
    public List<QuestionBean> getQuestion(int type) {
        List<QuestionBean> list = new ArrayList<>();
        Cursor cursor;
        //执行sql语句
        if (type <= 8) {
            cursor = db.rawQuery("select * from exam where section=" + type, null);
        } else if (type == 9) {
            cursor = db.rawQuery("select * from exam where wrong_item=1", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM(SELECT * FROM exam WHERE is_choice=1 ORDER BY RANDOM() limit 10) UNION" +
                    " SELECT * FROM(SELECT * FROM exam WHERE is_choice=0 ORDER BY RANDOM() limit 5) ORDER BY is_choice DESC", null);
        }

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                QuestionBean question = new QuestionBean();
                //ID
                question.setId(cursor.getInt(cursor.getColumnIndex("id")));
                //问题
                question.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                //四个选择
                question.setAnswerA(cursor.getString(cursor.getColumnIndex("answer_a")));
                question.setAnswerB(cursor.getString(cursor.getColumnIndex("answer_b")));
                question.setAnswerC(cursor.getString(cursor.getColumnIndex("answer_c")));
                question.setAnswerD(cursor.getString(cursor.getColumnIndex("answer_d")));
                //答案
                question.setAnswer(cursor.getInt(cursor.getColumnIndex("right_answer")));
                //解析
                question.setExplainAction(cursor.getString(cursor.getColumnIndex("explain_answer")));
                //设置为没有选择任何选项
                question.setSelectedAnswer(-1);
                //设置为没有选择任何输入
                question.setInputAnswer("");
                //题目类型
                question.setChoice(cursor.getInt(cursor.getColumnIndex("is_choice")) > 0);
                //题目所在的章节
                question.setSection(cursor.getInt(cursor.getColumnIndex("section")));
                list.add(question);
            }
            cursor.close();
            return list;
        } else return null;
    }

    public void recodeWrongItem(int id, boolean flag) {
        String sql;
        if (flag) {
            sql = String.format("update exam set wrong_item=0 where id=%s", id);
        } else {
            sql = String.format("update exam set wrong_item=1 where id=%s", id);
        }
        db.execSQL(sql);
    }

    private void closeDB() {
        db.close();
    }
}
