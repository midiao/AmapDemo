package com.cb8695.amapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.amap.api.navi.AMapHudView;
import com.amap.api.navi.AMapHudViewListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.cb8695.amapdemo.util.Utils;

import java.util.ArrayList;

public class HUDNaviActivity extends Activity implements AMapHudViewListener, AMapNaviListener {

    private final static String TAG = HUDNaviActivity.class.getSimpleName();

    private AMapHudView mAMapHudView;
    private AMapNavi mAMapNavi;

    //起点终点
    private NaviLatLng mNaviStart = new NaviLatLng(30.2737381907, 120.1417425207);
    private NaviLatLng mNaviEnd = new NaviLatLng(30.2901718969, 120.1166317251);
    //起点终点列表
    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();

    //记录上一次导航方向
    private int lastIconType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAMapNavi = AMapNavi.getInstance(this);
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setEmulatorNaviSpeed(150);
        setContentView(R.layout.activity_hudnavi);
        mAMapHudView = (AMapHudView) findViewById(R.id.hudview);
        mAMapHudView.setHudViewListener(this);
    }

    @Override
    public void onHudViewCancel() {
        mAMapNavi.stopNavi();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapHudView.onResume();
        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);
        mAMapNavi.calculateDriveRoute(mStartPoints, mEndPoints, null, PathPlanningStrategy.DRIVING_DEFAULT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapHudView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAMapHudView.onDestroy();
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }
    /**
     * 返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mAMapNavi.stopNavi();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {
        AMapNavi.getInstance(this).startNavi(AMapNavi.EmulatorNaviMode);
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        if (lastIconType != naviInfo.getIconType()){
            lastIconType = naviInfo.getIconType();
            Intent updateIntent = new Intent(Utils.ACTION_UPDATE_ALL);
            updateIntent.putExtra("ICONTYPE", lastIconType);
            this.sendBroadcast(updateIntent);
            Log.i(TAG, "The iconType is " + naviInfo.getIconType());
            Log.i(TAG, "The currentTime is " +  + System.currentTimeMillis());
        }
        if(Utils.isHome(getApplicationContext())){
            Log.i(TAG, "The screen is home");
        }else {
            Log.i(TAG, "The screen is not home");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }
    private String getDirection(int iconType){
        switch (iconType){
            case 1:
                return "自车图标";
            case 2:
                return "左转";
            case 3:
                return "右转";
            case 4:
                return "左前方";
            case 5:
                return "右前方";
            case 6:
                return "左后方";
            case 7:
                return "右后方";
            case 8:
                return "左转掉头";
            case 9:
                return "直行";
            default:
                return "暂不显示";
        }
    }
}
