package com.cb8695.amapdemo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb8695 on 2016/2/29.
 */
public class Utils {
    // 更新 widget 的广播对应的action
    public final static String ACTION_UPDATE_ALL = "com.cb8695.widget.UPDATE_ALL";
    /**
     * 判断设备是否处在Launcher
     * @param context
     * @return
     */
    public static boolean  isHome(Context context) {
        List<String> names = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfos) {
            names.add(resolveInfo.activityInfo.packageName);
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        return names.contains(runningTaskInfos.get(0).topActivity.getPackageName());
    }
}
