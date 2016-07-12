package com.xxx.handnote.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xxx.handnote.R;
import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.utils.image.ImageLoadUtils;
import com.xxx.handnote.utils.image.ImagePopwInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * -->  首页图片列表
 */
public class HomeItem_ImageList extends FrameLayout {

    private Context context;
    private RecyclerView recyclerView;
    private ImageListAdapter imageListAdapter;

    private List<String> imageList;

    public HomeItem_ImageList(Context context) {
        super(context);
        initView(context);
    }

    public HomeItem_ImageList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeItem_ImageList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initView(Context context) {
        this.context = context;

        //关联布局
        View.inflate(context, R.layout.item_imagelist, this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);// 水平方向
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.invalidateItemDecorations();

        imageListAdapter = new ImageListAdapter();
        recyclerView.setAdapter(imageListAdapter);
    }

    /**
     * 刷新数据
     */
    public void resetView() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    /**
     * 设置图集数据
     *
     * @param imageList
     */
    public void setData(List<String> imageList) {
        this.imageList = imageList;
        recyclerView.getAdapter().notifyDataSetChanged();

        urlList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            urlList.add(new ImagePopwInfoBean(imageList.get(i)));
        }
    }

    private List<ImagePopwInfoBean> urlList;

    private class ImageListAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(UIUtils.inflate(R.layout.item_image));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            try {
                final String image_url = imageList.get(position);

                ImageLoadUtils.loadImage(image_url, holder.simpleDraweeView);

                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePopView.showImagePopw(urlList, position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return imageList == null ? 0 : imageList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView simpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);

            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_iv);
        }
    }

    /**
     * 返回recyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
