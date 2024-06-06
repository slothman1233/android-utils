package cjjc.plugin.cjjcMvp.widget.mvp

//自定义布局组件
fun mvpWidgetKt(
    mRootPackageName:String?,  //全路径
    mApplicationId:String?,  //工程ID包名
    mActivityPackageName:String, //创建的文件名
    mPageName:String, //页面名
    mWidgetLayoutName:String //布局文件名
)="""

package ${mRootPackageName}.${mActivityPackageName};

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.cjjc.base_lib.view.widget.BaseWidgetMvp;
import ${mApplicationId}.R;

public class ${mPageName}Widget extends BaseWidgetMvp implements ${mPageName}Interface.View {


    public ${mPageName}Widget(Context context) {
        this(context, null);
    }

    public ${mPageName}Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ${mPageName}Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        initView(context);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
    //初始化自定义属性
    //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.${mPageName}Style);
    //typedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.${mWidgetLayoutName}, this);
    }
}

"""

//无布局纯组件
fun mvpWidgetNoLayoutKt(
    mRootPackageName:String?,  //全路径
    mApplicationId:String?,  //工程ID包名
    mActivityPackageName:String, //创建的文件名
    mPageName:String, //页面名
)="""

package ${mRootPackageName}.${mActivityPackageName};

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.cjjc.base_lib.view.widget.BaseWidgetMvp;
import ${mApplicationId}.R;

public class ${mPageName}Widget extends BaseWidgetMvp implements ${mPageName}Interface.View {


    public ${mPageName}Widget(Context context) {
        this(context, null);
    }

    public ${mPageName}Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ${mPageName}Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        initView(context);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
    //初始化自定义属性
    //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.${mPageName}Style);
    //typedArray.recycle();
    }

    private void initView(Context context) {
    }
}

"""