package com.hnhy.cropimglibrary.scrollerproxy;

import android.content.Context;
import android.os.Build;

public abstract class ScrollerProxy {
    public static ScrollerProxy getScroller(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            return new PreGingerScroller(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new GingerScroller(context);
        } else {
            return new IcsScroller(context);
        }
    }

    public abstract void fling(int startX, int startY, int velocityX, int velocityY,
                               int minX, int maxX, int minY, int maxY, int overX, int overY);

    public abstract boolean computeScrollOffset();

    public abstract boolean isFinished();

    public abstract void forceFinished(boolean finished);

    public abstract int getCurrX();

    public abstract int getCurrY();
}
