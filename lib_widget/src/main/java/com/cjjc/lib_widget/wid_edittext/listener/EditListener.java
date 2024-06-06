package com.cjjc.lib_widget.wid_edittext.listener;

public class EditListener {

    /**
     * 回调监听
     */
    public interface EditPwdOnListener {
        //眼睛按钮回调
        void onClickPwdIsVisible(boolean isVisible);
    }

    /**
     * 回调监听
     */
    public interface EditCodeOnListener {
        //获取验证码按钮回调
        void onClick();
    }
    /**
     * 回调监听
     */
    public interface EditForgetPwdOnListener {
        //忘记密码按钮回调
        void onClick();
    }



}
