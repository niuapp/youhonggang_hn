package com.xxx.handnote.fragment.main_viewpager_fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.R;
import com.xxx.handnote.adapter.HomeAllListAdapter;
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

    private RecyclerView recyclerView;
    private HomeAllListAdapter homeAllListAdapter;

    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = UIUtils.inflate(R.layout.fragment_all);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                try {
//                    RecyclerView.ViewHolder viewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
//                    if (viewHolderForAdapterPosition instanceof HomeAllListAdapter.HomeAllListViewHolder){
//                        HomeAllListAdapter.HomeAllListViewHolder homeAllListViewHolder = (HomeAllListAdapter.HomeAllListViewHolder) viewHolderForAdapterPosition;
//
//                        if (homeAllListViewHolder.mDate != null){
//                            CharSequence text = homeAllListViewHolder.mDate.getText();
//                            if (!TextUtils.isEmpty(text)){
//                                LogUtils.d("----------> " + text.toString().trim());
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.invalidateItemDecorations();



        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        if (homeAllListAdapter == null){
            homeAllListAdapter = new HomeAllListAdapter();
            recyclerView.setAdapter(homeAllListAdapter);
        }

    }

}
