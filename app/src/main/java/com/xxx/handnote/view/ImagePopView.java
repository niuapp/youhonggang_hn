package com.xxx.handnote.view;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.xxx.handnote.R;
import com.xxx.handnote.base.BaseActivity;
import com.xxx.handnote.base.OnContinueRunnable;
import com.xxx.handnote.utils.UIUtils;
import com.xxx.handnote.utils.ViewUtils;
import com.xxx.handnote.utils.image.ExtendedViewPager;
import com.xxx.handnote.utils.image.ImageLoadUtils;
import com.xxx.handnote.utils.image.ImagePopwInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --> 图片
 */
public class ImagePopView {

    private static PopupWindow imageWindow;
    private static ExtendedViewPager evp;

    /**
     * 图片展示的窗口，需要当前点击图片的位置，图片所在集合
     */
    public static void showImagePopw(List<ImagePopwInfoBean> urlList, int index, final OnContinueRunnable onContinueRunnable) {
        View view = UIUtils.inflate(R.layout.popw_image);

        if (imageWindow == null) {
            imageWindow = new PopupWindow(UIUtils.getContext());
        }


        imageWindow.setOutsideTouchable(true);//点击外部关闭
        imageWindow.setFocusable(true);//输入框
        imageWindow.setHeight(((BaseActivity) UIUtils.getForegroundActivity()).getCurrentActivityView().getMeasuredHeight());
        imageWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        imageWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        imageWindow.setBackgroundDrawable(new ColorDrawable(0));

        initView(view, urlList, index);
        imageWindow.setContentView(view);

        //关闭监听，得到关闭的index
        imageWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onContinueRunnable != null && evp != null && evp.getCurrentItem() >= 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    int index = evp.getCurrentItem();
                    map.put("index", index + "");
                    onContinueRunnable.continueRunnable(map);
                }
            }
        });

        imageWindow.showAtLocation(((BaseActivity) UIUtils.getForegroundActivity()).getCurrentActivityView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 图片展示的窗口，需要当前点击图片的位置，图片所在集合
     */
    public static void showImagePopw(List<ImagePopwInfoBean> urlList, int index) {

        showImagePopw(urlList, index, null);
    }

    /**
     * 初始化View
     *
     * @param view
     * @param urlList
     * @param index
     */
    private static void initView(final View view, final List<ImagePopwInfoBean> urlList, int index) {
        //预览ViewPager
        evp = (ExtendedViewPager) view.findViewById(R.id.imagePopVP);
//        evp.setOffscreenPageLimit(2);
        evp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return urlList == null ? 0 : urlList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View itemView = null;

                String imageUrl = urlList.get(position).imageUrl;
                if (TextUtils.isEmpty(imageUrl)) imageUrl = "";


                //判断是否是gif，用不同的控件加载
                if (imageUrl.contains(".gif")) {
                    itemView = UIUtils.inflate(R.layout.item_image_vp_item_gif);
                    SimpleDraweeView simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.imageViewPagerItem_image);


                    simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            closeImageWindow();
                        }
                    });

                    ImageLoadUtils.loadImage(imageUrl, simpleDraweeView);

                } else {
                    itemView = UIUtils.inflate(R.layout.item_image_vp_item);
                    ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewPagerItem_image);

                    View loadView = itemView.findViewById(R.id.imageLoadView);

                    int w = urlList.get(position).width;
                    int h = urlList.get(position).height;
//                    int mw = UIUtils.getMaxWidth();
//
//                    LogUtils.d("w -> " + w + "  h -> " + h + "  mw -> " + mw);
//
//                    //TODO 比例
//                    int height = (int) (( (urlList.get(position).width * 1.0f) / urlList.get(position).height ) * UIUtils.getMaxWidth());
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//
//                    layoutParams.width = w;
//                    layoutParams.height = h;
//                    imageView.setLayoutParams(layoutParams);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            closeImageWindow();
                        }
                    });

//                    imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(UIUtils.getForegroundActivity());
//
//                            builder.setMessage("点击关闭");
//                            builder.setNegativeButton("取消", null);
//
//                            builder.show();
//                            return false;
//                        }
//                    });

                    try {
//                        Netroid.displayImage(imageUrl, imageView, loadView, w, h);
                        Picasso.with(UIUtils.getContext())
                                .load(imageUrl)
                                .resize(UIUtils.getMaxWidth(), UIUtils.getMaxHeight())
                                .centerInside()
                                .placeholder(R.drawable.placehold)
                                .error(R.drawable.placehold)
                                .into(imageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                final View imageInfoLayout = itemView.findViewById(R.id.imageInfoLayout);
                imageInfoLayout.setVisibility(View.VISIBLE);
                imageInfoLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageInfoLayout.setVisibility(View.GONE);//点击隐藏图片信息
                    }
                });

                View showInfoButton = itemView.findViewById(R.id.imageInfoBtn);
                showInfoButton.setVisibility(View.VISIBLE);
                showInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageInfoLayout.setVisibility(View.VISIBLE);//点击展示图片信息
                    }
                });

                String info1 = TextUtils.isEmpty(urlList.get(position).imageInfo1) ? "" : urlList.get(position).imageInfo1.trim();
                ((TextView) itemView.findViewById(R.id.imageViewPagerItem_info_1)).setText(info1);
                String info2 = TextUtils.isEmpty(urlList.get(position).imageInfo2) ? "" : urlList.get(position).imageInfo2.trim();
                ((TextView) itemView.findViewById(R.id.imageViewPagerItem_info_2)).setText(info2);

                if (TextUtils.isEmpty(info1) && TextUtils.isEmpty(info2)){//没有图片信息 就都隐藏
                    imageInfoLayout.setVisibility(View.GONE);
                    showInfoButton.setVisibility(View.GONE);
                }

                ViewUtils.removeSelfFromParent(itemView);
                container.addView(itemView);
                return itemView;
            }
        });
        evp.setCurrentItem(index);
    }


    /**
     * 关闭分享窗口
     */
    public static void closeImageWindow() {
        if (imageWindow != null && imageWindow.isShowing()) {
            imageWindow.dismiss();
        }
    }


}
