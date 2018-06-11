package com.cdkj.myxb.module.user;

import android.support.annotation.StringDef;

import com.cdkj.myxb.BaseApplication;
import com.cdkj.myxb.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cdkj on 2018/2/22.
 */

public class UserHelper {
    /*C:经销商,P:平台用户,S:专家,A:顾问,M:人力,*/

    public static final String C = "C"; // 经销商
    public static final String S = "S"; // 专家
    public static final String T = "T"; // 服务团队
    public static final String B = "B"; // 合伙人


    @StringDef({C, S, T, B})
    @Retention(RetentionPolicy.SOURCE)
    public @interface userType {
    }


    /**
     * 根据用户类型获取预约标题
     *
     * @param state
     * @return
     */
    public static String getAppointmentTypeByState(String state) {

        switch (state) {
            case T:
                return "服务团队预约";
            case S:
                return "专家预约";
        }

        return "预约";

    }

    /**
     * 根据kind获取类型
     *
     * @param kind
     * @return
     */
    public static String getUserTypeByKind(String kind) {
        switch (kind) {
            case UserHelper.C:
                return BaseApplication.getInstance().getString(R.string.mry);
            case UserHelper.S:
                return BaseApplication.getInstance().getString(R.string.experts);
            case UserHelper.T:
                return BaseApplication.getInstance().getString(R.string.shopper);
            case UserHelper.B:
                return BaseApplication.getInstance().getString(R.string.partner);
        }
        return "";
    }


    public static int getLevelIconWithUserKind(String kind, String level) {

        switch (kind) {
            case UserHelper.S: //专家
                switch (level){
                    case "zero_level":
                        return R.drawable.user_atlas_level_0_w;

                    case "zs_spec":
                        return R.drawable.user_atlas_level_zs_w;

                    case "sx_spec":
                        return R.drawable.user_atlas_level_sx_w;

                    case "ty_spec":
                        return R.drawable.user_atlas_level_sx_w;
                }
                break;

            case UserHelper.C: //经销商
                switch (level){
                    case "zero_level":
                        return R.drawable.user_atlas_level_0_w;

                    case "bj_service":
                        return R.drawable.user_atlas_level_1_w;

                    case "zs_service":
                        return R.drawable.user_atlas_level_2_w;

                    case "hz_service":
                        return R.drawable.user_atlas_level_hz_w;
                }
                break;


            case UserHelper.T: //服务团队
            case UserHelper.B: //合伙人

                switch (level){
                    case "zero_level":
                        return R.drawable.user_atlas_level_0_w;

                    case "bj_partner":
                        return R.drawable.user_atlas_level_1_w;

                    case "zs_partner":
                        return R.drawable.user_atlas_level_2_w;

                    case "sz_partner":
                        return R.drawable.user_atlas_level_3_w;

                    case "hg_partner":
                        return R.drawable.user_atlas_level_4_w;

                    case "shuanghg_partner":
                        return R.drawable.user_atlas_level_5_w;

                    case "sanhg_partner":
                        return R.drawable.user_atlas_level_6_w;
                }
                break;
        }
        return R.drawable.user_atlas_level_0_w;

    }

    /**
     * 根据kind获取类型
     *
     * @param kind
     * @return
     */
    public static String getLevelWithUserKind(String kind, String level) {
        switch (kind) {
            case UserHelper.S: //专家
                switch (level){
                    case "zero_level":
                        return "初级";

                    case "zs_spec":
                        return "资深";

                    case "sx_spec":
                        return "首席";

                    case "ty_spec":
                        return "首席";
                }
                break;

            case UserHelper.C: //经销商
                switch (level){
                    case "zero_level":
                        return "初级";

                    case "bj_service":
                        return "白金";

                    case "zs_service":
                        return "钻石";

                    case "hz_service":
                        return "黄钻";
                }
                break;


            case UserHelper.T: //服务团队
            case UserHelper.B: //合伙人

                switch (level){
                    case "zero_level":
                        return "初级";

                    case "bj_partner":
                        return "白金";

                    case "zs_partner":
                        return "钻石";

                    case "sz_partner":
                        return "双钻";

                    case "hg_partner":
                        return "皇冠";

                    case "shuanghg_partner":
                        return "双皇冠";

                    case "sanhg_partner":
                        return "三皇冠";
                }
                break;
        }
        return "";
    }

}
