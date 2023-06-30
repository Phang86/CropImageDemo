package com.hnhy.cropimagedemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hnhy.cropimagedemo.R;
import com.hnhy.cropimagedemo.base.BaseView;
import com.hnhy.cropimagedemo.databinding.ToolbarLayoutBinding;

public class ToolBarView extends BaseView<ToolbarLayoutBinding> {

    public ToolBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView(TypedArray ta) {
        String title = ta.getString(R.styleable.toolBarView_toolbar_title_text);
        int isShowOperate = ta.getInteger(R.styleable.toolBarView_toolbar_title_isShow_operate, 0);
        int resourceBgId = ta.getResourceId(R.styleable.toolBarView_toolbar_title_operate_img_src, R.drawable.selector_pair_bg);

        if (TextUtils.isEmpty(title)) {
            return;
        }

        mBinding.toolbarTitle.setText(title);

        if (isShowOperate == 0) {
            mBinding.toolbarLeft.setVisibility(GONE);
            mBinding.toolbarRight.setVisibility(GONE);
        } else if (isShowOperate == 1) {
            mBinding.toolbarLeft.setVisibility(VISIBLE);
            mBinding.toolbarRight.setVisibility(GONE);
        } else if (isShowOperate == 2){
            mBinding.toolbarRight.setVisibility(VISIBLE);
            mBinding.toolbarLeft.setVisibility(GONE);
        }else{
            mBinding.toolbarRight.setVisibility(VISIBLE);
            mBinding.toolbarLeft.setVisibility(VISIBLE);
        }

        //更换操作图标背景
        if (resourceBgId != R.drawable.selector_pair_bg) {
            mBinding.toolbarRight.setBackgroundResource(resourceBgId);
        }

    }

    @Override
    protected ToolbarLayoutBinding getViewBinding() {
        return ToolbarLayoutBinding.inflate(LayoutInflater.from(mContext));
    }

    @Override
    protected int[] getAttrs() {
        return R.styleable.toolBarView;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mBinding.toolbarLeft.setOnClickListener(onClickListener);
        mBinding.toolbarRight.setOnClickListener(onClickListener);
    }


}
