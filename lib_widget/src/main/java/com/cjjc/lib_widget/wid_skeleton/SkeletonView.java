package com.cjjc.lib_widget.wid_skeleton;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

import com.cjjc.lib_widget.wid_skeleton.master.SkeletonMaster;



public class SkeletonView extends SkeletonMaster {

    public SkeletonView(Context context) {
        super(context);
    }

    public SkeletonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkeletonView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SkeletonView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}