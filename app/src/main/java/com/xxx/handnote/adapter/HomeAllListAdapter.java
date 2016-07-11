package com.xxx.handnote.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xxx.handnote.R;
import com.xxx.handnote.base.Const;
import com.xxx.handnote.bean.HomeListBean;
import com.xxx.handnote.utils.UIUtils;

import java.util.List;

/**
 * Created by niuapp on 2016/7/11 12:04.
 * Project : HandNote.
 * Email : *******
 * -->
 */
public class HomeAllListAdapter extends BaseRecyclerViewAdapter {

    private final String json = "{\n" +
            "    \"code\": \"1000\",\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"res\": [\n" +
            "        {\n" +
            "            \"id\": \"2\",\n" +
            "            \"title\": \"aaassssbbb\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasdaas213\",\n" +
            "            \"img\": [\n" +
            "                \"m.shouji.cc/uploads/20160625/576e9ff8a78e4.jpg\"\n" +
            "            ],\n" +
            "            \"addtime\": \"1466867704\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"1\",\n" +
            "            \"title\": \"aaassss\",\n" +
            "            \"cid\": \"1\",\n" +
            "            \"content\": \"asdasdqweqasdasd\",\n" +
            "            \"img\": [\n" +
            "                \"m.shouji.cc/uploads/20160625/576e9fd935974.png\"\n" +
            "            ],\n" +
            "            \"addtime\": \"1466840175\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private final String requestUrl = Const.BASE_URL + "index/index";
    private List<HomeListBean.ResEntity> resEntities;

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
            resEntities = new Gson().fromJson(json, HomeListBean.class).res;
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

    @Override
    protected ViewHolder getChildViewHolder(View itemView) {
        return new HomeAllListViewHolder(itemView);
    }

    @Override
    protected View adapterCreateView(ViewGroup parent, int viewType) {
        View itemView = null;

        if (viewType == 1){
            itemView = UIUtils.inflate(R.layout.item_home_all_text);
        }

        return itemView;
    }

    @Override
    protected int getAdapterItemType(int position) {
        return 0;
    }

    @Override
    protected boolean onAdapterBindViewHolder(ViewHolder holder, int position, int itemType) {
        return false;
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }


    public class HomeAllListViewHolder extends ViewHolder{

        public HomeAllListViewHolder(View itemView) {
            super(itemView);

            int type = (int) itemView.getTag();
        }
    }
}
