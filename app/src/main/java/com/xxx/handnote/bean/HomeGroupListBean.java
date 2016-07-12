package com.xxx.handnote.bean;

import java.util.List;

/**
 * Created by niuapp on 2016/7/12 17:52.
 * Project : HandNote.
 * Email : *******
 * --> 首页分组列表
 */
public class HomeGroupListBean {

    public String code;
    public String msg;

    public List<ResEntity> res;

    public static class ResEntity {
        public String id;
        public String catname;
        public String pic;
        public String addtime;
    }
}
