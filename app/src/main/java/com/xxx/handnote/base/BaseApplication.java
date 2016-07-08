package com.xxx.handnote.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xxx.handnote.utils.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by niuapp on 2016/7/8 14:14.
 * Project : HandNote.
 * Email : *******
 * -->
 */
public class BaseApplication extends Application {

    //上下文
    private static BaseApplication mContext;
    //主线程的Handler
    private static Handler mMainThreadHandler;
    //主线程
    private static Thread mMainThread;
    //主线程ID
    private static int mMainThreadId;
    //Looper
    private static Looper mLooper;

    //
    private static boolean initFlag = false;
    private static int a = 1;

    //记录打开的Activity 的集合
    private static List<Activity> allActivity;



    //记录用户信息
    private static UserInfo.MyInfo userInfo;

    /**
     * 当前时间
     *
     * @return
     */
    public static String getStartDate() {
        if (startDate == null) return "";
        return startDate;
    }

    /**
     * 当前系统时间
     *
     * @param startDate
     */
    public static void setStartDate(String startDate) {
        BaseApplication.startDate = startDate;
    }

    private static String startDate;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!Const.deBugFlag) { //不是 deBug模式才开启
            //重写系统的异常处理器
            Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread thread, Throwable ex) {

                    //友盟统计
                    try {
//                        MobclickAgent.reportError(getApplication(), ex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //出现异常时调用
                    //把异常信息输出到sd卡对应的文件中
                    StringBuilder sb = new StringBuilder();
                    //时间
                    sb.append("time: " + Formatter.formatFileSize(BaseApplication.this, System.currentTimeMillis()));
                    //编译信息
                    Field[] fields = Build.class.getDeclaredFields();
                    for (Field field : fields) {
                        try {
                            sb.append(field.getName() + " = " + field.get(null) + "\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //异常信息
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    sb.append(sw.toString());

                    //友盟统计
//                    MobclickAgent.reportError(getApplication(), sb.toString());

                    //把sb中的数据输出到 文件中
                    try {
//                        LogUtils.d("路径--> " + FileUtils.getExternalStoragePath() + "exception.info");
                        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileUtils.getExternalStoragePath() + "exception.info")));
                        bw.write(sb.toString());
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //干掉自己的进程
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
        }else {
            FileUtils.writeFile("", FileUtils.getExternalStoragePath() + "log.html", false);//清空log
        }


        if (!initFlag) {
            init();
            initFlag = true;
        }
    }

    // 初始化
    private void init() {

        Fresco.initialize(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        //初始化分享
//        ShareSDK.initSDK(this);

        //给成员变量初始化
        mContext = this;
        mMainThreadHandler = new Handler();
        mMainThread = Thread.currentThread();
        //Returns the identifier of the calling thread, which be used with setThreadPriority(int, int).
        mMainThreadId = android.os.Process.myTid();
        mLooper = getMainLooper();

        //初始化记录Activity的集合
        allActivity = new ArrayList<>();

    }

    /**
     * 获取已经打开的Activity集合
     *
     * @return 已经打开的Activity集合
     */
    public static List<Activity> getActivityList() {
        return allActivity;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息对象
     */
    public static UserInfo.MyInfo getUserInfo() {

        if (BaseApplication.userInfo == null) {
            synchronized (BaseApplication.class) {
                if (BaseApplication.userInfo == null) {
                    BaseApplication.userInfo = UserInfo.getUserInfo();
                }
            }
        }
        return BaseApplication.userInfo;
    }

    /**
     * 用户退出
     */
    public static void logout() {
        synchronized (BaseApplication.class) {
            UserInfo.logout();
            BaseApplication.userInfo = null;
        }
    }

    public static BaseApplication getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getLooper() {
        return mLooper;
    }
}
