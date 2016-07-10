package com.xxx.handnote.fragment;


import com.xxx.handnote.base.BaseApplication;
import com.xxx.handnote.base.Const;

/**
 * 主页 我的
 */
public class MainFragmentFactory {

	public static BaseFragment createFragment(int position) {
		
		// 从缓存中取出
		BaseFragment fragment = BaseApplication.getmMainFragments().get(position);
		if (null == fragment) {
			switch (position) {
			case Const.PAGER_HOME:
				fragment = new HomeFragment();
				break;
			case Const.PAGER_MY:
				fragment = new MyFragment();
				break;

			default:
				break;
			}
			// 把frament加入到缓存中
			BaseApplication.getmMainFragments().put(position, fragment);
		}
		return fragment;
	}
}
