//package com.xxx.handnote.adapter;
//
//import android.content.Intent;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.google.gson.Gson;
//import com.yuntoo.yuntooapk.R;
//import com.yuntoo.yuntooapk.activity.test_.DetailActivity_test;
//import com.yuntoo.yuntooapk.base.Const;
//import com.yuntoo.yuntooapk.base.OnClickStartActivityListener;
//import com.yuntoo.yuntooapk.base.OnRefreshCompleteListener;
//import com.yuntoo.yuntooapk.bean.HomeListBeanV2;
//import com.yuntoo.yuntooapk.bean.parser.HomeListBeanV2Parser;
//import com.yuntoo.yuntooapk.utils.CodeUtils;
//import com.yuntoo.yuntooapk.utils.LogUtils;
//import com.yuntoo.yuntooapk.utils.UIUtils;
//import com.yuntoo.yuntooapk.utils.YuntooArtUtils;
//import com.yuntoo.yuntooapk.utils.image.ImageLoadUtils;
//import com.yuntoo.yuntooapk.utils.net.NetUtils;
//import com.yuntoo.yuntooapk.utils.net.net_volley_netroid.Const_Net;
//import com.yuntoo.yuntooapk.utils.net.net_volley_netroid.net_request.RequestMapData;
//import com.yuntoo.yuntooapk.view.HomeItem_Arts_ImageList;
//import com.yuntoo.yuntooapk.view.ItemGallery;
//import com.yuntoo.yuntooapk.view.ItemVideoView;
//import com.yuntoo.yuntooapk.view.LoopViewPager;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by niuapp on 2016/2/23 18:29.
// * Project : YuntooApk.
// * Email : *******
// * -->
// */
//public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
//    private static final String TAG = "HomeListAdapter";
//
//    private String requestUrl = Const_Net.BASE_URL + "v2/story/refresh/"; //刷新
//    private String requestGalleryUrl = Const_Net.BASE_URL + "api/gallery/recommend/"; //推荐艺术馆
//
//    private String requestDeleteGalleryUrlStart = Const_Net.BASE_URL + "api/gallery/"; //删除推荐艺术馆 前
//    private String requestDeleteGalleryUrlEnd = "/not_recommend/";//删除推荐艺术馆 后
//
//    private String requestMoreUrl = Const_Net.BASE_URL + "v2/story/"; //加载更多
//
//    private int offset = 0;
//    private int limit = 5;
//    private int startOffset = 10;
//
//    //条目类型 头脚
//    private final int HEAD_TYPE = -2;
//    private final int NULL_TYPE = -1;
//    private final int FOOT_TYPE = -3;
//
//    //中间内容条目的类型
//    /*展示类型：
//            1为GIF， 2为故事，3为图集，
//            4为视频，5艺术馆*/
//    private final int ITEM_GIF_TYPE = 1;
//    private final int ITEM_STORY_TYPE = 2;
//    private final int ITEM_LIST_TYPE = 3;
//    private final int ITEM_VIDEO_TYPE = 4;
//    private final int ITEM_ARTS_TYPE = 5;
//
//    public List<HomeListBeanV2.DataEntity> listData = new ArrayList<>();
//    private RecyclerView currentRecyclerView; //当前RecyclerView
//
//    /**
//     * 请求网络得到数据
//     */
//    public HomeListAdapter() {
//
//        //试加载缓存
//        //查看是否有缓存Json 试解析
//        String cacheJson = YuntooArtUtils.getSharedPreferences().getString(Const.CACHE_HOME_LIST, "");
//        if (!TextUtils.isEmpty(cacheJson)) {
//            try {
//
//                HomeListBeanV2 homeListBean = new Gson().fromJson(cacheJson, HomeListBeanV2.class);
//
//                if (1 == homeListBean.success) {
//                    listData = homeListBean.data;
//                    if (listData != null) {
////                        notifyDataSetChanged();
//                        offset = listData.size();
//                    }
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "  缓存 Json 异常");
//                e.printStackTrace();
//            }
//        }
//
//        isLoadMore = false;
//        //初始化数据
//        refreshData();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HEAD_TYPE;
//        } else if (position == getItemCount() - 1) {
//            return FOOT_TYPE;
//        } else {
//            //内容条目，不同的类型
//            position = fixPosition(position);
//            if (listData != null) {
//                int itemType = listData.get(position).index_type;
//                switch (itemType) {
//                    case ITEM_GIF_TYPE:
//                        return ITEM_GIF_TYPE;
//                    case ITEM_STORY_TYPE:
//                        return ITEM_STORY_TYPE;
//                    case ITEM_LIST_TYPE:
//                        return ITEM_LIST_TYPE;
//                    case ITEM_VIDEO_TYPE:
//                        return ITEM_VIDEO_TYPE;
//                    case ITEM_ARTS_TYPE:
//                        return ITEM_ARTS_TYPE;
//                    default:
//                        return ITEM_ARTS_TYPE;
//                }
//            }
//            return NULL_TYPE;//没有这个条目
//
//        }
//    }
//
//    private View mHeadView;//头布局
//    private View mFootView;//脚布局
//
//    /**
//     * 设置头布局
//     *
//     * @param headView
//     */
//    public void setHeadView(View headView) {
//        this.mHeadView = headView;
//        notifyItemInserted(0);
//    }
//
//    /**
//     * 设置脚布局
//     *
//     * @param footView
//     */
//    public void setFootView(View footView) {
//        this.mFootView = footView;
//        notifyItemInserted(getItemCount() - 1);
//    }
//
//    public static ItemVideoView currentVideoView;//当前视频对象
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        //不同类型的Item
//        View itemView = null;
//
//
//        if (viewType == HEAD_TYPE) { //头
//            if (mHeadView != null) {
//                itemView = mHeadView;
//            } else {
//                itemView = new LoopViewPager(UIUtils.getContext());
//                mHeadView = itemView;
//            }
//        } else if (viewType == FOOT_TYPE) { //脚
//            if (mFootView != null) {
//                itemView = mFootView;
//            } else {
//                itemView = UIUtils.inflate(R.layout.item_foot_loadingview);
//                mFootView = itemView;
//            }
//        } else {
//            switch (viewType) {
//                case ITEM_GIF_TYPE: //gif
//                    itemView = UIUtils.inflate(R.layout.item_home_image_card_gif);
//                    break;
//                case ITEM_STORY_TYPE: //故事
//                    itemView = UIUtils.inflate(R.layout.item_home_image_card_story);
//                    break;
//                case ITEM_LIST_TYPE: //横向图集
//                    itemView = UIUtils.inflate(R.layout.item_home_image_card_gallery);
//                    break;
//                case ITEM_VIDEO_TYPE: //视频
//                    itemView = UIUtils.inflate(R.layout.item_home_image_card_video);
//                    break;
//                case ITEM_ARTS_TYPE: //艺术馆
//                    itemView = UIUtils.inflate(R.layout.item_home_image_card_arts);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//
//        //不同类型的Item
//        if (itemView == null) {
//            itemView = new View(UIUtils.getContext());
//            itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//        }
//
//        itemView.setTag(viewType);//记录当前条目类型
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(ViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//
//        int tag = (int) holder.itemView.getTag();
//
//        if (tag == ITEM_VIDEO_TYPE && holder.mHomeItemVideoView != null) { //如果视频被移出屏幕，就重置该播放
//            if (holder.mHomeItemVideoView.isPlay()) {
//                holder.mHomeItemVideoView.resetView(); //如果被移除的条目正在播放，就停止
//            }
//
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        int itemType = getItemViewType(position);
//        final HomeListBeanV2.DataEntity dataEntity;
//
//        if (itemType == HEAD_TYPE) {
////            if (mHeadView != null && mHeadView instanceof LoopViewPager){
////                ((LoopViewPager)mHeadView).setCurrentItem(((LoopViewPager)mHeadView).getCurrentItem());
////            }
//            //头设置
//            return;
//        } else if (itemType == FOOT_TYPE) {
//            //脚设置
//            return;
//        } else {
//            //修正position
//            position = fixPosition(position);
//            dataEntity = listData.get(position);
//
//            YuntooArtUtils.setImageColor(dataEntity.gallery_cover_ave, holder.mHomeRlItemIv_Gif1);
//            YuntooArtUtils.setImageColor(dataEntity.gallery_cover_ave, holder.mHomeRlItemIv);
//
////            try {
////                if (TextUtils.isEmpty(dataEntity.gallery_cover_ave) || "null".equals(dataEntity.gallery_cover_ave)){
////                    holder.mHomeRlItemIv_Gif1.getHierarchy().setPlaceholderImage(R.drawable.placeholder);
////                    holder.mHomeRlItemIv.getHierarchy().setPlaceholderImage(R.drawable.placeholder);
////                }else {
////                    int color = UIUtils.getColor(R.color.colorWhiteS);
////                    if (dataEntity.gallery_cover_ave.startsWith("0x") || dataEntity.gallery_cover_ave.startsWith("0X")){
////                        try {
////                            color = Integer.parseInt(dataEntity.gallery_cover_ave.substring(2), 16);
////                        } catch (NumberFormatException e) {
////                            e.printStackTrace();
////                        }
////                    }
////
////
////                    holder.mHomeRlItemIv_Gif1.getHierarchy().setPlaceholderImage(new ColorDrawable(0xff000000 + color));
////                    holder.mHomeRlItemIv.getHierarchy().setPlaceholderImage(new ColorDrawable(0xff000000 + color));
////                }
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
//            switch (itemType) {//内容
//                case ITEM_GIF_TYPE: //gif
//
//                    try {
//                        if (holder.mHomeRlItemIv != null) {
//                            holder.mHomeRlItemIv_Gif1.setVisibility(View.VISIBLE);
//
//                            ImageLoadUtils.loadImage_Gif1(dataEntity.story_cover_url, holder.mHomeRlItemIv, dataEntity.gif_image, holder.mHomeRlItemIv_Gif1);
//                        }
//
//                        //标题
//                        if (TextUtils.isEmpty(dataEntity.story_title)) {
//                            holder.mHomeRlItemTitle.setVisibility(View.GONE);
//                            holder.mHomeRlItemTitle.setText("");
//                        } else {
//                            holder.mHomeRlItemTitle.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTitle.setText(CodeUtils.decodeUrl(dataEntity.story_title));
//                        }
//
//                        //描述
//                        if (TextUtils.isEmpty(dataEntity.description)) {
//                            holder.mHomeRlItemTv.setVisibility(View.GONE);
//                            holder.mHomeRlItemTv.setText("");
//                        } else {
//                            holder.mHomeRlItemTv.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTv.setText(CodeUtils.decodeUrl(dataEntity.description));
//                        }
//
//                        //艺术馆名
//                        String galleryName = dataEntity.gallery_name;
//                        if (TextUtils.isEmpty(galleryName)) {
//                            galleryName = dataEntity.gallery_en_name;
//                        }
//                        holder.mHomeItemGalleryName.setText(CodeUtils.decodeUrl(galleryName));
//
//                        //头像
//                        if (holder.mHeadImage != null) {
//                            ImageLoadUtils.loadImage(dataEntity.user_avatar, holder.mHeadImage);
//                        }
//
//                        //时间
//                        if (holder.mTime != null) {
//                            holder.mTime.setText(YuntooArtUtils.showDate(dataEntity.story_create_time));
//                        }
//                        //阅读数
//                        if (holder.mReadCount != null) {
//                            holder.mReadCount.setText(YuntooArtUtils.fixReadCount(dataEntity.story_reading_count));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                case ITEM_STORY_TYPE: //故事
//
//                    try {
//
//                        //图片
//                        if (holder.mHomeRlItemIv != null) {
//
//                            if (!TextUtils.isEmpty(dataEntity.story_cover_url)) {
//                                if (dataEntity.story_cover_url.contains(".gif")) {
//                                    holder.mHomeRlItemIv_Gif1.setVisibility(View.VISIBLE);
//                                    //用gif图的加载方式
//                                    ImageLoadUtils.loadImage_Gif1(dataEntity.story_cover_url, holder.mHomeRlItemIv, dataEntity.gif_image, holder.mHomeRlItemIv_Gif1);
//                                } else {
//                                    holder.mHomeRlItemIv_Gif1.setVisibility(View.GONE);
//                                    ImageLoadUtils.loadImage(dataEntity.story_cover_url, holder.mHomeRlItemIv);
//                                }
//                            }
//                        }
//
//                        //标题
//                        if (TextUtils.isEmpty(dataEntity.story_title)) {
//                            holder.mHomeRlItemTitle.setVisibility(View.GONE);
//                            holder.mHomeRlItemTitle.setText("");
//                        } else {
//                            holder.mHomeRlItemTitle.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTitle.setText(CodeUtils.decodeUrl(dataEntity.story_title));
//                        }
//
//                        //描述
//                        if (TextUtils.isEmpty(dataEntity.description)) {
//                            holder.mHomeRlItemTv.setVisibility(View.GONE);
//                            holder.mHomeRlItemTv.setText("");
//                        } else {
//                            holder.mHomeRlItemTv.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTv.setText(CodeUtils.decodeUrl(dataEntity.description));
//                        }
//
//                        //艺术馆名
//                        String galleryName = dataEntity.gallery_name;
//                        if (TextUtils.isEmpty(galleryName)) {
//                            galleryName = dataEntity.gallery_en_name;
//                        }
//                        holder.mHomeItemGalleryName.setText(CodeUtils.decodeUrl(galleryName));
//
////                        //判断是否有标签，有就设置，没有就不设置
////                        if (holder.mHomeItemTagText1 != null && holder.mHomeItemTagText2 != null) {
////                            String tag = CodeUtils.decodeUrl(dataEntity.story_tag);
////                            if (!TextUtils.isEmpty(tag)) {
////                                String[] tags = tag.split(",");
////                                for (int i = 0; i < tags.length; i++) {
////                                    switch (i) {
////                                        case 0:
////                                            holder.mHomeItemTagText1.setText(tags[0]);
////                                            holder.mHomeItemTagText1.setVisibility(View.VISIBLE);
////                                            holder.mHomeItemTagText2.setVisibility(View.GONE);
////                                            break;
////                                        case 1:
////                                            holder.mHomeItemTagText2.setText(tags[1]);
////                                            holder.mHomeItemTagText2.setVisibility(View.VISIBLE);
////                                            break;
////                                    }
////                                }
////                            } else {
////                                holder.mHomeItemTagText1.setVisibility(View.GONE);
////                                holder.mHomeItemTagText2.setVisibility(View.GONE);
////                            }
////                        }
//
//                        //头像
//                        if (holder.mHeadImage != null) {
//                            ImageLoadUtils.loadImage(dataEntity.user_avatar, holder.mHeadImage);
//                        }
//
//                        //时间
//                        if (holder.mTime != null) {
//                            holder.mTime.setText(YuntooArtUtils.showDate(dataEntity.story_create_time));
//                        }
//                        //阅读数
//                        if (holder.mReadCount != null) {
//                            holder.mReadCount.setText(YuntooArtUtils.fixReadCount(dataEntity.story_reading_count));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                case ITEM_VIDEO_TYPE: //视频
//
//                    try {
//                        if (holder.mHomeItemVideoView != null) {
//                            String url = dataEntity.video_url;
//                            currentVideoView = holder.mHomeItemVideoView;
//
//                            if (TextUtils.isEmpty(url)) url = "";
//
//                            holder.mHomeItemVideoView.setVideoUrl(url, dataEntity.story_cover_url);
//                            String time = "";
//                            if (dataEntity.video_total_time > 0) {
//                                if (dataEntity.video_total_time >= 60) {
//                                    time = dataEntity.video_total_time / 60 + "'" + dataEntity.video_total_time % 60 + "\"";
//                                } else {
//                                    time = dataEntity.video_total_time % 60 + "\"";
//                                }
//                                holder.mHomeItemVideoView.setTimeTag(time);
//                            } else {
//                                holder.mHomeItemVideoView.setTimeTag("");
//                            }
//                        }
//
//                        YuntooArtUtils.setImageColor(dataEntity.gallery_cover_ave, holder.mHomeItemVideoView.getCover());//图片色
//
//                        //标题
//                        if (TextUtils.isEmpty(dataEntity.story_title)) {
//                            holder.mHomeRlItemTitle.setVisibility(View.GONE);
//                            holder.mHomeRlItemTitle.setText("");
//                        } else {
//                            holder.mHomeRlItemTitle.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTitle.setText(CodeUtils.decodeUrl(dataEntity.story_title));
//                        }
//
//                        //描述
//                        if (TextUtils.isEmpty(dataEntity.description)) {
//                            holder.mHomeRlItemTv.setVisibility(View.GONE);
//                            holder.mHomeRlItemTv.setText("");
//                        } else {
//                            holder.mHomeRlItemTv.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTv.setText(CodeUtils.decodeUrl(dataEntity.description));
//                        }
//
//                        //艺术馆名
//                        String galleryName = dataEntity.gallery_name;
//                        if (TextUtils.isEmpty(galleryName)) {
//                            galleryName = dataEntity.gallery_en_name;
//                        }
//                        holder.mHomeItemGalleryName.setText(CodeUtils.decodeUrl(galleryName));
//
//                        //头像
//                        if (holder.mHeadImage != null) {
//                            ImageLoadUtils.loadImage(dataEntity.user_avatar, holder.mHeadImage);
//                        }
//
//                        //时间
//                        if (holder.mTime != null) {
//                            holder.mTime.setText(YuntooArtUtils.showDate(dataEntity.story_create_time));
//                        }
//                        //阅读数
//                        if (holder.mReadCount != null) {
//                            holder.mReadCount.setText(YuntooArtUtils.fixReadCount(dataEntity.story_reading_count));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                case ITEM_LIST_TYPE: //图集
//                    try {
//                        imageList = dataEntity.story_image_list;//TODO 临时
//                        if (holder.mHomeItemGallery != null) {
//                            holder.mHomeItemGallery.setData(dataEntity.story_image_list, dataEntity.story_id + "", dataEntity.gallery_id + "");
//                        }
//
//                        //标题
//                        if (TextUtils.isEmpty(dataEntity.story_title)) {
//                            holder.mHomeRlItemTitle.setVisibility(View.GONE);
//                            holder.mHomeRlItemTitle.setText("");
//                        } else {
//                            holder.mHomeRlItemTitle.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTitle.setText(CodeUtils.decodeUrl(dataEntity.story_title));
//                        }
//
//                        //描述
//                        if (TextUtils.isEmpty(dataEntity.description)) {
//                            holder.mHomeRlItemTv.setVisibility(View.GONE);
//                            holder.mHomeRlItemTv.setText("");
//                        } else {
//                            holder.mHomeRlItemTv.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTv.setText(CodeUtils.decodeUrl(dataEntity.description));
//                        }
//
//                        //艺术馆名
//                        String galleryName = dataEntity.gallery_name;
//                        if (TextUtils.isEmpty(galleryName)) {
//                            galleryName = dataEntity.gallery_en_name;
//                        }
//                        holder.mHomeItemGalleryName.setText(CodeUtils.decodeUrl(galleryName));
//
//                        //头像
//                        if (holder.mHeadImage != null) {
//                            ImageLoadUtils.loadImage(dataEntity.user_avatar, holder.mHeadImage);
//                        }
//
//                        //时间
//                        if (holder.mTime != null) {
//                            holder.mTime.setText(YuntooArtUtils.showDate(dataEntity.story_create_time));
//                        }
//                        //阅读数
//                        if (holder.mReadCount != null) {
//                            holder.mReadCount.setText(YuntooArtUtils.fixReadCount(dataEntity.story_reading_count));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                case ITEM_ARTS_TYPE: //艺术馆
//
//                    try {
////                        if (holder.mHomeRlItemIv != null) {
////                            ImageLoadUtils.loadImage(dataEntity.gallery_cover, holder.mHomeRlItemIv);
////                        }
//
//                        if (holder.mHomeItemArtsImageList != null && imageList != null) {
//                            holder.mHomeItemArtsImageList.setData(imageList, dataEntity.story_id + "", dataEntity.gallery_id + "");
//                        }
//
//                        //标题
//                        if (TextUtils.isEmpty(dataEntity.story_title)) {//TODO
//                            holder.mHomeRlItemTitle.setVisibility(View.GONE);
//                            holder.mHomeRlItemTitle.setText("");
//                        } else {
//                            holder.mHomeRlItemTitle.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTitle.setText(CodeUtils.decodeUrl(dataEntity.story_title));
//                        }
//
//                        //描述
//                        if (TextUtils.isEmpty(dataEntity.gallery_description)) {
//                            holder.mHomeRlItemTv.setVisibility(View.GONE);
//                            holder.mHomeRlItemTv.setText("");
//                        } else {
//                            holder.mHomeRlItemTv.setVisibility(View.VISIBLE);
//                            holder.mHomeRlItemTv.setText(CodeUtils.decodeUrl(dataEntity.gallery_description));
//                        }
//
//                        //艺术馆名
//                        String galleryName = dataEntity.gallery_name;
//                        if (TextUtils.isEmpty(galleryName)) {
//                            galleryName = dataEntity.gallery_en_name;
//                        }
//                        holder.mHomeItemGalleryName.setText(CodeUtils.decodeUrl(galleryName));
//
//                        //头像
//                        if (holder.mHeadImage != null) {
//                            ImageLoadUtils.loadImage(dataEntity.user_avatar, holder.mHeadImage);
//                        }
//
//                        //阅读数
//                        if (holder.mReadCount != null) {
//                            holder.mReadCount.setText("他在24小时内被阅读" + YuntooArtUtils.fixReadCount(dataEntity.story_reading_count) + "次");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    return;//自己的点击
//            }
//
//            //如果加载到了倒数 第某条，就请求更多数据
//            if (listData.size() >= 4) {
//                if (position == listData.size() - 4) {
//                    //加载更多
//                    loadMoreData(listData.size());
//                }
//            }
//
//        }
//
//
//        //点击回调
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dataEntity != null) {
//                    Intent intent = new Intent();
////                    intent.putExtra("detailUrl", YuntooArtUtils.createDetailUrl(dataEntity.story_id + "", dataEntity.gallery_id + ""));
//                    intent.putExtra("storyId", dataEntity.story_id + "");
//                    intent.putExtra("storyTitle", dataEntity.story_title + "");
//                    intent.putExtra("userNickname", dataEntity.user_nickname + "");
//
//                    UIUtils.startActivity(DetailActivity_test.class, intent);
//                }
//            }
//        });
//    }
//
//    private List<HomeListBeanV2.DataEntity.ImageListEntry> imageList;
//
//    //加载更多的标记
//    private boolean isLoadMore = false;
//    private boolean isError = false;
//
//    /**
//     * 加载更多的方法
//     *
//     * @param index 当前最后一条的索引
//     */
//    private synchronized void loadMoreData(final int index) {
//
//        if (listData == null || listData.size() < startOffset) {
//            isError = true;
//            return;
//        }
//
//
//        if (!isLoadMore) {
//            isLoadMore = true;
//            isError = false;
//
//
//            if (mFootView != null && currentRecyclerView != null) {
//                mFootView.setVisibility(View.VISIBLE);
//            }
//
//            NetUtils.getDataByNet(requestMoreUrl, HomeListAdapter.class, RequestMapData.params_ListMore(offset + "", limit + ""), new HomeListBeanV2Parser(), new NetUtils.SuccessListener() {
//                @Override
//                public void onSuccess(Object data) {
//
//                    try {
//                        //得到数据后更新
//                        HomeListBeanV2 homeListBean = (HomeListBeanV2) data;
//
//                        if (1 == homeListBean.success && homeListBean.data != null) {
//
//                            offset = offset + homeListBean.data.size();
//
//                            listData.addAll(homeListBean.data); //添加数据
//                            notifyItemRangeInserted(index + 1, homeListBean.data.size()); //范围插入
//                        } else {
//                            isError = true;
//                            if (mFootView != null && currentRecyclerView != null) {
////                                currentRecyclerView.smoothScrollBy(0, -(mFootView.getMeasuredHeight() + UIUtils.getDimens(R.dimen.base12dp)));
//                                mFootView.setVisibility(View.GONE);
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        isError = true;
//                    }
//
//                }
//
//                @Override
//                public void onError(String msg) {
//                    isLoadMore = false;
//                    LogUtils.e(msg);
//
//                    isError = true;
//                }
//
//                @Override
//                public void saveJson(String json) {
//                    isLoadMore = false;
//                }
//            });
//        }
//
//
//    }
//
//    private OnRefreshCompleteListener onRefreshCompleteListener;
//
//    /**
//     * 设置刷新完成监听
//     *
//     * @param onRefreshCompleteListener
//     */
//    public void setOnRefreshCompleteListener(OnRefreshCompleteListener onRefreshCompleteListener) {
//        this.onRefreshCompleteListener = onRefreshCompleteListener;
//    }
//
//    /**
//     * 重新请求数据
//     */
//    public synchronized void refreshData() {
//
//        if (mFootView != null) {
//            mFootView.setVisibility(View.VISIBLE);
//        }
//
//        //第一次请求 ，刷新 10条
//        NetUtils.getDataByNet(requestUrl, HomeListAdapter.class, RequestMapData.params_ListMore(0 + "", startOffset + ""), new HomeListBeanV2Parser(), new NetUtils.SuccessListener() {
//            @Override
//            public void onSuccess(Object data) {
//
//                if (onRefreshCompleteListener != null) {
//                    onRefreshCompleteListener.onRefreshCompleter();
//                }
//
//                //得到数据后更新
//                try {
//                    HomeListBeanV2 homeListBean = (HomeListBeanV2) data;
//
//                    if (1 == homeListBean.success) {
//                        listData = homeListBean.data;
//                        if (listData != null) {
//                            notifyDataSetChanged();
//
//                            getGalleryItem();//请求艺术馆信息
//                            offset = homeListBean.data.size();
//                            if (offset < startOffset && mFootView != null) {
//                                mFootView.setVisibility(View.GONE);
////                                UIUtils.showToastSafe("没有更多数据");
//                            }
//                        }
//                    } else {
//                        if (mFootView != null) {
//                            mFootView.setVisibility(View.GONE);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    if (mFootView != null) {
//                        mFootView.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(String msg) {
//                LogUtils.e(msg);
//                if (mFootView != null) {
//                    mFootView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void saveJson(String json) {
//                //把请求结果保存到sp中缓存
//                YuntooArtUtils.getSharedPreferences().edit().putString(Const.CACHE_HOME_LIST, json).commit();
//            }
//        });
//    }
//
//    /**
//     * 请求艺术馆信息
//     */
//    private synchronized void getGalleryItem() {
//
//        if (true) return; //暂时取消
//        //TODO 判断用户是否登录，如果登录就请求推荐艺术馆信息
//        if (YuntooArtUtils.userState())
//            NetUtils.getDataByNet(requestGalleryUrl, HomeListAdapter.class, RequestMapData.baseParamsMap(), new HomeListBeanV2Parser(), new NetUtils.SuccessListener() {
//
//                @Override
//                public void onSuccess(Object data) {
//                    //得到数据后更新
//                    try {
//                        HomeListBeanV2 homeListBean = (HomeListBeanV2) data;
//
//                        if (1 == homeListBean.success) {
//                            List<HomeListBeanV2.DataEntity> list = homeListBean.data;
//                            //遍历集合添加到列表中，更新
////                        for (int i = 0; i < list.size(); i++) {
//                            for (int i = 0; i < 1; i++) {
//                                int index = list.get(i).location - 1;
//                                if (listData.size() <= index) index = listData.size() - 1;
//                                listData.add(index, list.get(i));
//                                //通知变更
//                                LogUtils.d(index + "");
//                                notifyItemInserted(index);
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String msg) {
//                    LogUtils.e(msg);
//                }
//
//                @Override
//                public void saveJson(String json) {
//                }
//            });
//    }
//
//
//    /**
//     * 修正position
//     *
//     * @param position
//     * @return
//     */
//    private int fixPosition(int position) {
//        //脚布局不影响，不加入修正
//        return position - 1;
//    }
//
//    @Override
//    public int getItemCount() {
//        int count = listData == null ? 0 : listData.size();
//        return count + 2;
//    }
//
//    public void setCurrentRecyclerView(final RecyclerView currentRecyclerView) {
//        this.currentRecyclerView = currentRecyclerView;
//
//        currentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    try {
//                        if (mFootView != null && currentRecyclerView != null) {
////                        mFootView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
//                            if (((LinearLayoutManager) currentRecyclerView.getLayoutManager()).findLastVisibleItemPosition() == (getItemCount() - 1)) {
//                                if (isError) {
//                                    currentRecyclerView.smoothScrollBy(0, -(mFootView.getMeasuredHeight() + UIUtils.getDimens(R.dimen.base12dp)));
//                                    mFootView.setVisibility(View.GONE);
//                                }
//                                loadMoreData(listData == null ? 0 : listData.size());
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//    }
//
//    /**
//     * 首页List的　ViewHolder
//     */
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        //
//        public TextView mReadCount;
//        public TextView mTime;
//
//        public SimpleDraweeView mHomeRlItemIv;
//        public SimpleDraweeView mHomeRlItemIv_Gif1;//第一帧gif
//        public TextView mHomeRlItemTv;
//        public TextView mHomeItemTagText2;
//        public TextView mHomeItemTagText1;
//
//        public SimpleDraweeView mHeadImage; //头像
//
//        public TextView mHomeItemGalleryName;//艺术馆名
//        public HomeItem_Arts_ImageList mHomeItemArtsImageList;//艺术馆图片列表
//        public TextView mHomeRlItemTitle;//标题
//
//        //视频控件
//        public ItemVideoView mHomeItemVideoView;
//        public TextView mHomeRlItemTvBelow;
//
//        //横向图片列表图片
//        public ItemGallery mHomeItemGallery;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            int itemType = (int) itemView.getTag();
//
//            if (itemType == HEAD_TYPE) {
//
//                return;//头
//            }
//            if (itemType == FOOT_TYPE) {
//                //点击加载更多
//                itemView.findViewById(R.id.loadingView).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loadMoreData(listData == null ? 0 : listData.size());
//                    }
//                });
//
//                return;//脚
//            }
//
//
//            switch (itemType) {
//                case ITEM_GIF_TYPE: // GIF
//
//                    mHomeRlItemIv = (SimpleDraweeView) itemView.findViewById(R.id.home_rl_item_iv);
//                    mHomeRlItemIv_Gif1 = (SimpleDraweeView) itemView.findViewById(R.id.home_rl_item_iv_gif1);
//                    mHomeRlItemTv = (TextView) itemView.findViewById(R.id.home_rl_item_tv);
//                    mHomeRlItemTitle = (TextView) itemView.findViewById(R.id.home_rl_item_title);
//                    mHomeItemGalleryName = (TextView) itemView.findViewById(R.id.homeItemGalleryName);
//                    mHeadImage = (SimpleDraweeView) itemView.findViewById(R.id.my_head_image);
//                    mTime = (TextView) itemView.findViewById(R.id.item_time);
//                    mReadCount = (TextView) itemView.findViewById(R.id.itemReadCount);
//
//                    break;
//
//                case ITEM_STORY_TYPE: // 故事
//
//                    mHomeRlItemIv = (SimpleDraweeView) itemView.findViewById(R.id.home_rl_item_iv);
//                    mHomeRlItemIv_Gif1 = (SimpleDraweeView) itemView.findViewById(R.id.home_rl_item_iv_gif1);
//                    mHomeRlItemTv = (TextView) itemView.findViewById(R.id.home_rl_item_tv);
//                    mHomeRlItemTitle = (TextView) itemView.findViewById(R.id.home_rl_item_title);
//                    mHomeItemGalleryName = (TextView) itemView.findViewById(R.id.homeItemGalleryName);
//                    mHomeItemTagText2 = (TextView) itemView.findViewById(R.id.homeItemTagText2);
//                    mHomeItemTagText1 = (TextView) itemView.findViewById(R.id.homeItemTagText1);
//                    mHeadImage = (SimpleDraweeView) itemView.findViewById(R.id.my_head_image);
//                    mTime = (TextView) itemView.findViewById(R.id.item_time);
//                    mReadCount = (TextView) itemView.findViewById(R.id.itemReadCount);
//
//                    break;
//
//                case ITEM_LIST_TYPE: // 横向图集 自己实现点击跳转
//
//                    mHomeItemGallery = (ItemGallery) itemView.findViewById(R.id.home_itemGallery);
//                    mHomeRlItemTv = (TextView) itemView.findViewById(R.id.home_rl_item_tv);
//                    mHomeRlItemTitle = (TextView) itemView.findViewById(R.id.home_rl_item_title);
//                    mHomeItemGalleryName = (TextView) itemView.findViewById(R.id.homeItemGalleryName);
//                    mHeadImage = (SimpleDraweeView) itemView.findViewById(R.id.my_head_image);
//                    mTime = (TextView) itemView.findViewById(R.id.item_time);
//                    mReadCount = (TextView) itemView.findViewById(R.id.itemReadCount);
//
//                    break;
//                case ITEM_VIDEO_TYPE: // 视频
//
//                    mHomeItemVideoView = (ItemVideoView) itemView.findViewById(R.id.home_itemVideoView);
//
//                    mHomeItemVideoView.setOnPlayListener(new OnPlayListener() {
//                        @Override
//                        public void onPlay(ItemVideoView itemVideoView) {
//                            if (preVideoView != null) {
//                                preVideoView.resetView();
//                            }
//                            preVideoView = itemVideoView;
//                        }
//                    });
//
//                    mHomeRlItemTv = (TextView) itemView.findViewById(R.id.home_rl_item_tv);
//                    mHomeRlItemTitle = (TextView) itemView.findViewById(R.id.home_rl_item_title);
//                    mHomeItemGalleryName = (TextView) itemView.findViewById(R.id.homeItemGalleryName);
//                    mHeadImage = (SimpleDraweeView) itemView.findViewById(R.id.my_head_image);
//                    mTime = (TextView) itemView.findViewById(R.id.item_time);
//                    mReadCount = (TextView) itemView.findViewById(R.id.itemReadCount);
//
//                    break;
//                case ITEM_ARTS_TYPE: // 艺术馆
//
//                    mHomeItemArtsImageList = (HomeItem_Arts_ImageList) itemView.findViewById(R.id.home_rl_item_imageList);
//                    mHomeRlItemTv = (TextView) itemView.findViewById(R.id.home_rl_item_tv);
//                    mHomeRlItemTitle = (TextView) itemView.findViewById(R.id.home_rl_item_title);
//                    mHomeItemGalleryName = (TextView) itemView.findViewById(R.id.homeItemGalleryName);
//                    mHeadImage = (SimpleDraweeView) itemView.findViewById(R.id.my_head_image);
//                    mReadCount = (TextView) itemView.findViewById(R.id.itemReadCount);
//
//                    // 打开艺术馆
//                    itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            openArts();//打开艺术馆
//                        }
//                    });
//                    mHomeItemArtsImageList.setOnItemClickCallBack(new OnClickStartActivityListener() {
//                        @Override
//                        public void onItemClick() {
//                            openArts();//打开艺术馆
//                        }
//                    });
//
//
//                    //给删除按钮设置点击
//                    itemView.findViewById(R.id.item_art_delete).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int position = fixPosition(getAdapterPosition());
//                            if (listData.get(position) != null) {
//                                //删除艺术馆
//                                NetUtils.pushStringByNet_POST(requestDeleteGalleryUrlStart + listData.get(position).gallery_id + requestDeleteGalleryUrlEnd, RequestMapData.baseParamsMap(), null);
//                            }
//
//                            listData.remove(position);
//                            notifyItemRemoved(position);
//                        }
//                    });
//
//                    //订阅按钮
//                    itemView.findViewById(R.id.subscribeButton).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int position = fixPosition(getAdapterPosition());
//                            try {
//                                HomeListBeanV2.DataEntity dataEntity = listData.get(position);
//                                if (dataEntity != null) {
//                                    YuntooArtUtils.changeSubscribeButtonState((TextView) v, !v.isSelected());
//                                    UIUtils.showToastSafe("点击");
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    });
//
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        private void openArts() {
//            final HomeListBeanV2.DataEntity dataEntity = listData.get(fixPosition(getAdapterPosition()));
//            if (dataEntity != null) {
//                Map<String, String> artsInfo = new HashMap<String, String>();
//                artsInfo.put("id", dataEntity.gallery_id + "");
//                artsInfo.put("name", CodeUtils.decodeUrl(dataEntity.gallery_name));
//                artsInfo.put("enName", CodeUtils.decodeUrl(dataEntity.gallery_en_name));
//                artsInfo.put("description", CodeUtils.decodeUrl(dataEntity.gallery_description));
//                artsInfo.put("cover", dataEntity.gallery_cover);
////                UIUtils.startActivity(ArtsActivity.class, artsInfo);
//            }
//        }
//    }
//
//    private ItemVideoView preVideoView;//前一个播放的VideoView
//
//    public interface OnPlayListener {
//        void onPlay(ItemVideoView itemVideoView);
//    }
//
//
//    /**
//     * 停止活动
//     */
//    public void stopAction() {
//        //停止自动轮播
//        //停止播放视频
//        if (currentVideoView != null) {
//            currentVideoView.resetView();
//        }
//
//        // 取消之前的所有请求
//        if (onRefreshCompleteListener != null) {
//            onRefreshCompleteListener.onRefreshCompleter();
//        }
//        NetUtils.cancelAllRequest(HomeListAdapter.class);
//        isLoadMore = false;
//    }
//
//    /**
//     * 刷新
//     */
//    public void refresh() {
//        // 刷新头数据
//        if (mHeadView != null && mHeadView instanceof LoopViewPager) {
//            ((LoopViewPager) mHeadView).requestData();
//        }
//        refreshData();
//        isLoadMore = false;
//    }
//}
