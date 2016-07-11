package com.xxx.handnote.bean.callback;


import com.google.gson.Gson;
import com.xxx.handnote.bean.HomeListBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by niuapp on 2016/7/8 18:38.
 * Project : HandNote.
 * Email : *******
 * --> 首页接口回调
 */
public abstract class HomeListBeanCallBack extends Callback<HomeListBean> {
    @Override
    public HomeListBean parseNetworkResponse(Response response, int id) throws Exception {
        String json = response.body().string();

        HomeListBean homeListBean = null;
        try {
            homeListBean = new Gson().fromJson(json, HomeListBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return homeListBean;
    }
}
