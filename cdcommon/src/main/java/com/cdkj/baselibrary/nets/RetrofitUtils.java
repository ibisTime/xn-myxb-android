package com.cdkj.baselibrary.nets;


import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

/**
 * 服务器api
 * Created by Administrator on 2016/9/1.
 */
public class RetrofitUtils {

    private static Retrofit retrofitInstance = null;

    //url类型
    public static final int RELEASE = 0; //正式环境
    public static final int DEBUG = 1;//研发环境
    public static final int TEST = 2;//测试环境


    private RetrofitUtils() {
    }

    /**
     * 获取Retrofit实例
     *
     * @return Retrofit
     */
    private static Retrofit getInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                     .baseUrl(getBaseURL(TEST)) //  需要更换七牛URL
                    .client(OkHttpUtils.getInstance())
                    .addConverterFactory(FastJsonConVerter.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    /**
     * 创建Retrofit请求Api
     *
     * @param clazz Retrofit Api接口
     * @return api实例
     */
    public static <T> T createApi(Class<T> clazz) {
        return getInstance().create(clazz);
    }

    public static BaseApiServer getBaseAPiService() {
        return createApi(BaseApiServer.class);
    }

    /**
     * 获取URL  根据版本切换不同版本
     *
     * @return
     */
    public static String getBaseURL(int urlType) {
        switch (urlType) {
            case DEBUG:
                return "http://120.26.6.213:4401/forward-service/";//研发环境
            case TEST:
                return "http://47.96.161.183:4401/forward-service/";//测试
        }
        return "http://47.98.107.162:4401/forward-service/";//正式环境
    }

    /**
     * 添加公共请求参数
     *
     * @return
     */
    public static Map getRequestMap() {
        Map map = new HashMap();
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        return map;
    }

}
