package com.graduate.outofclass;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

public class SplashAcitivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                    return defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.getSystemWindowInsetLeft(),
                            0,
                            defaultInsets.getSystemWindowInsetRight(),
                            defaultInsets.getSystemWindowInsetBottom());
                }
            });
            ViewCompat.requestApplyInsets(decorView);
            //将状态栏设成透明，如不想透明可设置其他颜色
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashAcitivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
