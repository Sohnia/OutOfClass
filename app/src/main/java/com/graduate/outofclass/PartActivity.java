package com.graduate.outofclass;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduate.outofclass.bean.QuestionBean;
import com.graduate.outofclass.presenter.QuestionDBService;
import com.graduate.outofclass.utils.GetToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class PartActivity extends BaseActivity {

    //数据库的名称
    private String DB_EXAM;
    //数据库的地址
    private String DB_PATH = "/data/data/com.graduate.outofclass/databases/";
    //总的题目数据
    private int count;
    //当前显示的题目
    private int currentItem;
    //问题
    @BindView(R.id.tv_title)
    TextView tv_title;
    //选项
    @BindViews({R.id.RadioA, R.id.RadioB, R.id.RadioC, R.id.RadioD})
    List<RadioButton> radioButtons;

    RadioButton[] mRadioButton;
    //详情
    @BindView(R.id.tv_result)
    TextView tv_result;
    //容器
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    //section
    @BindView(R.id.tool_bar_text)
    TextView barText;
    @BindView(R.id.btn_down)
    AppCompatButton btn_down;
    @BindView(R.id.btn_up)
    AppCompatButton btn_up;
    @BindView(R.id.til_text_input)
    TextInputLayout textInputLayout;
    @BindView(R.id.answer_input)
    EditText editTextAnswer;
    @BindView(R.id.linear_layout_test)
    LinearLayout linearLayoutTest;
    @BindView(R.id.relative_layout_none_item)
    RelativeLayout relativeLayoutNoneItem;

    //题库
    List<QuestionBean> questionBeans;
    //数据库服务
    QuestionDBService questionDbService;
    //是否进入错题模式
    private boolean wrongMode;
    private int examType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试题必须为part1_1,1_2,2_1,2_2,3_1,3_2,4,5
        initFile(DB_EXAM);
        initDB(DB_EXAM);
    }

    @Override
    protected void initData() {
        DB_EXAM = "exam_data_v1.db";
        wrongMode = false;
        examType = getIntent().getIntExtra("exam_Type", 0); //type 1-8代表 按顺序测试的序号,9,10代表错题和随机测题,
    }

    @Override
    protected void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) {
                    btn_down.setClickable(false);
                } else {
                    btn_down.setClickable(true);
                    for (int i = 0; i < 4; i++) {
                        if (mRadioButton[i].isChecked()) {
                            questionBeans.get(currentItem).setSelectedAnswer(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        mRadioButton = radioButtons.toArray(new RadioButton[radioButtons.size()]);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_part;
    }

    @OnClick({R.id.btn_up, R.id.btn_down})
    public void testOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                checkAnswer(questionBeans.get(currentItem));
                if (currentItem > 0) {
                    currentItem--;
                    btn_down.setText("下一题");
                    QuestionBean q = questionBeans.get(currentItem);
                    initItemType(q);
                }else {
                    GetToast.useString(this,"已经是第一题了呢！");
                }
                break;
            case R.id.btn_down:
                //判断是否为最后一题
                checkAnswer(questionBeans.get(currentItem));
                if (currentItem < count - 1) {
                    currentItem++;
                    QuestionBean q = questionBeans.get(currentItem);
                    initItemType(q);
                    if(currentItem == (count - 1) && wrongMode)
                        btn_down.setText("错题检查完成");
                    else if(currentItem == (count - 1))
                        btn_down.setText("检查错题");
                } else if (currentItem == (count - 1) && wrongMode) {
                    checkResult();
                } else {
                    checkWrongItem();
                    //没有题目了，开始检测正确性
                }
                break;
            default:
                break;
        }

    }

    private void checkAnswer(QuestionBean questionBean) {
        if (questionBean.isChoice() && !wrongMode){
            if(questionBean.getSelectedAnswer() == questionBean.getAnswer()){
                questionDbService.recodeWrongItem(questionBean.getId(),true);
            }else {
                questionDbService.recodeWrongItem(questionBean.getId(),false);
            }
        }else if(!questionBean.isChoice() && !wrongMode) {
            questionBean.setInputAnswer(editTextAnswer.getText().toString());
            if (questionBean.getInputAnswer().toLowerCase().equals(questionBean.getAnswerA().toLowerCase())){
                //这道题目答对了
                questionDbService.recodeWrongItem(questionBean.getId(),true);
            }else {
                questionDbService.recodeWrongItem(questionBean.getId(),false);
            }
        }
    }

    private void checkResult() {
        new AlertDialog.Builder(PartActivity.this).setTitle("提示").setMessage("已经到达最后一道题，错题查看完成！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void initItemType(QuestionBean q) {
        barText.setText(number2Section(examType,q));
        editTextAnswer.setText("");
        if (q.isChoice()) {
            textInputLayout.setVisibility(View.GONE);
            mRadioGroup.setVisibility(View.VISIBLE);
            mRadioButton[0].setText(q.getAnswerA());
            mRadioButton[1].setText(q.getAnswerB());
            mRadioButton[2].setText(q.getAnswerC());
            mRadioButton[3].setText(q.getAnswerD());
            mRadioGroup.clearCheck();
            //设置选中
            if (q.getSelectedAnswer() != -1) {
                mRadioButton[q.getSelectedAnswer()].setChecked(true);
            }
        } else {
            mRadioGroup.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.VISIBLE);
            if (!"".equals(q.getInputAnswer())) {
                editTextAnswer.setText(q.getInputAnswer());
            }

        }
        tv_title.setText(q.getQuestion());
        tv_result.setText(q.getExplainAction());
    }

    private void checkWrongItem(){
        final List<Integer> wrongList = checkAnswer(questionBeans);
        if (wrongList.size() == 0) {
            new AlertDialog.Builder(PartActivity.this).setTitle("提示").setMessage("你好厉害，答对了所有题！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", null).show();
        } else {
            //窗口提示
            new AlertDialog.Builder(PartActivity.this).setTitle("恭喜，答题完成！")
                    .setMessage("答对了" + (questionBeans.size() - wrongList.size()) + "道题" + "\n"
                            + "答错了" + wrongList.size() + "道题" + "\n" + "是否查看错题？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    wrongMode = true;
                    if (wrongList.size() == 0) {
                        dialog.dismiss();
                    } else {
                        List<QuestionBean> newList = new ArrayList<>();
                        for (int i = 0; i < wrongList.size(); i++) {
                            newList.add(questionBeans.get(wrongList.get(i)));
                        }
                        questionBeans.clear();
                        questionBeans.addAll(newList);
                        currentItem = 0;
                        count = questionBeans.size();
                        //更新当前显示的内容
                        QuestionBean q = questionBeans.get(currentItem);
                        initItemType(q);
                        btn_down.setText("下一题");
                        //显示结果
                        tv_result.setVisibility(View.VISIBLE);
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }


    /**
     * 初始化数据库服务
     */
    private void initDB(String fileName) {
        questionDbService = new QuestionDBService(fileName);
        questionBeans = questionDbService.getQuestion(examType);
        if (questionBeans != null){
            count = questionBeans.size();
            currentItem = 0;
            QuestionBean q = questionBeans.get(0);
            initItemType(q);
        }else{
            linearLayoutTest.setVisibility(View.GONE);
            relativeLayoutNoneItem.setVisibility(View.VISIBLE);
//            GetToast.useString(this,"没有获取到试题");
        }

    }

    private String number2Section(int examType, QuestionBean q) {
        String section = "";
        switch (examType){
            case 1:
                section = getString(R.string.basic_know);
                break;
            case 2:
                section = getString(R.string.basic_character);
                break;
            case 3:
                section = getString(R.string.classic_sensor_1);
                break;
            case 4:
                section = getString(R.string.classic_sensor_2);
                break;
            case 5:
                section = getString(R.string.classic_sensor_3);
                break;
            case 6:
                section = getString(R.string.tech_rfid);
                break;
            case 7:
                section = getString(R.string.ai_sensor);
                break;
            case 8:
                section = getString(R.string.wireless_sensor);
                break;
            case 9:
                section = getString(R.string.error_exam) + " : " + number2Section(q.getSection(),null);
                break;
            case 10:
                section = "随机测试 : " + number2Section(q.getSection(),null);
                break;
        }
        return section;
    }

    /**
     * 判断是否答题正确
     *
     * @param questionBeans q
     * @return List int
     */
    private List<Integer> checkAnswer(List<QuestionBean> questionBeans) {
        List<Integer> wrongList = new ArrayList<>();
        QuestionBean q = new QuestionBean();
        for (int i = 0; i < questionBeans.size(); i++) {
            q = questionBeans.get(i);
            //选择题判断对错
            if (q.isChoice() && q.getAnswer() != q.getSelectedAnswer()) {
                wrongList.add(i);
            }else if(!q.isChoice() && !q.getAnswerA().toLowerCase().equals(q.getInputAnswer().toLowerCase())){
                wrongList.add(i);
            }
        }
        Log.i("linan", "wrongquestionBeans is: " + wrongList.size());
        return wrongList;
    }

    /**
     * 将数据库拷贝到相应目录
     */
    private void initFile(String DB_NAME) {
        //判断数据库是否拷贝到相应的目录下
        if (!new File(DB_PATH + DB_NAME).exists()) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //复制文件
            try {
                InputStream is = this.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                //用来复制文件
                byte[] buffer = new byte[1024];
                //保存已经复制的长度
                int length;

                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
