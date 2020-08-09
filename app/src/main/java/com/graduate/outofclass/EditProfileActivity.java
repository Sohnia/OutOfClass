package com.graduate.outofclass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

public class EditProfileActivity extends BaseActivity {

    @BindView(R.id.et_userName)
    EditText editName;
    @BindView(R.id.et_school)
    EditText editSchool;
    @BindView(R.id.et_College)
    EditText editCollege;
    @BindView(R.id.et_sign)
    EditText editSign;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final int REQUEST_PROFILE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getData();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_profile;
    }

    /**
     * 获取SharedPreferences的数据
     */
    private void getData() {

        String name = sharedPreferences.getString("name", null);
        String school = sharedPreferences.getString("school", null);
        String college = sharedPreferences.getString("college", null);
        String sign = sharedPreferences.getString("sign", null);

        if (name != null) {
            editName.setText(name);
        } else {
            editName.setText("");
        }

        if (school != null) {
            editSchool.setText(school);
        } else {
            editSchool.setText("");
        }

        if (college != null) {
            editCollege.setText(college);
        } else {
            editCollege.setText("");
        }

        if (sign != null) {
            editSign.setText(sign);
        } else {
            editSign.setText("");
        }
    }


    @OnClick(R.id.edit_btn)
    public void saveClick(View v){
        saveData();
        finish();
        setResult(REQUEST_PROFILE);
    }
    /**
     * 保存数据到SharedPreferences
     */
    private void saveData() {
        String name = editName.getText().toString().trim();
        String school = editSchool.getText().toString().trim();
        String college = editCollege.getText().toString().trim();
        String sign = editSign.getText().toString();

        editor.putString("name", name);
        editor.putString("school", school);
        editor.putString("college", college);
        editor.putString("sign", sign);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}