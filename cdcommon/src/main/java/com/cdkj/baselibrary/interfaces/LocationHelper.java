package com.cdkj.baselibrary.interfaces;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;

import com.cdkj.baselibrary.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位辅助类 判断是否有定位权限
 * <p>
 * 使用注意 必须要调用onRequestPermissionsResult用于获取权限回调
 * <p>
 * 必须要在页面关闭时调用destroyLocation方法用于关闭一些东西 比如在Activity的onDestroy中调用
 * <p>
 * Created by cdkj on 2017/11/7.
 */

public class LocationHelper {

    private LocationInterface mLocationInterface;//定位实现接口

    private PermissionHelper mPreHelper;//权限请求

    private LocationCallBackListener mLocationCallBackListener;//定位回调

    //需要进行定位功能的权限
    private List<String> needLocationPermissions = new ArrayList<>();

    public LocationHelper(@NonNull Object obj, @NonNull LocationInterface locationInterface, @NonNull LocationCallBackListener locationCallBackListener) {
        this.mLocationInterface = locationInterface;
        this.mLocationInterface.init(locationCallBackListener);
        this.mLocationCallBackListener = locationCallBackListener;
        setLocationPermissions();
        mPreHelper = new PermissionHelper(obj);
    }


    /**
     * 添加还需要申请的权限
     *
     * @param permissions
     */
    public void addPermissions(String permissions) {
        needLocationPermissions.add(permissions);
    }

    /**
     * 需要的定位权限
     */
    private void setLocationPermissions() {
        needLocationPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        needLocationPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        needLocationPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
            return;
        }

        mLocationInterface.startLocation();
    }

    /**
     * 请求定位权限
     */
    private void requestPermissions() {
        mPreHelper.requestPermissions(new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                mLocationInterface.startLocation();
            }

            @Override
            public void doAfterDenied(String... permission) {
                mLocationCallBackListener.noPermissions();
            }
        }, getPermissions());
    }

    private String[] getPermissions() {
        final int size = needLocationPermissions.size();
        String[] arr = (String[]) needLocationPermissions.toArray(new String[size]);
        return arr;
    }

    /**
     * 关闭定位
     */
    public void stopLocation() {
        mLocationInterface.stopLocation();
    }

    /**
     * 关闭一些内容
     */
    public void destroyLocation() {
        mLocationInterface.destroyLocation();
    }

    /**
     * 获取权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPreHelper == null) return;
        mPreHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
