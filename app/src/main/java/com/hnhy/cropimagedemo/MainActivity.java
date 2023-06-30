package com.hnhy.cropimagedemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.widget.Toast;

import com.hnhy.cropimagedemo.base.BaseActivity;
import com.hnhy.cropimagedemo.databinding.ActivityMainBinding;
import com.hnhy.cropimagedemo.util.CacheDataManagerUtils;
import com.hnhy.cropimagedemo.util.PermissionUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity<ActivityMainBinding>{

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> openAlbumLauncher;

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (PermissionUtils.requestPermissionResult(result, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                openAlbum();
                return;
            }
            Toast.makeText(this, "未获取到外部读取权限", Toast.LENGTH_SHORT).show();
        });

        openAlbumLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                //通过Intent意图把Uri对象传递给CropImageActivity
                Uri uri = result.getData().getData();
                Intent intent = new Intent(this, CropImageActivity.class);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mBinding.mainToolbar.setOnClickListener(v -> { finish(); });
        mBinding.mainOpenAlbum.setOnClickListener(v -> { openAlbum(); });
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            openAlbumLauncher.launch(intent);
        }
    }

    /**
     * 检查权限是否拥有
     * @param permission 权限名
     */
    private boolean checkPermission(String[] permission){
        return PermissionUtils.addPermission(this, permission, requestPermissionLauncher);
    }

    /**
     * EventBus接收到的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Bitmap bitmap){
        if (bitmap != null) {
            mBinding.cropOutImage.setImageBitmap(bitmap);
        }
    }

}