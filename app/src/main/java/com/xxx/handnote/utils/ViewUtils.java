package com.xxx.handnote.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.PopupWindow;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewUtils {
	/** 把自身从父View中移除 */
	public static void removeSelfFromParent(View view) {
		if (view != null) {
			ViewParent parent = view.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView(view);
			}
		}
	}

	/** 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂 */
	public static void requestLayoutParent(View view, boolean isAll) {
		ViewParent parent = view.getParent();
		while (parent != null && parent instanceof View) {
			if (!parent.isLayoutRequested()) {
				parent.requestLayout();
				if (!isAll) {
					break;
				}
			}
			parent = parent.getParent();
		}
	}

	/** 判断触点是否落在该View上 */
	public static boolean isTouchInView(MotionEvent ev, View v) {
		int[] vLoc = new int[2];
		v.getLocationOnScreen(vLoc);
		float motionX = ev.getRawX();
		float motionY = ev.getRawY();
		return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
	}

	/** FindViewById的泛型封装，减少强转代码 */
	public static <T extends View> T findViewById(View layout, int id) {
		return (T) layout.findViewById(id);
	}

	/**
	 * ViewPager 滚动速度设置
	 *
	 * ViewPagerScroller scroller = new ViewPagerScroller(context);
	 * scroller.setScrollDuration(2000);
	 * scroller.initViewPagerScroll(viewPager);
	 *
	 */
	public static class ViewPagerScroller extends Scroller {
		private int mScrollDuration = 2000;             // 滑动速度

		/**
		 * 设置速度速度
		 * @param duration
		 */
		public void setScrollDuration(int duration){
			this.mScrollDuration = duration;
		}

		public ViewPagerScroller(Context context) {
			super(context);
		}

		public ViewPagerScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}



		public void initViewPagerScroll(ViewPager viewPager) {
			try {
				Field mScroller = ViewPager.class.getDeclaredField("mScroller");
				mScroller.setAccessible(true);
				mScroller.set(viewPager, this);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * UIUtils.setPopupWindowTouchModal(popupWindow, false);　
	 * 该popupWindow外部的事件就可以传递给下面的Activity了。
	 *
	 * Set whether this window is touch modal or if outside touches will be sent
	 * to
	 * other windows behind it.
	 *
	 */
	public static void setPopupWindowTouchModal(PopupWindow popupWindow,
												boolean touchModal) {
		if (null == popupWindow) {
			return;
		}
		Method method;
		try {

			method = PopupWindow.class.getDeclaredMethod("setTouchModal",
					boolean.class);
			method.setAccessible(true);
			method.invoke(popupWindow, touchModal);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
