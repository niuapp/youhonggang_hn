package com.xxx.handnote.base;

/**
 * Created by Administrator on 2015/12/29.
 * 用户信息类，静态成员
 */
public class UserInfo {

    public static MyInfo getUserInfo() {
        if (userInfo == null) {
            synchronized (UserInfo.class) {
                if (userInfo == null) {
                    userInfo = new MyInfo();
                }
            }
        }
        return userInfo;
    }


    /**
     * 注销用户
     */
    public static void logout() {
        userInfo = null;
    }

    private static MyInfo userInfo;

    private UserInfo() {
    }

    public static class MyInfo {

        /**
         * 昵称
         */
        public String NICKNAME;
        /**
         * 用户登录后的session_key·
         */
        public String SESSION_KEY;
    }
}
