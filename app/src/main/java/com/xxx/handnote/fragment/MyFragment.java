package com.xxx.handnote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.R;
import com.xxx.handnote.utils.UIUtils;

/**
 * Created by niuapp on 2016/7/10 19:42.
 * Project : youhonggang_hn.
 * Email : *******
 * --> 我的
 *      一个列表
 */
public class MyFragment extends BaseFragment {
    private View contentView;

    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = UIUtils.inflate(R.layout.fragment_my);

        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

    }
}
