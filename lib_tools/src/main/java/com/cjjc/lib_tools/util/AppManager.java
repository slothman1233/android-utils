package com.cjjc.lib_tools.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * 管理所有Activity
 */
public class AppManager {

    public static Stack<Activity> activityStack;
    public static AppManager instance;

    private AppManager() {
    }

    /**
     * 获取AppManager单例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null)
                    instance = new AppManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 从堆栈中移除该Activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.remove(activity);
    }

    /**
     * 是否包含某Activity
     */
    public boolean containActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前Activity
     */
    public Activity getCurrentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity activity) {
        finishActivity(activity, true);
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity activity, boolean needRemove) {
        if (activity != null) {
            if (needRemove) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名Activity
     */
    public void finishActivity(Class<?> activityClass) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(activityClass)) {
                iterator.remove();
                finishActivity(activity, false);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null) {
                iterator.remove();
                finishActivity(activity, false);
            }
        }
    }

    /**
     * 结束所有Activity 除了cls这个activity之外
     */
    public void finishAllActivityExceptClsActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(cls)) {
                iterator.remove();
                finishActivity(activity, false);
            }
        }
    }

    /**
     * 结束所有Activity 除了cls这个activity之外
     * 并保证整个activity管理栈中只存在cls这一个activity
     */
    public void finishAllActivityExceptClsActivityOnly(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(cls)) {
                iterator.remove();
                finishActivity(activity, false);
            }
        }
        Iterator<Activity> iterator1 = activityStack.iterator();
        while (iterator1.hasNext()) {
            if (activityStack.size() == 1) return;
            Activity activity = iterator1.next();
            iterator1.remove();
            finishActivity(activity, false);
        }

//        int count = 0;
//        List<Integer> pos = new ArrayList<>();
//        for (int i = 0, size = activityStack.size(); i < size; i++) {
//            Activity activity = activityStack.get(i);
//            if (activity != null && !activity.getClass().equals(cls)) {
//                activity.finish();
//            } else {
//                count++;
//                pos.add(i);
//            }
//        }
//        for (int i = 0; i < count - 1; i++) {
//            Activity activity = activityStack.get(pos.get(i));
//            if (activity != null) {
//                activity.finish();
//            }
//        }
    }

    /**
     * 结束所有Activity 除了cls和cls2这个主activity之外
     */
    public void finishAllActivityExceptParamActivity(Class<?> cls, Class<?> cls2) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(cls) && !activity.getClass().equals(cls2)) {
                iterator.remove();
                finishActivity(activity, false);
            }
        }

//        for (int i = 0, size = activityStack.size(); i < size; i++) {
//            Activity activity = activityStack.get(i);
//            if (activity != null && !activity.getClass().equals(cls) && !activity.getClass().equals(cls2)) {
//                activity.finish();
//            }
//        }
    }
}
