package com.xxx.handnote.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;

import com.xxx.handnote.activity.MainActivity;
import com.xxx.handnote.R;
import com.xxx.handnote.base.swipebacklayout.SwipeBackLayout;
import com.xxx.handnote.base.swipebacklayout.app.SwipeBackActivity;
import com.xxx.handnote.utils.FileUtils;
import com.xxx.handnote.utils.UIUtils;


/**
 * Activity 基类
 */
public abstract class BaseActivity extends SwipeBackActivity {

    protected SharedPreferences sp;

    protected boolean isShowEnterAnim = true;
    protected boolean isShowExitAnim = true;


    // 静态的 可以得到前台Activity
    private static Activity mForegroundActivity;
    private View contentView;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        isShowEnterAnim = true;
        isShowExitAnim = true;

        super.onCreate(savedInstanceState);

        //如果是首页，禁止边界右滑关闭
        if (this instanceof MainActivity) {
            setSwipeBackEnable(false);
        } else {
            setSwipeBackEnable(true);
            SwipeBackLayout swipeBackLayout = getSwipeBackLayout();

//            swipeBackLayout.setEdgeSize(500);//边缘范围
//            swipeBackLayout.setScrollThresHold(0.9f);//滑动到这个页面的什么位置才关闭
//            swipeBackLayout.setSensitivity(UIUtils.getContext(), 0.1f);//灵敏度
        }

        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //注册广播接受者
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myNetReceiver, mFilter);

        //初始化sp
        sp = FileUtils.getSharedPreferences();

        //初始化数据
        init();

        //进入动画
        if (isShowEnterAnim) {
            setEnterSwichLayout();
        }


        //添加到已经打开的Activity集合
        UIUtils.getActivityList().add(this);


        //设置内容页布局
        contentView = getContentView();
        if (contentView != null) {
            setContentView(contentView);
        }


        //初始化View
        initView();
    }

    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * @return 返回Activity的内容View
     */
    protected abstract View getContentView();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        this.mForegroundActivity = this;

//        LoginPopupWindow.changeWindow_resume();//调整顺序
//
//        if (TextUtils.isEmpty(className)) {
//            className = this.getClass().getSimpleName();
//        }
//        if (!(this instanceof MainActivity)) {
//            MobclickAgent.onPageStart(className);//不包含Fragment的Activity页面要统计
//        }
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mForegroundActivity = null;
//        LoginPopupWindow.changeWindow_pause();//调整顺序
//
//        if (TextUtils.isEmpty(className)) {
//            className = this.getClass().getSimpleName();
//        }
//        if (!(this instanceof MainActivity)) {
//            MobclickAgent.onPageEnd(className);//不包含Fragment的Activity页面要统计
//        }
//        MobclickAgent.onPause(this);
//
//        LogUtils.d("onPause");

    }


    /**
     * @return 获取前台Activity
     */
    public static Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        if (myNetReceiver != null) {
            unregisterReceiver(myNetReceiver);
        }

        //从Activity集合中移除
        UIUtils.getActivityList().remove(this);

//        //关闭可能打开的窗口
//        try {
//
//            if (!(this instanceof TermActivity)){//是服务条款页面就不关闭登陆注册窗口
//                LoginPopupWindow.destroyWindow();
//            }
//
//            SharePopView.closeShareWindow();
//            SearchPopView.closeWindow();
//            ImagePopView.closeImageWindow();
//
//        } catch (Exception e) {
//
//        }
    }

    // 初始化双击时间
    long currentTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (onBackRunnable != null && keyCode == KeyEvent.KEYCODE_BACK) {
            onBackRunnable.continueRunnable(null);
            onBackRunnable = null; //TODO
            return true;
        }

        //如果当前Activity是 MainActivity双击返回键退出
        if (keyCode == KeyEvent.KEYCODE_BACK && this instanceof MainActivity) {
            //双击
            if (currentTime < (SystemClock.currentThreadTimeMillis() - 250L)) {
                //两次点击间隔小于500毫秒，Toast
                UIUtils.showToastSafe("再次点击返回键退出");

                currentTime = SystemClock.currentThreadTimeMillis();
            } else {
                //在500毫秒？内再次点击了返回键，退出
                currentTime = 0;
                //TODO 退出
//                setExitSwichLayout();

//                //停止分享，释放资源
//                ShareSDK.stopSDK(this);

                try {
                    UIUtils.hintToast();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //让应用退出虚拟机
                System.exit(0);

//                finish();
//                Intent home = new Intent(Intent.ACTION_MAIN);
//                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                home.addCategory(Intent.CATEGORY_HOME);
//                startActivity(home);//返回到桌面
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setExitSwichLayout();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //进入动画
    public void setEnterSwichLayout() {
        overridePendingTransition(R.anim.setg_next_in, R.anim.setg_next_out);
    }

    //退出动画
    public void setExitSwichLayout() {
        finish();
        if (isShowExitAnim) {
            overridePendingTransition(R.anim.setg_pre_in, R.anim.setg_pre_out);
        } else {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * popupwindow有关
     *
     * @return
     */
    public View getCurrentActivityView() {

        if (contentView == null){
            View cView = null;
            try {
                cView = getWindow().getDecorView().findViewById(android.R.id.content);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return cView;
        }

        return contentView;


    }


    //网络监听的广播接收者
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    private int inCount = 0;
    protected boolean isRefush = false;
    private BroadcastReceiver myNetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                ++inCount;

                if (inCount > 1) {
                    mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    netInfo = mConnectivityManager.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isAvailable()) {

                        /////////////网络连接
                        String name = netInfo.getTypeName();

//                        UIUtils.showToastSafe("网络已经连接");
//                        if (isRefush){
//                            isRefush = false;
//                            //关闭当前页面再次打开
//                            setExitSwichLayout();
//                            startActivity(new Intent(mForegroundActivity, MainActivity.class));
//                        }
                        //重新加载

                        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            /////WiFi网络
//                            UIUtils.showToastSafe("WiFi网络");
                        } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                            /////有线网络
//                            UIUtils.showToastSafe("有线网络");
                        } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            /////////3g网络
//                            UIUtils.showToastSafe("3g网络");
                        }
                    } else {
                        ////////网络断开
//                        UIUtils.showToastSafe("网络已经断开");
                    }
                }


            }

        }
    };



//    protected PullToRefreshWebView currentPullToRefreshWebView;
//
//    /**
//     * 给外界使用 用来完成刷新
//     *
//     * @return
//     */
//    public PullToRefreshWebView getCurrentPullToRefreshWebView() {
//        return currentPullToRefreshWebView;
//    }
//
//    public void setCurrentPullToRefreshWebView(PullToRefreshWebView currentPullToRefreshWebView) {
//        this.currentPullToRefreshWebView = currentPullToRefreshWebView;
//    }


    public OnContinueRunnable onBackRunnable;//在点击返回键时先执行这个

    public void setOnBackRunnable(OnContinueRunnable onBackRunnable) {
        this.onBackRunnable = onBackRunnable;
    }
}
