package com.huazi.jdemo.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.huazi.jdemo.base.utils.Constant;
import com.huazi.jdemo.custom.loading.LoadingView;
import com.huazi.jdemo.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huazi.jdemo.base.utils.Utils;
import com.huazi.jdemo.bean.EventBo;
import com.huazi.jdemo.ui.fragment.HomeFragment;
import com.huazi.jdemo.ui.fragment.ProjectFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    private static final int INDEX_HOMEPAGE = 0;
    private static final int INDEX_PROJECT = 1;
    private static final int INDEX_SQUARE = 2;
    private static final int INDEX_WE_CHAT = 3;
    private static final int INDEX_ME = 4;

    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    private Fragment mCurrentFragment;

    private Fragment mLastFragment;

    private int mLastIndex = -1;

    private long mExitTime = 0;

    private Context mContext;

    Unbinder mBinder;

    @BindView(R.id.navigation_bottom)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate???????????????????????????
        outState.putInt("index", mLastIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // ??????recreate????????????
        mLastIndex = savedInstanceState.getInt("index");
        switchFragment(mLastIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ??????Fragment?????????????????????????????????
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mContext = getApplicationContext();
        mBinder = ButterKnife.bind(this);
        initBottomNavigation();
        // ???????????????recreate???????????????
        if (savedInstanceState == null) {
            switchFragment(INDEX_HOMEPAGE);
        }
        mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
        mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            switchFragment(INDEX_HOMEPAGE);
                            return true;
                        case R.id.menu_project:
                            switchFragment(INDEX_PROJECT);
                            return true;
                        case R.id.menu_square:
                            switchFragment(INDEX_SQUARE);
                            return true;
                        case R.id.menu_wechat:
                            switchFragment(INDEX_WE_CHAT);
                            return true;
                        case R.id.menu_me:
                            switchFragment(INDEX_ME);
                            return true;
                        default:
                            return false;
                    }
                });
        showBadgeView(4, 6);
    }


    private void switchFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // ??????????????????fragment???????????????????????????fragment????????????tag, ???????????????
        // ???fragment??????tag,??????????????????findFragmentByTag???????????????fragment???????????????????????????
        mCurrentFragment = fragmentManager.findFragmentByTag("fragment" + index);
        mLastFragment = fragmentManager.findFragmentByTag("fragment" + mLastIndex);
        // ??????????????????
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            } else {
                transaction.show(mCurrentFragment);
            }
        }

        // ??????????????????????????????????????????
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            }
        }
        transaction.commit();
        mLastIndex = index;
    }

    private Fragment getFragment(int index) {
        Fragment fragment = mFragmentSparseArray.get(index);
        if (fragment == null) {
            switch (index) {
                case INDEX_HOMEPAGE:
                    fragment = HomeFragment.getInstance();
                    break;
                case INDEX_PROJECT:
                    fragment = ProjectFragment.getInstance();
                    break;
                case INDEX_SQUARE:
                    fragment = HomeFragment.getInstance();
                    break;
                case INDEX_WE_CHAT:
                    fragment = HomeFragment.getInstance();
                    break;
                case INDEX_ME:
                    fragment = HomeFragment.getInstance();
                    break;
                default:
                    break;
            }
            mFragmentSparseArray.put(index, fragment);
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - mExitTime > Constant.EXIT_TIME) {
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            mExitTime = curTime;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ???????????????????????????
     *
     * @param index      ??????bottom index
     * @param showNumber ?????????????????????
     */
    private void showBadgeView(int index, final int showNumber) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
        if (index < menuView.getChildCount()) {
            View view = menuView.getChildAt(index);
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            int iconWidth = icon.getWidth();
            int tabWidth = view.getWidth() / 2;
            int spaceWidth = tabWidth - iconWidth;
            final QBadgeView qBadgeView = new QBadgeView(this);

            qBadgeView.bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
            qBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    qBadgeView.clearAnimation();
                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_MAIN) {
            if (event.type == EventBo.TYPE_REFRESH_COLOR) {
                mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
                mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));
            } else if (event.type == EventBo.TYPE_CHANGE_DAY_NIGHT_MODE) {
                recreate();
            } else if (event.type == EventBo.TYPE_START_ANIMATION) {
                mLoadingView.setVisibility(View.VISIBLE);
                mLoadingView.startTranglesAnimation();
            } else if (event.type == EventBo.TYPE_STOP_ANIMATION) {
                mLoadingView.setVisibility(View.GONE);
            }
        }
    }
}
