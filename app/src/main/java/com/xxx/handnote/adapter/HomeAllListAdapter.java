package com.xxx.handnote.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.xxx.handnote.R;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.bean.HomeListBean;
import com.xxx.handnote.utils.HN_Utils;
import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.utils.image.ImageLoadUtils;
import com.xxx.handnote.utils.image.ImagePopwInfoBean;
import com.xxx.handnote.view.HomeItem_ImageList;
import com.xxx.handnote.view.ImagePopView;
import com.xxx.handnote.view.M_TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by niuapp on 2016/7/11 12:04.
 * Project : HandNote.
 * Email : *******
 * --> 全部
 */
public class HomeAllListAdapter extends BaseRecyclerViewAdapter {

    //类型
    private final int TYPE_DATE = 1;
    private final int TYPE_TEXT = 2;
    private final int TYPE_IMAGE_1 = 3;
    private final int TYPE_IMAGE_2 = 4;
    private final int TYPE_IMAGE_LIST = 5;

    private final String json = "{\n" +
            "    \"code\": \"1000\",\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"res\": [\n" +
            "        {\n" +
            "            \"addtime\": \"12343146686670\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"id\": \"2\",\n" +
            "            \"img\": [\n" +
            "                \n" +
            "            ],\n" +
            "            \"title\": \"aaassssbbb\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"addtime\": \"12343146696810\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"id\": \"2\",\n" +
            "            \"img\": [\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\"\n" +
            "            ],\n" +
            "            \"title\": \"aaassssbbb\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"addtime\": \"12343146676870\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"id\": \"2\",\n" +
            "            \"img\": [\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "                \"http://pic41.nipic.com/20140509/18696269_121755386187_2.png\",\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "                \"http://www.ylzxedu.com/uploads/9NbY1ocaxtGw3hfPpivZI472LoMRs-tQ7HZ1Wz4LEUVcSZ/zODspZ0uSL4fL6DnzK6KURgSVyMlgVg54z1U5W9934CjE5PtmSoAHTEhk-jT89to.jpg\",\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "                \"http://pic14.nipic.com/20110615/1347158_233357498344_2.jpg\"\n" +
            "            ],\n" +
            "            \"title\": \"aaassssbbb\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"addtime\": \"23443146686770\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"id\": \"2\",\n" +
            "            \"img\": [\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "                \"http://pic41.nipic.com/20140509/18696269_121755386187_2.png\"\n" +
            "            ],\n" +
            "            \"title\": \"aaassssbbb\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"addtime\": \"13443146686799\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"id\": \"2\",\n" +
            "            \"img\": [\n" +
            "                \"http://www.ylzxedu.com/uploads/9NbY1ocaxtGw3hfPpivZI472LoMRs-tQ7HZ1Wz4LEUVcSZ/zODspZ0uSL4fL6DnzK6KURgSVyMlgVg54z1U5W9934CjE5PtmSoAHTEhk-jT89to.jpg\",\n" +
            "                \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "                \"http://pic14.nipic.com/20110615/1347158_233357498344_2.jpg\"\n" +
            "            ],\n" +
            "            \"title\": \"aaassssbbb\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"addtime\": \"13443146686770\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasd\",\n" +
            "            \"id\": \"1\",\n" +
            "            \"img\": [\n" +
            "                \"http://pic14.nipic.com/20110615/1347158_233357498344_2.jpg\"\n" +
            "            ],\n" +
            "            \"title\": \"aaassss\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private final String requestUrl = Const.BASE_URL + "index/index";
    private List<HomeListBean.ResEntity> listData;

    /**
     * 初始化
     */
    public HomeAllListAdapter() {
        requestData();//请求数据

        //头脚
        setHeadView(UIUtils.inflate(R.layout.nullview));
        setFootView(UIUtils.inflate(R.layout.nullview));
    }

