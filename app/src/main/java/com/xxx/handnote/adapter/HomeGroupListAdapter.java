package com.xxx.handnote.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.xxx.handnote.R;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.bean.HomeGroupListBean;
import com.xxx.handnote.utils.CodeUtils;
import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.utils.image.ImageLoadUtils;
import com.xxx.handnote.view.M_TextView;

import java.util.List;

/**
 * Created by niuapp on 2016/7/11 12:04.
 * Project : HandNote.
 * Email : *******
 * --> 分组
 */
public class HomeGroupListAdapter extends BaseRecyclerViewAdapter {

    //类型
    private final int TYPE_DATE = 1;
    private final int TYPE_TEXT = 2;
    private final int TYPE_IMAGE_1 = 3;
    private final int TYPE_IMAGE_2 = 4;
    private final int TYPE_IMAGE_LIST = 5;

    private String json = "{\n" +
            "    \"code\": \"1000\",\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"res\": [\n" +
            "        {\n" +
            "            \"id\": \"1\",\n" +
            "            \"catname\": \"人物\",\n" +
            "            \"pic\": \"http://pic14.nipic.com/20110615/1347158_233357498344_2.jpg\",\n" +
            "            \"addtime\": \"7443146686770\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"3\",\n" +
            "            \"catname\": \"动物\",\n" +
            "            \"pic\": \"http://pic4.nipic.com/20091204/661401_090833000978_2.jpg\",\n" +
            "            \"addtime\": \"8443146686770\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"2\",\n" +
            "            \"catname\": \"风景\",\n" +
            "            \"pic\": \"http://www.ylzxedu.com/uploads/9NbY1ocaxtGw3hfPpivZI472LoMRs-tQ7HZ1Wz4LEUVcSZ/zODspZ0uSL4fL6DnzK6KURgSVyMlgVg54z1U5W9934CjE5PtmSoAHTEhk-jT89to.jpg\",\n" +
            "            \"addtime\": \"8143146686770\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private final String requestUrl = Const.BASE_URL + "index/getCateList";
    private List<HomeGroupListBean.ResEntity> listData;

    /**
     * 初始化
     */
    public HomeGroupListAdapter() {
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
            listData = new Gson().fromJson(json, HomeGroupListBean.class).res;
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
        return new HomeGroupListViewHolder(itemView);
    }

    //对应布局
    @Override
    protected View adapterCreateView(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = UIUtils.inflate(R.layout.item_home_group);

        return itemView;
    }

    //类型
    @Override
    protected int getAdapterItemType(int position) {
        return 99;
    }

    //绑定数据
    @Override
    protected boolean onAdapterBindViewHolder(ViewHolder holder, final int position, int itemType) {
        HomeGroupListViewHolder groupListViewHolder = (HomeGroupListViewHolder) holder;

        ImageLoadUtils.loadImage(listData.get(position).pic, groupListViewHolder.mItemImage);
        groupListViewHolder.mItemCountText.setText("(" + listData.get(position).id + ")");
        groupListViewHolder.mItemName.setText(CodeUtils.decodeUrl(listData.get(position).catname));

        groupListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToastSafe(CodeUtils.decodeUrl(listData.get(position).catname));
            }
        });

        return false;
    }

    //条目数
    @Override
    public int getAdapterItemCount() {
        return listData == null ? 0 : listData.size();
    }


    public class HomeGroupListViewHolder extends ViewHolder {

        private SimpleDraweeView mItemImage;
        private M_TextView mItemName;
        private M_TextView mItemCountText;

        public HomeGroupListViewHolder(View itemView) {
            super(itemView);

            int type = (int) itemView.getTag();

            mItemImage = (SimpleDraweeView) itemView.findViewById(R.id.itemImage);
            mItemName = (M_TextView) itemView.findViewById(R.id.itemName);
            mItemCountText = (M_TextView) itemView.findViewById(R.id.itemCountText);

        }
    }
}
