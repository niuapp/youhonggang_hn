package com.xxx.handnote.fragment.main_viewpager_fragment;


import com.xxx.handnote.base.BaseApplication;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.fragment.BaseFragment;

/**
 * 主页 我的
 */
public class MainViewPagerFragmentFactory {

	public static BaseFragment createFragment(int position) {
		
		// 从缓存中取出
		BaseFragment fragment = BaseApplication.getmMainViewPagerFragments().get(position);
		if (null == fragment) {
			switch (position) {
			case Const.PAGER_HOME_ALL:
				fragment = new AllFragment();
				break;
			case Const.PAGER_HOME_GROUP:
				fragment = new GroupFragment();
				break;

			default:
				break;
			}
			// 把frament加入到缓存中
			BaseApplication.getmMainViewPagerFragments().put(position, fragment);
		}
		return fragment;
	}
}
