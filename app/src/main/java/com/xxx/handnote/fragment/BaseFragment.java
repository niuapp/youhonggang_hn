package com.xxx.handnote.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.utils.ViewUtils;


/**
 * Created by Administrator on 2015/12/24.
 */
public abstract class BaseFragment extends Fragment {

    private View fragmentContentView;
    private String className;
    protected View viewPagerTab;

    // 初始化布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isCanLoad = false;
        //当前类名
        className = this.getClass().getSimpleName();

        //缓存View对象
        if (fragmentContentView == null) {
            fragmentContentView = initFragmentView(inflater, container, savedInstanceState);
        } else {
            //被ViewPager复用不能有父控件，移除
            ViewUtils.removeSelfFromParent(fragmentContentView);
        }

        return fragmentContentView;
    }


    /**
     * 是否可见
     */
    private boolean isCanLoad = false;
    private boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        isVisible = true;
        if (isCanLoad && isVisible) {
            initViewData(null);
        }
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
        isVisible = false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 对控件进行操作
//        isCanLoad = true;
//        if (isCanLoad && isVisible) {
            initViewData(null);
//        }
    }

    /**
     * 子类重写这个方法 初始化布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 内容 View
     */

    protected abstract View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 子类重写这个方法 在Fragment可见时初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initViewData(Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();

//        if (TextUtils.isEmpty(className)) {
//            className = this.getClass().getSimpleName();
//        }
//        MobclickAgent.onPageStart(className);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (TextUtils.isEmpty(className)) {
            className = this.getClass().getSimpleName();
        }

        UIUtils.showToastSafe(className);
//        MobclickAgent.onPageEnd(className);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 获取当前Fragment的RecyclerView
     *
     * @return 如果没有实现，就返回null
     */

    public RecyclerView getCurrentRecyclerView() {
        return null;
    }


    public void setViewPagerTab(View viewPagerTab) {
        this.viewPagerTab = viewPagerTab;
    }
}
