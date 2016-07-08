package com.xxx.handnote.utils.image;

/**
 * Created by niuapp on 2016/4/11 17:12.
 * Project : YuntooApk.
 * Email : *******
 * --> 打开图片窗口每个图片的信息
 */
public class ImagePopwInfoBean {

    public ImagePopwInfoBean(String imageUrl, String imageInfo1, String imageInfo2, int width, int height) {
        this.imageUrl = imageUrl;
        this.imageInfo1 = imageInfo1;
        this.imageInfo2 = imageInfo2;
        this.width = width;
        this.height = height;
    }

    public ImagePopwInfoBean(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl;
    public String imageInfo1;
    public String imageInfo2;


    public int width;
    public int height;
}
