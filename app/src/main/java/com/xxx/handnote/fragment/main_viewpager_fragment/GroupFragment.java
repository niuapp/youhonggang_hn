package com.xxx.handnote.fragment.main_viewpager_fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.R;
import com.xxx.handnote.adapter.HomeGroupListAdapter;
import com.xxx.handnote.fragment.BaseFragment;
import com.xxx.handnote.utils.UIUtils;

/**
 * Created by niuapp on 2016/7/10 19:42.
 * Project : youhonggang_hn.
 * Email : *******
 * --> 首页
 *      分组Fragment
 */
public class GroupFragment extends BaseFragment {
    private View contentView;

    private RecyclerView recyclerView;
    private HomeGroupListAdapter homeGroupListAdapter;

    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = UIUtils.inflate(R.layout.fragment_group);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager); //竖直两列 头跨两列
        recyclerView.invalidateItemDecorations();

        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        if (homeGroupListAdapter == null){
            homeGroupListAdapter = new HomeGroupListAdapter();
            recyclerView.setAdapter(homeGroupListAdapter);
        }
    }
}
