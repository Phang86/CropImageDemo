package com.hnhy.cropimagedemo;

import android.graphics.Bitmap;
import android.net.Uri;

import com.hnhy.cropimagedemo.base.BaseActivity;
import com.hnhy.cropimagedemo.databinding.ActivityCropImageBinding;

import org.greenrobot.eventbus.EventBus;

public class CropImageActivity extends BaseActivity<ActivityCropImageBinding> {

    @Override
    protected ActivityCropImageBinding getViewBinding() {
        return ActivityCropImageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        //接收MainActivity传过来的uri
        Uri uri = getIntent().getExtras().getParcelable("uri");
        if (uri != null){
            mBinding.cropView.of(uri)
                    .withAspect(50, 50)
                    .initialize(mContext);
        }
    }

    @Override
    protected void initData() {
        mBinding.corpToolbar.setOnClickListener(v -> {
            if (v.getId() == R.id.toolbar_right){
                Bitmap bitmap = mBinding.cropView.getOutput();
                //通过EventBus传递数据
                EventBus.getDefault().post(bitmap);
            }
            finish();
        });
    }

}