package com.graduate.outofclass.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.graduate.outofclass.CourseContentActivity;
import com.graduate.outofclass.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CourseDescriptionFragment extends Fragment {
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_course_description, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick({R.id.fb, R.id.sb, R.id.tb, R.id.fob})
    public void click(View v) {

        switch (v.getId()) {
            case R.id.fb:
                startActivity(1);
                break;
            case R.id.sb:
                startActivity(2);
                break;
            case R.id.tb:
                startActivity(3);
                break;
            case R.id.fob:
                startActivity(4);
                break;
            default:
                break;
        }
    }

    private void startActivity(int code){
        Intent intent = new Intent();
        intent.setClass(getContext(), CourseContentActivity.class);
        intent.putExtra("course", code);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
