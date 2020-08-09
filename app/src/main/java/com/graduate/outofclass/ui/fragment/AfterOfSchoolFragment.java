package com.graduate.outofclass.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduate.outofclass.PlanActivity;
import com.graduate.outofclass.R;
import com.graduate.outofclass.adapter.AfterOfSchoolAdapter;
import com.graduate.outofclass.bean.PlanBean;
import com.graduate.outofclass.presenter.PlanDBService;
import com.graduate.outofclass.utils.RecycleViewDivider;

import java.util.List;

public class AfterOfSchoolFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton imageButton;
    private TextView tvNoPlan;
    private AfterOfSchoolAdapter afterOfSchoolAdapter;
    private List<PlanBean> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View afterSchoolView = inflater.inflate(R.layout.fragment_after_school, container, false);

        recyclerView = afterSchoolView.findViewById(R.id.recyclerView);
        imageButton = afterSchoolView.findViewById(R.id.float_button);
        tvNoPlan = afterSchoolView.findViewById(R.id.tv_null_plan);
        afterOfSchoolAdapter = new AfterOfSchoolAdapter(getContext(), new AfterOfSchoolAdapter.ItemClicker() {
            @Override
            public void onItemClick(PlanBean p) {
                Intent intent = new Intent(getContext(), PlanActivity.class);
                intent.putExtra("data", p);
                startActivityForResult(intent, 1);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(afterOfSchoolAdapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.VERTICAL, 20,
                getResources().getColor(R.color.cardview_light_background)));

        initDB();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlanActivity.class);
                intent.putExtra("position", data.size() + 1);
                startActivityForResult(intent, 0);
            }
        });
        isNoPlan();
        return afterSchoolView;
    }

    private void isNoPlan(){
        if(data.size() == 0){
            tvNoPlan.setVisibility(View.VISIBLE);
        }else {
            tvNoPlan.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化数据库，并获取数据
     */
    public void initDB() {
        PlanDBService planDBService = new PlanDBService(getContext());
        data = planDBService.getPlan();
        afterOfSchoolAdapter.setData(data);
    }

    /**
     * 返回时候重新请求数据获取数据
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        initDB();
        isNoPlan();
    }
}
