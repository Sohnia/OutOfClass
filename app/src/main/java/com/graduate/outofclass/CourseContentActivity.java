package com.graduate.outofclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tencent.smtt.sdk.WebView;

public class CourseContentActivity extends AppCompatActivity {

    private WebView webView;
    private int course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        course = getIntent().getIntExtra("course", 1);

        webView = findViewById(R.id.webView);
        if (course == 1) {
            webView.loadUrl("file:///android_asset/CourseName.html");
        } else if (course == 2) {
            webView.loadUrl("file:///android_asset/CourseIntroduce.html");
        } else if (course == 3) {
            webView.loadUrl("file:///android_asset/CourseContent.html");
        } else if (course == 4) {
            webView.loadUrl("file:///android_asset/CourseReference.html");
        }
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setTextZoom(200);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