    /**
     * 请求数据
     */
    private void requestData() {

        try {
            listData = fixList(new Gson().fromJson(json, HomeListBean.class).res);
            notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }

//        //请求数据
//        OkHttpUtils.get().url(requestUrl).build()
//        .execute(new HomeListBeanCallBack() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//            @Override
//            public void onResponse(HomeListBean response, int id) {
//
//            }
//        });
    }

    //ViewHolder
    @Override
    protected ViewHolder getChildViewHolder(View itemView) {
        return new HomeAllListViewHolder(itemView);
    }

    //对应布局
    @Override
    protected View adapterCreateView(ViewGroup parent, int viewType) {
        View itemView = null;

        switch (viewType) {
            case TYPE_DATE:
                itemView = UIUtils.inflate(R.layout.item_home_all_date);
                break;
            case TYPE_TEXT:
                itemView = UIUtils.inflate(R.layout.item_home_all_text);
                break;
            case TYPE_IMAGE_1:
                itemView = UIUtils.inflate(R.layout.item_home_all_image1);
                break;
            case TYPE_IMAGE_2:
                itemView = UIUtils.inflate(R.layout.item_home_all_image2);
                break;
            case TYPE_IMAGE_LIST:
                itemView = UIUtils.inflate(R.layout.item_home_all_imagelist);
                break;
        }

        return itemView;
    }

    //类型
    @Override
    protected int getAdapterItemType(int position) {
        return listData.get(position).type;
    }

