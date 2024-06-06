package com.cjjc.libcatch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cjjc.lib_carth.crash.NeverCrash;
import com.cjjc.lib_tools.util.log.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.init(true, "=_=");
        init();
    }

    public void cla(View view) {
        int i = 1 / 0;
    }

    public void init() {
        NeverCrash.getInstance()
                .setDebugMode(true)
                .setMainCrashHandler((t, e) -> {
                    // todo 跨线程操作时注意线程调度回主线程操作
                    Log.e("=_=", "主线程异常");// 此处log只是展示，当debug为true时，主类内部log会打印异常信息
                    // todo 此处做你的日志记录即可
                })
                .setUncaughtCrashHandler((t, e) -> {
                    // todo 跨线程操作时注意线程调度回主线程操作
                    Log.e("=_=", "子线程异常");// 此处log只是展示，当debug为true时，主类内部log会打印异常信息
                    // todo 此处做你的日志记录即可
                })
                .register();
    }
}