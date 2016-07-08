package com.xxx.handnote.utils.net;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xxx.handnote.utils.UIUtils;

/**
 * Created by niuapp on 2016/7/8 16:33.
 * Project : HandNote.
 * Email : *******
 * -->  网络请求
 */
public class NetUtils {














    /**
     * 请求的回调接口
     */
    public interface SuccessListener {
        public abstract void onSuccess(Object data);
        public abstract void onError(String msg);
        public abstract void saveJson(String json);
    }

    /**
     * wifi是否连接
     * @return wifi是否连接
     */
    public static boolean isWifiConnected() {
        Context context = UIUtils.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否能上网
     * @return  是否能上网
     */
    public static boolean hasConnectedNetwork() {
        Context context = UIUtils.getContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 打开设置网络界面
     */
    public static void openNetworkSetting(final Context context){
        //提示对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO Auto-generated method stub
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                } else {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }
                context.startActivity(intent);
                System.exit(0);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                System.exit(0);
            }
        }).show();
    }
}
