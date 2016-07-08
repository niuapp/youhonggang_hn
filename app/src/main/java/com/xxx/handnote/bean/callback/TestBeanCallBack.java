package com.xxx.handnote.bean.callback;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xxx.handnote.bean.TestBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by niuapp on 2016/7/8 18:38.
 * Project : HandNote.
 * Email : *******
 * -->
 */
public abstract class TestBeanCallBack extends Callback<TestBean> {
    @Override
    public TestBean parseNetworkResponse(Response response, int id) throws Exception {
        String json = response.body().string();

        TestBean testBean = null;
        try {
            testBean = new Gson().fromJson(json, TestBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return testBean;
    }
}
