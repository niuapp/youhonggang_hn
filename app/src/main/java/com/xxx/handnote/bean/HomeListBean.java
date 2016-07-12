package com.xxx.handnote.bean;

import java.util.List;

/**
 * Created by niuapp on 2016/7/11 11:55.
 * Project : HandNote.
 * Email : *******
 * --> 首页列表
 */
public class HomeListBean {

    public String code;
    public String msg;

    public List<ResEntity> res;

    public static class ResEntity {
        public String id;
        public String title;
        public String cid;
        public String content;
        public String addtime;
        public List<String> img;

        /**
         * type 表示当前条目是什么类型
         *      1、时间
         *      2、文本
         *      3、单图
         *      4、2图
         *      5、多图
         */
        public int type;

        public ResEntity(int type, String addtime) {
            this.type = type;
            this.addtime = addtime;
        }
    }
}
