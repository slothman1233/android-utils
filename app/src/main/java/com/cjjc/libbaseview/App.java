package com.cjjc.libbaseview;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cjjc.lib_tools.util.database.MMkvHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        MMkvHelper.init(this);
    }
}
