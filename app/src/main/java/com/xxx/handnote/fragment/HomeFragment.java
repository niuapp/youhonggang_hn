package com.xxx.handnote.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xxx.handnote.R;
import com.xxx.handnote.fragment.main_viewpager_fragment.MainViewPagerFragmentFactory;
import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.view.M_TextView;

/**
 * Created by niuapp on 2016/7/10 19:39.
 * Project : youhonggang_hn.
 * Email : *******
 * --> 首页
 * ViewPager一个
 */
public class HomeFragment extends BaseFragment {
    private View contentView;

    private RelativeLayout mTitleLayout;
    private ImageView mButtonSearch;
    private LinearLayout mIndicatorLayout;
    private M_TextView mIndicatorAll;
    private M_TextView mIndicatorGroup;
    private ImageView mButtonTime;
    private ViewPager mHomeViewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;


    @Override
    protected View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = UIUtils.inflate(R.layout.fragment_home);

        mTitleLayout = (RelativeLayout) contentView.findViewById(R.id.titleLayout);
        mButtonSearch = (ImageView) contentView.findViewById(R.id.button_search);
        mIndicatorLayout = (LinearLayout) contentView.findViewById(R.id.indicatorLayout);
        setClickEvent(mIndicatorAll = (M_TextView) contentView.findViewById(R.id.indicator_all));
        setClickEvent(mIndicatorGroup = (M_TextView) contentView.findViewById(R.id.indicator_group));
        mButtonTime = (ImageView) contentView.findViewById(R.id.button_time);
        mHomeViewPager = (ViewPager) contentView.findViewById(R.id.home_viewPager);

        return contentView;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

        if (homeViewPagerAdapter == null) {
            homeViewPagerAdapter = new HomeViewPagerAdapter(getFragmentManager());
            mHomeViewPager.setAdapter(homeViewPagerAdapter);//设置适配器

            mHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {

                    //设置选择位置
                    if (mIndicatorAll != null && mIndicatorGroup != null) {
                        switch (position) {
                            case 0:
                                mIndicatorAll.setSelected(true);
                                mIndicatorGroup.setSelected(false);
                                break;
                            case 1:
                                mIndicatorAll.setSelected(false);
                                mIndicatorGroup.setSelected(true);
                                break;
                        }
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            //初始化位置
            mHomeViewPager.setCurrentItem(0);
            if (mIndicatorAll != null && mIndicatorGroup != null) {
                mIndicatorAll.setSelected(true);
                mIndicatorGroup.setSelected(false);
            }
        }
    }


    /**
     * 设置点击监听
     * @param view
     */
    private void setClickEvent(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.indicator_all: //切换到 0页
                        try {
                            if (mHomeViewPager.getCurrentItem() == 1) {
                                mHomeViewPager.setCurrentItem(0, true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.indicator_group: //切换到 1页
                        try {
                            if (mHomeViewPager.getCurrentItem() == 0) {
                                mHomeViewPager.setCurrentItem(1, true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }


    /**
     * 首页ViewPager适配器
     */
    private class HomeViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] pagerItemArr = UIUtils.getStringArray(R.array.homeViewPagerItemArr);

        public HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pagerItemArr.length;
        }

        @Override
        public Fragment getItem(int position) {
            return MainViewPagerFragmentFactory.createFragment(position);
        }
    }
}
