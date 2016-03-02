package com.cb8695.amapdemo;

/**
 * Created by cb8695 on 2016/3/1.
 */

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.cb8695.amapdemo.util.Utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
    private static Set idsSet = new HashSet();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // 每次 widget 被创建时，对应的将widget的id添加到set中
        for (int appWidgetId : appWidgetIds) {
            idsSet.add(appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(Utils.ACTION_UPDATE_ALL)) {
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet, intent);
        }
        super.onReceive(context, intent);
    }

    // widget被删除时调用
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id
        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(appWidgetId);
        }
        super.onDeleted(context, appWidgetIds);
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set, Intent intent) {
        // widget 的id
        int appID;
        int iconType;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();
            iconType = intent.getIntExtra("ICONTYPE", -1);
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            if (iconType == 2) {
                remoteView.setTextViewText(R.id.iconType, "左转");
                remoteView.setImageViewResource(R.id.directionImage, R.drawable.left);
            } else if (iconType == 3) {
                remoteView.setTextViewText(R.id.iconType, "右转");
                remoteView.setImageViewResource(R.id.directionImage, R.drawable.right);
            }else {
                remoteView.setTextViewText(R.id.iconType, "暂未定义");
                remoteView.setImageViewResource(R.id.directionImage, R.drawable.default_icon);
            }
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }
}