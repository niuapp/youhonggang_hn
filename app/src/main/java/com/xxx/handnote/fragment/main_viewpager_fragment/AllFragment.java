package com.xxx.handnote.fragment.main_viewpager_fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private String json = "{\n" +
            "    \"code\": \"1000\",\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"res\": [\n" +
            "        {\n" +
            "            \"id\": \"1\",\n" +
            "            \"catname\": \"人物\",\n" +
            "            \"pic\": \"\",\n" +
            "            \"addtime\": \"1466845164\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"3\",\n" +
            "            \"catname\": \"动物\",\n" +
            "            \"pic\": \"m.shouji.cc/uploads/20160626/576eaaaf31a4b.jpg\",\n" +
            "            \"addtime\": \"1466870083\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"2\",\n" +
            "            \"catname\": \"风景\",\n" +
            "            \"pic\": \"m.shouji.cc/uploads/20160625/576ea9cd82248.jpg\",\n" +
            "            \"addtime\": \"1466870047\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private View contentView;

    private RecyclerView recyclerView;

    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = UIUtils.inflate(R.layout.fragment_all);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.invalidateItemDecorations();
//        recyclerView.


        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

    }

}
