package com.cjjc.lib_widget.wid_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * ViewPager 滑动无缝切换
 */
public class AnimSwipeViewPager extends ViewPager {
    private int DEFAULT_SCROLLER_DURATION = 1000;

    public AnimSwipeViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AnimSwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        setPageTransformer(true, new LiquidSwipePageTransformer());
        int scrollerDuration = DEFAULT_SCROLLER_DURATION;
        setDuration(scrollerDuration);
    }

    private void setDuration(int scrollerDuration) {
        DEFAULT_SCROLLER_DURATION=scrollerDuration;
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), DEFAULT_SCROLLER_DURATION);
            mScroller.set(this,scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class LiquidSwipePageTransformer implements PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (page instanceof MySwipeLayout) {
                if (position < -1) {
                    ((MySwipeLayout) page).revealForPercentage(0f);
                } else if (position < 0) {
                    page.setTranslationX(-(page.getWidth() * position));
                    ((MySwipeLayout) page).revealForPercentage(100 - Math.abs(position * 100));
                } else if (position <= 1) {
                    ((MySwipeLayout) page).revealForPercentage(100f);
                    page.setTranslationX(-(page.getWidth() * position));
                }
            }
        }
    }
}
