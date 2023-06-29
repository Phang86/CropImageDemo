package com.hnhy.cropimagedemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.hnhy.cropimagedemo.CropUtil;
import com.hnhy.cropimagedemo.databinding.ActivityCropImageBinding;

public class CropImageActivity extends AppCompatActivity {

    private ActivityCropImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCropImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        Uri uri = getIntent().getExtras().getParcelable("uri");
        if (uri != null){
            binding.cropView.of(uri)
                    .withOutputSize(80,80)
                    .initialize(this);
        }

        binding.toolbar.setNavigationOnClickListener(v -> { finish(); });

        binding.cropConfirm.setOnClickListener(v -> {
            Bitmap bitmap = binding.cropView.getOutput();
            //Uri corpUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
            Intent intent = new Intent();
            intent.putExtra("bitmap",bitmap);
            setResult(CropImageActivity.this.RESULT_OK, intent);
            finish();
        });
    }


}