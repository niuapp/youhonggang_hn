package com.xxx.handnote.activity.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.handnote.R;
import com.xxx.handnote.base.OnItemClickListener;
import com.xxx.handnote.utils.LogUtils;
import com.xxx.handnote.utils.UIUtils;


/**
 * --> 加载更多 可配置的 RecyclerView
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {

    protected final int HEAD_TYPE = -2;
    protected final int FOOT_TYPE = -3;
    protected final int NULL_TYPE = -1;

    //加载更多的标记
    protected boolean isLoadMore = false;
    protected boolean isError = false;
    protected RecyclerView currentRecyclerView;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //不同类型的Item
        View itemView = null;


        if (viewType == HEAD_TYPE) { //头
            if (mHeadView != null) {
                itemView = mHeadView;
            } else {
                itemView = UIUtils.inflate(R.layout.nullview);
                mHeadView = itemView;
            }
        } else if (viewType == FOOT_TYPE) { //脚
            if (mFootView != null) {
                itemView = mFootView;
            } else {
                itemView = UIUtils.inflate(R.layout.item_foot_loadingview);
                mFootView = itemView;
            }

        } else if (viewType == NULL_TYPE) {
            itemView = UIUtils.inflate(R.layout.nullview);
        } else {
            //其他条目
            itemView = adapterCreateView(parent, viewType);
        }

        //不同类型的Item
        if (itemView == null) {
            itemView = UIUtils.inflate(R.layout.nullview);
        }

        itemView.setTag(viewType);//记录当前条目类型
        return getChildViewHolder(itemView);
    }

    /**
     * 获取当前RecyclerView
     *
     * @return
     */
    public RecyclerView getCurrentRecyclerView() {
        return currentRecyclerView;
    }

    /**
     * 自己的ViewHolder
     *
     * @param itemView
     * @return
     */
    protected abstract ViewHolder getChildViewHolder(View itemView);

    /**
     * 创建ViewHolder的itemView
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View adapterCreateView(ViewGroup parent, int viewType);

    /**
     * 加载更多
     */
    protected void loadMore() {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_TYPE;
        } else if (position == getItemCount() - 1) {
            return FOOT_TYPE;
        } else {
            //内容条目，不同的类型
            position = fixPosition(position);

            //子类设置条目类型
            return getAdapterItemType(position);
        }
    }

    /**
     * 返回条目类型
     *
     * @param position
     * @return
     */
    protected abstract int getAdapterItemType(int position);

    /**
     * 在头布局设置数据时
     */
    protected void bindViewHolder_HEAD_TYPE() {

    }

    /**
     * 在脚布局设置数据时
     */
    protected void bindViewHolder_FOOT_TYPE() {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == HEAD_TYPE) {
            bindViewHolder_HEAD_TYPE();
            return;//头设置
        }
        if (itemType == FOOT_TYPE) {
            bindViewHolder_FOOT_TYPE();

            loadMore(); //加载更多
            return;//脚设置
        }
        if (itemType == NULL_TYPE) {
            return;//空布局
        }

        //修正position
        position = fixPosition(position);

        if (onAdapterBindViewHolder(holder, position, itemType)) {
            //点击回调
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, holder);
                    }
                }
            });
        }
    }

    /**
     * 绑定条目数据
     *
     * @param holder
     * @param position
     * @param itemType
     * @return 是否使用点击回调
     */
    protected abstract boolean onAdapterBindViewHolder(ViewHolder holder, int position, int itemType);

    @Override
    public int getItemCount() {
        return getAdapterItemCount() + 2; // + 头脚布局
    }

    public abstract int getAdapterItemCount();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected View mHeadView;//头布局
    protected View mFootView;//脚布局

    /**
     * 设置头布局
     *
     * @param headView
     */
    public void setHeadView(View headView) {
        this.mHeadView = headView;
        notifyItemInserted(0);
    }

    public View getmHeadView() {
        return mHeadView;
    }


    /**
     * 设置脚布局
     *
     * @param footView
     */
    public void setFootView(View footView) {
        this.mFootView = footView;
        mFootView.setVisibility(View.VISIBLE);
        notifyItemInserted(getItemCount() - 1);
    }

    public View getmFootView() {
        return mFootView;
    }

    /**
     * 与此Adapter相关联的RecyclerView
     *
     * @param currentRecyclerView
     */
    public void setCurrentRecyclerView(final RecyclerView currentRecyclerView) {
        this.currentRecyclerView = currentRecyclerView;

        // RecyclerView 滚动监听，上拉加载更多
        currentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (true || newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {//true 临时添加 待

                    try {
                        if (mFootView != null && currentRecyclerView != null) {
//                        mFootView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                            boolean flag = false;

                            if (currentRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {

                                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) currentRecyclerView.getLayoutManager()).findLastVisibleItemPositions(null);//最后一个显示的条目
                                int lastVisibleItemPosition = -1;
                                if (lastVisibleItemPositions.length == 2) {
                                    lastVisibleItemPosition = Math.max(lastVisibleItemPositions[0], lastVisibleItemPositions[1]);
                                }
                                flag = (lastVisibleItemPosition == (getItemCount() - 1));

                            } else if (currentRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                                flag = ((LinearLayoutManager) currentRecyclerView.getLayoutManager()).findLastVisibleItemPosition() == (getItemCount() - 1);
                            }

                            if (flag) {
                                if (isError) {
                                    if (isCanFullScreen()){
                                        //判断RecyclerView中的条目是否可以占满一个屏幕  可以才回弹
                                        int bottom = currentRecyclerView.getHeight() - mFootView.getTop();
//                                    LogUtils.d(" bottom -->  " + bottom);
                                        currentRecyclerView.smoothScrollBy(0, -bottom);
                                        mFootView.setVisibility(View.GONE);
                                    }
                                } else {
                                    loadMore();
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * RecyclerView有效条目是否可以填充屏幕
     * @return
     */
    private boolean isCanFullScreen(){
        if (currentRecyclerView == null) return false;
        View firstView = currentRecyclerView.getChildAt(0);
        if (firstView != null){
            LogUtils.d("top --> " + firstView.getTop());
            if (firstView.getTop() == 0){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    /**
     * 修正position
     *
     * @param position
     * @return
     */
    private int fixPosition(int position) {
        //脚布局不影响，不加入修正
        return position - 1;
    }

    //=============== 条目点击回调 ===============
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //===========================================
}
