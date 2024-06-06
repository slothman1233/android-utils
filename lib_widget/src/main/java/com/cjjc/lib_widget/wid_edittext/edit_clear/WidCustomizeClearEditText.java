package com.cjjc.lib_widget.wid_edittext.edit_clear;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.cjjc.lib_tools.util.StringUtil;
import com.cjjc.lib_widget.R;

/**
 * 自定义EditText
 * 功能：自带清除功能
 *
 * 定义一个控件来放 EditText  做统一功能使用
 *
 */
public class WidCustomizeClearEditText extends AppCompatEditText {

    //背景Drawable样式
    private int editBackgroundResourceDrawable = R.drawable.shape_edit_text_statue;
    //是否显示清除按钮
    private boolean isShowClear = true;
    private String textContent; //当前输入内容
    private Drawable clearImg;//清除图标

    public WidCustomizeClearEditText(Context context) {
        super(context);
    }

    public WidCustomizeClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidCustomizeClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WidCustomizeClearEditText);

            editBackgroundResourceDrawable= typedArray.getResourceId(R.styleable.WidCustomizeClearEditText_editBackgroundResourceDrawable, R.drawable.shape_edit_text_statue);
            isShowClear=typedArray.getBoolean(R.styleable.WidCustomizeClearEditText_isShowClear,true);
            clearImg = typedArray.getDrawable(R.styleable.WidCustomizeClearEditText_clearImg);

            //释放资源
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化控件样式

        //设置背景样式
        setBackgroundResource(editBackgroundResourceDrawable);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textContent = text.toString();
        //聚焦+文本内容不为空
        setClearImgIsVisible(hasFocus() && !StringUtil.isBlank(textContent));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        //聚焦+文本内容不为空
        if (focused && !StringUtil.isBlank(textContent)) {
            setClearImgIsVisible(true);
        } else {
            setClearImgIsVisible(false);
        }
    }

    /**
     * 设置清除图标显示/隐藏
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setClearImgIsVisible(boolean isVisible) {
        if(!isShowClear){
            return;
        }
        if (isVisible) {
            if(clearImg==null){
                clearImg=getContext().getDrawable(R.drawable.iv_edit_clear);
            }
            setCompoundDrawablesWithIntrinsicBounds(null, null, clearImg, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


    /**
     * 监听按下清除按钮
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean isClean = event.getX() > getWidth() - getTotalPaddingRight() && event.getX() < getWidth() - getPaddingRight();
            if (isClean) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置是否显示 清除按钮
     * @param isShowClear
     */
    public void setIsShowClear(boolean isShowClear){
        this.isShowClear=isShowClear;
    }

}
