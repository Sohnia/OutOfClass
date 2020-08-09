package com.graduate.outofclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.graduate.outofclass.ui.fragment.AfterOfSchoolFragment;
import com.graduate.outofclass.ui.fragment.CourseDescriptionFragment;
import com.graduate.outofclass.ui.fragment.ProfileFragment;
import com.graduate.outofclass.ui.fragment.TestFragment;
import com.graduate.outofclass.ui.view.BottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener(){
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#00CD66")
                .addItem(CourseDescriptionFragment.class,
                        getString(R.string.main),
                        R.drawable.ic_tab_home_normal,
                        R.drawable.ic_tab_home_active)
                .addItem(AfterOfSchoolFragment.class,
                        getString(R.string.plan),
                        R.drawable.ic_tab_subject_normal,
                        R.drawable.ic_tab_subject_active)
                .addItem(TestFragment.class,
                        getString(R.string.exam),
                        R.drawable.ic_tab_group_normal,
                        R.drawable.ic_tab_group_active)
                .addItem(ProfileFragment.class,
                        getString(R.string.mine),
                        R.drawable.ic_tab_profile_normal,
                        R.drawable.ic_tab_profile_active)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
