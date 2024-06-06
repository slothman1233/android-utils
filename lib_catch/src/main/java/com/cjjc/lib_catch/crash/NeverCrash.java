package com.cjjc.lib_catch.crash;

import android.os.Handler;
import android.os.Looper;

import com.cjjc.lib_tools.util.log.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 用途：全局异常拦截，防止APP崩溃
 * 异常日志会在控制台打印,仅在debug模式下
 * 生成环境错误日志,使用Bugly查看
 */
public class NeverCrash {

    private final static NeverCrash INSTANCE = new NeverCrash();

    private boolean debugMode;//是否是Debug模式
    private MainCrashHandler mainCrashHandler;
    private UncaughtCrashHandler uncaughtCrashHandler;

    private NeverCrash() {
    }

    public static NeverCrash getInstance() {
        return INSTANCE;
    }

    private synchronized MainCrashHandler getMainCrashHandler() {
        if (null == mainCrashHandler) {
            mainCrashHandler = (t, e) -> {
            };
        }
        return mainCrashHandler;
    }

    /**
     * 主线程发生异常时的回调，可用于打印日志文件
     * <p>
     * 注意跨线程操作的可能
     */
    public NeverCrash setMainCrashHandler(MainCrashHandler mainCrashHandler) {
        this.mainCrashHandler = mainCrashHandler;
        return this;
    }

    private synchronized UncaughtCrashHandler getUncaughtCrashHandler() {
        if (null == uncaughtCrashHandler) {
            uncaughtCrashHandler = (t, e) -> {
            };
        }
        return uncaughtCrashHandler;
    }

    /**
     * 子线程发生异常时的回调，可用于打印日志文件
     * <p>
     * 注意跨线程操作的可能
     */
    public NeverCrash setUncaughtCrashHandler(UncaughtCrashHandler uncaughtCrashHandler) {
        this.uncaughtCrashHandler = uncaughtCrashHandler;
        return this;
    }

    private boolean isDebugMode() {
        return debugMode;
    }

    /**
     * debug模式，会打印log日志，且toast提醒发生异常，反之则都没有
     */
    public NeverCrash setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    /**
     * 完成监听异常的注册
     *
     */
    public void register() {
        //主线程异常拦截
        new Handler(Looper.getMainLooper()).post(() -> {
            while (true) {
                try {
                    Looper.loop();
                } catch (Throwable e) {
                    if (isDebugMode()) {
                        handlerException(e);
                    }
                    getMainCrashHandler().mainException(Looper.getMainLooper().getThread(), e);
                }
            }
        });

        //子线程异常拦截
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            if (isDebugMode()) {
                handlerException(e);
            }
            getUncaughtCrashHandler().uncaughtException(t, e);
        });
    }

    //自定义错误处理器
    private boolean handlerException(Throwable ex) {
        if (ex == null) {  //如果已经处理过这个Exception,则让系统处理器进行后续关闭处理
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n------------Crash Log Begin--------------\n");
        //获取错误原因
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause!=null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        final String msg= writer.toString() ;
        stringBuffer.append(msg);
        stringBuffer.append("\n------------Crash Log End--------------\n");
        LogUtil.xLoge(stringBuffer.toString());
        return true;
    }

    public interface MainCrashHandler {
        void mainException(Thread t, Throwable e);
    }

    public interface UncaughtCrashHandler {
        void uncaughtException(Thread t, Throwable e);
    }
}