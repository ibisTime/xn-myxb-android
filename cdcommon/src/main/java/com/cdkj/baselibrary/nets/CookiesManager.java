package com.cdkj.baselibrary.nets;




import com.cdkj.baselibrary.CdApplication;
import com.cdkj.baselibrary.utils.LogUtil;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore = new PersistentCookieStore(CdApplication.getContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                if(url!=null && item!=null)
                {
                    cookieStore.add(url, item);
                }
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);

        for (Cookie cookie : cookies) {
            LogUtil.E("Cookie"+cookie.toString());
        }

        return cookies;
    }
}