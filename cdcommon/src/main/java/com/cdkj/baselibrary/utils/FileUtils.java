package com.cdkj.baselibrary.utils;

import android.os.Environment;
import android.util.Log;

import com.cdkj.baselibrary.appmanager.MyCdConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cdkj on 2018/2/6.
 */

public class FileUtils {

    /**
     * 检查储存卡是否可用
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 取SD卡路径
     **/
    public static String getSDPath() {
        File sdDir = null;

        if (FileUtils.isExternalStorageWritable()) {
            sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
        }
        if (sdDir != null) {
            return sdDir.getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 获取相册路径 如果没有则创建
     *
     * @param albumName
     * @return
     */
    public static File getAlbumStorageDir(String albumName) {
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), albumName);        ///storage/sdcard/Pictures/
        File file = new File(FileUtils.getSDPath(), albumName);  //storage/sdcard/
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("album", "Directory not created");
            }
        }

        return file;
    }

    /**
     * 保存相册图片
     *
     * @param picName 图片名
     * @return
     */
    public static File saveAlbumPic(String picName) {
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date()) + picName + ".jpg";
        File file = new File(FileUtils.getAlbumStorageDir(MyCdConfig.ALBUMNAME), filename);

        return file;

    }


}
