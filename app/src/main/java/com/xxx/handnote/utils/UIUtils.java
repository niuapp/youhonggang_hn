package com.xxx.handnote.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.handnote.R;
import com.xxx.handnote.base.BaseActivity;
import com.xxx.handnote.base.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class UIUtils {

    private static TextView toastTextView;

    /**
     * 获取 全局Context
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 获取Activity记录集合
     *
     * @return
     */
    public static List<Activity> getActivityList() {
        return BaseApplication.getActivityList();
    }

    /**
     * dip -> px
     */
    public static int dip2Px(int dp) {
        //获取屏幕显示规格密度
        float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5);
        return px;
    }

    /**
     * px -> dip
     */
    public static int px2Dip(int px) {
        //获取屏幕显示规格密度
        float scale = getContext().getResources().getDisplayMetrics().density;
        int dp = (int) (px / scale + 0.5);
        return dp;
    }

    /**
     * 获取最大宽度
     * @return
     */
    public static int getMaxWidth() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    /**
     * 获取最大高度
     * @return
     */
    public static int getMaxHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * 通过id创建View
     *
     * @param resId
     * @return
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
//        System.out.println(".......haha" + getContext() == null ? true : false);
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 把毫秒值 转换成 分秒
     * @param value
     * @return
     */
    public static String formatDate(long value, String type){
        SimpleDateFormat formatter = null;
        String date = "";
        switch (type){
            case "ms":
                formatter = new SimpleDateFormat("mm:ss");
                try {
                    date = formatter.format(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return date;

    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 判断当前的线程是不是在主线程
     */
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 在主线程中执行
     *
     * @param runnable
     */
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * 开启Activity
     */
    public static void startActivity(Class c, Map map) {
        BaseActivity activity = (BaseActivity) getForegroundActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, c);

            if (map != null) {
                IntentExtraUtils.putIntentExtra(intent, map);
            }

            activity.startActivity(intent);
//			// 开启动画
//			activity.overridePendingTransition(R.anim.setg_next_in, R.anim.setg_next_out);
        } else {
            try {

                Intent intent = new Intent(getContext(), c);

                if (map != null) {
                    IntentExtraUtils.putIntentExtra(intent, map);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 开启Activity
     *
     * @param c
     * @param intent
     */
    public static void startActivity(Class c, Intent intent) {
        BaseActivity activity = (BaseActivity) getForegroundActivity();
        if (activity != null) {
            intent.setClass(activity, c);

            activity.startActivity(intent);
        }
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static Toast myToast;

    /**
     * 提示
     * @param str
     */
    private static void showToast(String str) {

        if (myToast == null || toastTextView == null) {
            myToast = new Toast(getContext());
            myToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, -1000);
            myToast.setDuration(Toast.LENGTH_SHORT);
            toastTextView = (TextView) inflate(R.layout.toastview);
            toastTextView.setText(str.trim());
            myToast.setView(toastTextView);
        } else {
            toastTextView.setText(str.trim());
            myToast.setView(toastTextView);
        }
        myToast.show();
    }

    /**
     * 隐藏Toast
     */
    public static void hintToast() {
        if (myToast != null) {
            myToast.cancel();
        }
    }

    /**
     * 返回应用中当前显示的Activity
     *
     * @return
     */
    public static Activity getForegroundActivity() {
        return BaseActivity.getForegroundActivity();
    }

    /**
     * 在当前Activity上弹出窗口，通过传入view 和 windowManager 弹出，在外界控制显示
     *
     * @param windowManager 当前Activity对应的 windowManager
     * @param contentView   当前窗口要显示的内容
     */
    public static void showWindow(WindowManager windowManager, View contentView) {

        // 设置布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //宽高填充
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //全屏 要有WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，好像因为这个才能相应返回键？
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //透明窗体
        params.format = PixelFormat.TRANSPARENT;
        //普通应用程序窗口
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        //添加
        windowManager.addView(contentView, params);
    }

    /**
     * 是否在显示虚拟导航键盘
     * @return
     */
    public static boolean ishowNavigationBar(){
        int uiOptions = getForegroundActivity().getWindow().getDecorView().getSystemUiVisibility();
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 隐藏虚拟键盘
     */
    public static void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getForegroundActivity().getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
//            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
//            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getForegroundActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    /**
     * 开启屏幕常亮
     */
    public static void openScreenON(Activity activity){
        try {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭屏幕常亮
     */
    public static void closeScreenON(Activity activity){
        try {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
