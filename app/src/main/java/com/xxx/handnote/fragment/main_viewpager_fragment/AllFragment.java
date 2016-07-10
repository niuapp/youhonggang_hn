package com.xxx.handnote.fragment.main_viewpager_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.R;
import com.xxx.handnote.fragment.BaseFragment;
import com.xxx.handnote.utils.UIUtils;

/**
 * Created by niuapp on 2016/7/10 19:39.
 * Project : youhonggang_hn.
 * Email : *******
 * --> 首页
 *      全部Fragment
 */
public class AllFragment extends BaseFragment {
    private View contentView;


    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = UIUtils.inflate(R.layout.fragment_all);


        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
    }
}
