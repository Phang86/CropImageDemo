package com.hnhy.cropimagedemo.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.hnhy.cropimagedemo.databinding.ActivityMainBinding;
import com.hnhy.cropimagedemo.util.PermissionUtils;

import java.net.URI;
import java.util.IllegalFormatCodePointException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityMainBinding binding;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> openAlbumLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (PermissionUtils.requestPermissionResult(result, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                openAlbum();
            }else{
                Toast.makeText(this, "未获取到外部读取权限", Toast.LENGTH_SHORT).show();
            }
        });

        openAlbumLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri uri = result.getData().getData();
                Intent intent = new Intent(this, CropImageActivity.class);
                intent.putExtra("uri",uri);
                startActivityForResult(intent, 1);
            }
        });

        binding.mainOpenAlbum.setOnClickListener(v -> {
            openAlbum();
        });
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

    private boolean checkPermission(String[] permission){
        return PermissionUtils.addPermission(this, permission, requestPermissionLauncher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = data.getExtras().getParcelable("bitmap");
                binding.cropOutImage.setImageBitmap(bitmap);
            }
        }
    }
}