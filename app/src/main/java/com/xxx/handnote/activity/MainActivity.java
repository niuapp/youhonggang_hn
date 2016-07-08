package com.xxx.handnote.activity;

import android.view.View;
import android.widget.TextView;

import com.xxx.handnote.R;
import com.xxx.handnote.base.BaseActivity;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {

    }

    @Override
    protected View getContentView() {
        return UIUtils.inflate(R.layout.activity_test);
    }

    @Override
    protected void initView() {
        TextView textView = (TextView) findViewById(R.id.text);

        if (textView != null){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpUtils.get().url(Const.BASE_URL).build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    UIUtils.showToastSafe(response);
                                }
                            });
                }
            });
        }

    }
}
