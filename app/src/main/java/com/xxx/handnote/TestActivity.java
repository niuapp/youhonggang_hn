package com.xxx.handnote;

import android.view.View;
import android.widget.TextView;

import com.xxx.handnote.base.BaseActivity;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by niuapp on 2016/7/8 16:25.
 * Project : HandNote.
 * Email : *******
 * -->
 */
public class TestActivity extends BaseActivity {
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
