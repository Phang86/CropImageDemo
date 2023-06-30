package com.hnhy.cropimagedemo.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.hnhy.cropimagedemo.util.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseActivity<V extends ViewBinding> extends AppCompatActivity{

    protected V mBinding;
    protected Context mContext;
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.MIUISetStatusBarLightMode(getWindow(),false);
        mBinding = getViewBinding();
        setContentView(mBinding == null ? null : mBinding.getRoot());
        mContext = this;
        initView();
        initData();
    }

    protected abstract V getViewBinding();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
