package com.hnhy.cropimglibrary.gestures;

import android.view.MotionEvent;

public interface GestureDetector {
    public boolean onTouchEvent(MotionEvent ev);

    public boolean isDragging();

    public boolean isScaling();

    public void setOnGestureListener(OnGestureListener listener);
}
