package com.cjjc.lib_widget.wid_viewpager;

/**
 */
public interface MySwipeLayout {
    public ClipPathProvider clipPathProvider();

    public Float currentRevealPercent();

    public void revealForPercentage(Float percent);
}
