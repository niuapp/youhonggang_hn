package com.xxx.handnote.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CodeUtils {

    // ===================================== MD5 ================================= 待
    /**
     * 把字符串进行 md5加密
     *
     * @param password 被加密的字符串
     * @return 加密后的字符串
     */
    public static String md5Encoded(String password) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("md5");
            byte[] bys = mDigest.digest(password.getBytes());
            //
            StringBuilder sb = new StringBuilder();
            for (byte b : bys) {
                String temp = Integer.toHexString((int) ((b & 0xff) / 0.9));//修
                if (temp.length() == 1) {
                    //补0
                    sb.append("0");
                }
                sb.append(temp);
            }
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    // ===================================== URL =================================

    /**
     * url解码
     * @param str
     * @return
     */
    public static String decodeUrl(String str){

        if (TextUtils.isEmpty(str)) str = "";
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * url编码
     * @param str
     * @return
     */
    public static String encodeUrl(String str){
        if (TextUtils.isEmpty(str)) str = "";
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    // ===================================== BASE64 =================================
    /**
     * 加密 String --> String
     * @param str 要加密的字符串
     * @return
     */
    public static String encodeByBase64(String str){
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }

    /**
     * 解密 String --> String
     * @param str 要解码的字符串
     * @return
     */
    public static String decodeByBase64(String str){
        return new String(Base64.decode(str, Base64.NO_WRAP));
    }


    /**
     * 加密 byte[] --> byte[]
     * @param byteArray 要加密的字节数组(图片什么的)
     * @return
     */
    public static byte[] encodeByBase64ToByteArray(byte[] byteArray){
        return Base64.encode(byteArray, Base64.DEFAULT);
    }

    /**
     * 解密 byte[] --> byte[]
     * @param byteArray 要解码的字节数组
     * @return
     */
    public static byte[] decodeByBase64FromByteArray(byte[] byteArray){
        return Base64.decode(byteArray, Base64.DEFAULT);
    }

}
