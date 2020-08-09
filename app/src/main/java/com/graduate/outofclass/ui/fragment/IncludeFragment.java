package com.graduate.outofclass.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.graduate.outofclass.PartActivity;
import com.graduate.outofclass.R;

import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class IncludeFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btn_group)
    LinearLayout btn_group;
    @BindView(R.id.all_exam)
    AppCompatButton allExam;
    @BindView(R.id.error_exam)
    AppCompatButton errorExam;
    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_include, container, false);
        unbinder = ButterKnife.bind(this, rootview);
        Bundle bundle = getArguments();
        id = bundle.getInt("state", 0);
        if (id == 0) {
            allExam.setVisibility(View.GONE);
            errorExam.setVisibility(View.GONE); //顺序测试
        } else if (id == 1) {
            btn_group.setVisibility(View.GONE);
            errorExam.setVisibility(View.GONE); //随机测试
        } else {
            btn_group.setVisibility(View.GONE);
            allExam.setVisibility(View.GONE); //错题测试
        }
        return rootview;
    }

    @OnClick({R.id.basic_know, R.id.basic_character, R.id.classic_sensor_1, R.id.tech_rfid,
            R.id.classic_sensor_2, R.id.classic_sensor_3, R.id.ai_sensor, R.id.wireless_sensor,
            R.id.error_exam, R.id.all_exam})
    public void includeOnClick(View v) {
        Intent intent = new Intent(getContext(), PartActivity.class);
        intent.putExtra("partNumber", id + 1);
        switch (v.getId()) {
            case R.id.basic_know:
                intent.putExtra("exam_Type", 1);
                break;
            case R.id.basic_character:
                intent.putExtra("exam_Type", 2);
                break;
            case R.id.classic_sensor_1:
                intent.putExtra("exam_Type", 3);
                break;
            case R.id.classic_sensor_2:
                intent.putExtra("exam_Type", 4);
                break;
            case R.id.classic_sensor_3:
                intent.putExtra("exam_Type", 5);
                break;
            case R.id.tech_rfid:
                intent.putExtra("exam_Type", 6);
                break;
            case R.id.ai_sensor:
                intent.putExtra("exam_Type", 7);
                break;
            case R.id.wireless_sensor:
                intent.putExtra("exam_Type", 8);
                break;
            case R.id.error_exam:
                intent.putExtra("exam_Type", 9);
//                intent.putExtra("exam_Type", 0); //错题检测，不用传值也可以
                break;
            case R.id.all_exam:
                intent.putExtra("exam_Type", 10);
//                intent.putExtra("exam_Type", 0); //综合测验
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
