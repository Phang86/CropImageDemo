package com.hnhy.cropimagedemo.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BaseView<V extends ViewBinding> extends FrameLayout {

    protected Context mContext;
    protected V mBinding;

    public BaseView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mBinding = getViewBinding();
        TypedArray ta = mContext.obtainStyledAttributes(attrs, getAttrs());
        initView(ta);
        ta.recycle();
        addView(mBinding.getRoot());
    }

    protected abstract void initView(TypedArray ta);

    protected abstract V getViewBinding();

    protected abstract int[] getAttrs();
}