    //绑定数据
    @Override
    protected boolean onAdapterBindViewHolder(ViewHolder holder, int position, int itemType) {
        HomeAllListViewHolder homeAllListViewHolder = (HomeAllListViewHolder) holder;

        switch (itemType) {
            case TYPE_DATE:
                homeAllListViewHolder.mDate.setText(HN_Utils.formatDate(Long.parseLong(listData.get(position).addtime), HN_Utils.TIME_YMD));
                break;
            case TYPE_TEXT:
                homeAllListViewHolder.mItemTime.setText(HN_Utils.formatDate(Long.parseLong(listData.get(position).addtime), HN_Utils.TIME_HM));
                homeAllListViewHolder.mItemText.setText(listData.get(position).content);
                break;
            case TYPE_IMAGE_1:
                homeAllListViewHolder.mItemTime.setText(HN_Utils.formatDate(Long.parseLong(listData.get(position).addtime), HN_Utils.TIME_HM));
                homeAllListViewHolder.mItemText.setText(listData.get(position).content);

                ImageLoadUtils.loadImage(listData.get(position).img.get(0), homeAllListViewHolder.mImage1);
                break;
            case TYPE_IMAGE_2:
                homeAllListViewHolder.mItemTime.setText(HN_Utils.formatDate(Long.parseLong(listData.get(position).addtime), HN_Utils.TIME_HM));
                homeAllListViewHolder.mItemText.setText(listData.get(position).content);

                ImageLoadUtils.loadImage(listData.get(position).img.get(0), homeAllListViewHolder.mImage1);
                ImageLoadUtils.loadImage(listData.get(position).img.get(1), homeAllListViewHolder.mImage2);
                break;
            case TYPE_IMAGE_LIST:
                homeAllListViewHolder.mItemTime.setText(HN_Utils.formatDate(Long.parseLong(listData.get(position).addtime), HN_Utils.TIME_HM));
                homeAllListViewHolder.mItemText.setText(listData.get(position).content);

                homeAllListViewHolder.mImageList.setData(listData.get(position).img);
                break;
        }

        try { // 点击预览图片
            if (listData.get(position).img != null) {
                final List<ImagePopwInfoBean> urlList = new ArrayList<>();
                for (int i = 0; i < listData.get(position).img.size(); i++) {
                    urlList.add(new ImagePopwInfoBean(listData.get(position).img.get(i)));
                }
                if (homeAllListViewHolder.mImage1 != null) {
                    homeAllListViewHolder.mImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImagePopView.showImagePopw(urlList, 0);
                        }
                    });
                }
                if (homeAllListViewHolder.mImage2 != null) {

                    homeAllListViewHolder.mImage2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImagePopView.showImagePopw(urlList, 1);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //条目数
    @Override
    public int getAdapterItemCount() {
        return listData == null ? 0 : listData.size();
    }


    public class HomeAllListViewHolder extends ViewHolder {

        public M_TextView mItemTime;
        public M_TextView mItemText;

        public M_TextView mDate;

        public SimpleDraweeView mImage1;
        public SimpleDraweeView mImage2;

        public HomeItem_ImageList mImageList;

        public HomeAllListViewHolder(View itemView) {
            super(itemView);

            int type = (int) itemView.getTag();

            switch (type) {
                case TYPE_DATE:
                    mDate = (M_TextView) itemView.findViewById(R.id.date);
                    break;
                case TYPE_TEXT:
                    mItemTime = (M_TextView) itemView.findViewById(R.id.itemTime);
                    mItemText = (M_TextView) itemView.findViewById(R.id.itemText);
                    break;
                case TYPE_IMAGE_1:
                    mItemTime = (M_TextView) itemView.findViewById(R.id.itemTime);
                    mItemText = (M_TextView) itemView.findViewById(R.id.itemText);

                    mImage1 = (SimpleDraweeView) itemView.findViewById(R.id.image_1);
                    break;
                case TYPE_IMAGE_2:
                    mItemTime = (M_TextView) itemView.findViewById(R.id.itemTime);
                    mItemText = (M_TextView) itemView.findViewById(R.id.itemText);

                    mImage1 = (SimpleDraweeView) itemView.findViewById(R.id.image_1);
                    mImage2 = (SimpleDraweeView) itemView.findViewById(R.id.image_2);
                    break;
                case TYPE_IMAGE_LIST:
                    mItemTime = (M_TextView) itemView.findViewById(R.id.itemTime);
                    mItemText = (M_TextView) itemView.findViewById(R.id.itemText);

                    mImageList = (HomeItem_ImageList) itemView.findViewById(R.id.imageList);
                    break;
            }
        }
    }

    /**
     * 修改集合
     *
     * @param resEntities
     * @return
     */
    private List<HomeListBean.ResEntity> fixList(List<HomeListBean.ResEntity> resEntities) {
        if (resEntities == null) {
            return null;
        }

        //首先按时间排序 最近 --> 最远
        Collections.sort(resEntities, new Comparator<HomeListBean.ResEntity>() {
            @Override
            public int compare(HomeListBean.ResEntity lhs, HomeListBean.ResEntity rhs) {
                return HN_Utils.comperTime(Long.parseLong(rhs.addtime), Long.parseLong(lhs.addtime));
            }
        });

        String preTime = "";//上一条时间类型 时间

        //遍历插入
        for (int i = 0; i < resEntities.size(); i++) {
            //确定类型
            HomeListBean.ResEntity resEntity = resEntities.get(i);
            if (resEntity.img == null || resEntity.img.size() == 0) {
                resEntity.type = TYPE_TEXT;
            } else if (resEntity.img.size() == 1) {
                resEntity.type = TYPE_IMAGE_1;
            } else if (resEntity.img.size() == 2) {
                resEntity.type = TYPE_IMAGE_2;
            } else if (resEntity.img.size() > 2) {
                resEntity.type = TYPE_IMAGE_LIST;
            }

            if (i == 0) { //首先拿到第一条时间添加
                preTime = resEntity.addtime;
                resEntities.add(i, new HomeListBean.ResEntity(TYPE_DATE, preTime));
                i++;
            } else {
                //判断当前时间和上一次添加的时间 如果相差一天以上 就再 添加时间类型条目，否则就跳过
                if (HN_Utils.comperTime(Long.parseLong(preTime), Long.parseLong(resEntity.addtime)) > 0) {
                    //相差1天以上，添加
                    preTime = resEntities.get(i).addtime;
                    resEntities.add(i, new HomeListBean.ResEntity(TYPE_DATE, preTime));
                    i++;//在当前位置加入，继续判断除时间 和本条 之后一条
                }
            }


        }

        return resEntities;
    }
}
