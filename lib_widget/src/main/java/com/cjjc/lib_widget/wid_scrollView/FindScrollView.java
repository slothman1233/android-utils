package com.cjjc.lib_widget.wid_scrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class FindScrollView extends ScrollView {
    private float mLastXIntercept = 0f;
    private float mLastYIntercept = 0f;

    public FindScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
