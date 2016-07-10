package com.xxx.handnote.activity;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xxx.handnote.R;
import com.xxx.handnote.base.BaseActivity;
import com.xxx.handnote.fragment.MainFragmentFactory;
import com.xxx.handnote.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private FrameLayout mMainFragmentLayout;
    private LinearLayout mMainBottomLayout;
    private ImageView mButtonHome;
    private ImageView mButtonAddNote;
    private ImageView mButtonMy;
    private FragmentManager fragmentManager;

    @Override
    protected void init() {

    }

    @Override
    protected View getContentView() {
        return UIUtils.inflate(R.layout.activity_main);
    }

    @Override
    protected void initView() {

        mMainFragmentLayout = (FrameLayout) findViewById(R.id.main_fragmentLayout);
        mMainBottomLayout = (LinearLayout) findViewById(R.id.main_bottomLayout);
        setClickEvent(mButtonHome = (ImageView) findViewById(R.id.button_home));
        setClickEvent(mButtonAddNote = (ImageView) findViewById(R.id.button_addNote));
        setClickEvent(mButtonMy = (ImageView) findViewById(R.id.button_my));


        if (fragmentManager == null){
            fragmentManager = getSupportFragmentManager();
        }

        fragmentManager.beginTransaction().replace(R.id.main_fragmentLayout, MainFragmentFactory.createFragment(0)).commit();
    }

    private void setClickEvent(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_home:
                        fragmentManager.beginTransaction().replace(R.id.main_fragmentLayout, MainFragmentFactory.createFragment(0)).commit();
                        break;
                    case R.id.button_addNote:
                        UIUtils.showToastSafe("打开增加页面");
                        break;
                    case R.id.button_my:
                        fragmentManager.beginTransaction().replace(R.id.main_fragmentLayout, MainFragmentFactory.createFragment(1)).commit();
                        break;
                }
            }
        });
    }
}
