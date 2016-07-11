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


    }
}
