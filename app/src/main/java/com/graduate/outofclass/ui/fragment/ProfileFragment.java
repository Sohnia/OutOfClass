package com.graduate.outofclass.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.outofclass.EditProfileActivity;
import com.graduate.outofclass.R;
import com.graduate.outofclass.ui.view.CircleImageView;
import com.graduate.outofclass.utils.PhotoPopupWindow;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.name)
    TextView textViewName;
    @BindView(R.id.signature)
    TextView textViewSignature;
    @BindView(R.id.school)
    TextView textViewSchool;
    @BindView(R.id.college)
    TextView textViewCollege;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PhotoPopupWindow mPhotoPopupWindow;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final int REQUEST_PROFILE = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View profileView = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, profileView);
        sharedPreferences = getContext().getSharedPreferences("profile", getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getData();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoPopupWindow = new PhotoPopupWindow(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进行图片选择
                        mPhotoPopupWindow.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        // 判断系统中是否有处理该 Intent 的 Activity
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_IMAGE_GET);
                        } else {
                            Toast.makeText(getContext(), "未找到图片查看器", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 拍照及文件权限申请
                        // 权限已经申请，直接拍照
                        mPhotoPopupWindow.dismiss();
                        imageCapture();
                    }

                });
                mPhotoPopupWindow.showAtLocation(profileView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        return profileView;
    }

    @OnClick({R.id.signature,R.id.school,R.id.college,R.id.name,R.id.version,R.id.about,R.id.suggestion})
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.signature:
                startActivity();
                break;
            case R.id.school:
                startActivity();
                break;
            case R.id.college:
                startActivity();
                break;
            case R.id.name:
                startActivity();
                break;
            case R.id.version:
                Toast.makeText(getContext(), "version alpha-1.0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(getContext(), "毕业设计", Toast.LENGTH_SHORT).show();
                break;
            case R.id.suggestion:
                Toast.makeText(getContext(), "抱歉，暂未开通建议功能！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void startActivity() {
        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivityForResult(intent, REQUEST_PROFILE);
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
            textViewName.setText(name);
        } else {
            textViewName.setText("");
        }

        if (school != null) {
            textViewSchool.setText(school);
        } else {
            textViewSchool.setText("");
        }

        if (college != null) {
            textViewCollege.setText(college);
        } else {
            textViewCollege.setText("");
        }

        if (sign != null) {
            textViewSignature.setText(sign);
        } else {
            textViewSignature.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();

        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PROFILE:
                    getData();
                    break;
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startSmallPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;

                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    startSmallPhotoZoom(Uri.fromFile(temp));
                    break;
            }
        }
    }


    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            circleImageView.setImageBitmap(photo);
        }
    }

    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
        }
        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "head.jpg")));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
