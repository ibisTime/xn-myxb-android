package com.cdkj.myxb.weight;

import com.cdkj.baselibrary.utils.SPUtils;
import com.cdkj.myxb.BaseApplication;

/**
 * 搜索内容保存
 * Created by cdkj on 2017/10/24.
 */

public class SearchSaveUtils {

    /**
     * 保存用户搜索的内容
     *
     * @param info
     */
    public static void saveSearchInfo(String info) {
        SPUtils.put(BaseApplication.getInstance(), "SearchInfo", info);
    }

    /**
     * 获取用户搜索的内容
     */
    public static String getSaveSearchInfo() {
        return SPUtils.getString(BaseApplication.getInstance(), "SearchInfo", "暂无");
    }

    /**
     * 清除用户搜索的内容
     */
    public static void clearSearchInfo() {
        SPUtils.remove(BaseApplication.getInstance(), "SearchInfo");
    }


}
