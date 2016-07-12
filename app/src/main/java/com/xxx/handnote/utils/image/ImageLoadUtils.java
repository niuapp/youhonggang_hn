package com.xxx.handnote.utils.image;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.xxx.handnote.R;
import com.xxx.handnote.base.OnContinueRunnable;

import java.util.HashMap;

/**
 * Created by niuapp on 2016/2/19 16:01.
 * Project : YuntooApk.
 * Email : *******
 * --> 加载图片和图片缓存  Netroid
 */
public class ImageLoadUtils {

//    /**
//     * Netroid 加载图片， NetworkImageView 支持批量使用的图片对象
//     *
//     * @param url       图片url Netroid支持本地图片 格式-> 路径前缀 "http://"  "sdcard://"  "assets://"  "drawable://"
//     * @param imageView
//     */
//    public static void loadImageBatch(String url, NetworkImageView imageView) {
//        imageView.setBackgroundColor(0xffeeeeee);
//        imageView.setErrorImageResId(R.drawable.load_failed);//加载失败图片
//        Netroid.displayImage(url, imageView);
//    }

    /**
     * 加载带第一帧url的 gif 当gif加载完毕时 把第一帧隐藏
     *
     * @param url
     * @param imageView
     * @param url_gif1
     * @param imageView_gif1
     */
    public static void loadImage_Gif1(String url, SimpleDraweeView imageView, String url_gif1, final SimpleDraweeView imageView_gif1) {

//加载gif
        if (TextUtils.isEmpty(url) || imageView == null) {
            loadImage_Gif1("res://"+ "" +"/" + R.drawable.placehold, imageView, url_gif1, imageView_gif1);
            return;
        }

        //加载第一帧View 有这个图片url才加载
        if (!TextUtils.isEmpty(url_gif1) && imageView_gif1 != null) {
            imageView_gif1.setVisibility(View.VISIBLE);
            Uri uri_gif1 = Uri.parse(url_gif1);
            imageView_gif1.setImageURI(uri_gif1);
        }

        Uri uri = Uri.parse(url);

        //下载完成监听
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (anim != null) {
                    anim.start();
                    //隐藏第一帧
                    imageView_gif1.setVisibility(View.GONE);
                }
            }


        };

        //加载gif图
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .build();
        imageView.setController(controller);
    }

    /**
     * 加载单张图片
     */
    public static void loadImage(String url, SimpleDraweeView imageView){
        loadImage(url, imageView, null);
    }

    /**
     * 加载单张图片，在图片加载完成时会响应回调
     *
     * @param url
     * @param imageView
     * @param runnable  会在图片下载成功后回调
     */
    public static void loadImage(String url, SimpleDraweeView imageView, final Runnable runnable) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            loadImage("res://"+ "com.xxx.handnote" +"/" + R.drawable.placehold, imageView, runnable);
            return;
        }

        Uri uri = Uri.parse(url);

        //判断url后缀(包含)，是否是gif图，是gif图就更改加载方式
        if (url.contains(".gif")) {

            //加载gif图
            //下载完成监听
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable ImageInfo imageInfo,
                        @Nullable Animatable anim) {
                    if (runnable != null){
                        runnable.run();
                    }
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(imageView.getController())
                    .setUri(uri)
                    .setControllerListener(controllerListener)
                    .setAutoPlayAnimations(true)
                    .build();
            imageView.setController(controller);
        } else {
            //加载图片

            //下载完成监听
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable ImageInfo imageInfo,
                        @Nullable Animatable anim) {
                    if (runnable != null){
                        runnable.run();
                    }
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(imageView.getController())
                    .setUri(uri)
                    .setControllerListener(controllerListener)
                    .build();
            imageView.setController(controller);
        }
    }


    /**
     * 加载单张图片，在图片加载完成时会响应回调
     *
     * @param url
     * @param imageView
     * @param runnable  会在图片下载成功后回调 更加完善，包含失败回调
     */
    public static void loadImage_cb(String url, SimpleDraweeView imageView, final OnContinueRunnable runnable) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            loadImage_cb("res://"+ "" +"/" + R.drawable.placehold, imageView, runnable);
            return;
        }

        Uri uri = Uri.parse(url);

        //判断url后缀(包含)，是否是gif图，是gif图就更改加载方式
        if (url.contains(".gif")) {

            //加载gif图
            //下载完成监听
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable ImageInfo imageInfo,
                        @Nullable Animatable anim) {
                    if (runnable != null){
                        HashMap<String, String> continueInfo = new HashMap<>();
                        continueInfo.put("isFailure", "false");
                        continueInfo.put("isGif", "true");
                        runnable.continueRunnable(continueInfo);
                    }
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    if (runnable != null){
                        HashMap<String, String> continueInfo = new HashMap<>();
                        continueInfo.put("isFailure", "true");
                        runnable.continueRunnable(continueInfo);
                    }
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(imageView.getController())
                    .setUri(uri)
                    .setControllerListener(controllerListener)
                    .setAutoPlayAnimations(true)
                    .build();
            imageView.setController(controller);
        } else {
            //加载图片

            //下载完成监听
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable ImageInfo imageInfo,
                        @Nullable Animatable anim) {
                    if (runnable != null){
                        if (runnable != null){
                            HashMap<String, String> continueInfo = new HashMap<>();
                            continueInfo.put("isFailure", "false");
                            runnable.continueRunnable(continueInfo);
                        }
                    }
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    if (runnable != null){
                        HashMap<String, String> continueInfo = new HashMap<>();
                        continueInfo.put("isFailure", "true");
                        runnable.continueRunnable(continueInfo);
                    }
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(imageView.getController())
                    .setUri(uri)
                    .setControllerListener(controllerListener)
                    .build();
            imageView.setController(controller);
        }
    }
}
