package newui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.newui.interesting.music.R;

import java.util.ArrayList;
import java.util.List;

import newui.base.BaseActivity;
import newui.fragment.BrowFragment;


/**
 * Created by sjning
 * created on: 2019/1/10 下午4:26
 * description:
 */
public class NewMainActivity extends BaseActivity implements View.OnClickListener {

    private static final String FRAGMENT_TAG_BROW = "brow";
    private static final String FRAGMENT_TAG_LOCAL = "local";
    private static final String FRAGMENT_TAG_MY_MUSIC = "my_music";
    private static final String FRAGMENT_TAG_ME = "me";

    public static final int CASHIER_INDEX = 0;
    public static final int TABLE_INDEX = 1;
    public static final int ORDER_INDEX = 2;
    public static final int MINE_INDEX = 3;

    private List<View> tabList = new ArrayList<>();
    private int curIndex = 0;
    private Fragment mCurShowFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_activity);
        initView();
        initDefaultTab(true);
    }

    private void initDefaultTab(boolean showFragment) {
        tabList.get(curIndex).setSelected(true);
        if (showFragment) {
            showFragment(String.valueOf(tabList.get(curIndex).getTag()), "", null, true);
        }
    }

    public void initView() {
        inflaterItem(R.id.view_bottom_1, FRAGMENT_TAG_BROW);
        inflaterItem(R.id.view_bottom_2, FRAGMENT_TAG_LOCAL);
        inflaterItem(R.id.view_bottom_3, FRAGMENT_TAG_MY_MUSIC);
        inflaterItem(R.id.view_bottom_4, FRAGMENT_TAG_ME);
    }

    private void inflaterItem(int id, String tag) {
        View tab = findViewById(id);
        tab.setOnClickListener(this);
        tab.setTag(tag);
        tabList.add(tab);
    }

    @Override
    public void onClick(View view) {
        if (view.isSelected()) { //已选中的，点击无效
            return;
        }
        view.setSelected(true);
        String showTag = (String) view.getTag();
        showFragment(showTag, (String) tabList.get(curIndex).getTag(), null, false);
        if (tabList.get(curIndex) != null) {
            tabList.get(curIndex).setSelected(false);
        }
        for (int i = 0; i < tabList.size(); i++) {
            if (view == tabList.get(i)) {
                this.curIndex = i;
                break;
            }
        }
    }

    private void showFragment(String showTag, String oldTag, Bundle fragmentBundle, boolean isNew) {
        if (TextUtils.isEmpty(showTag) || TextUtils.equals(showTag, oldTag)) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        mCurShowFragment = fragmentManager.findFragmentByTag(showTag);
        Fragment oldFragment = TextUtils.isEmpty(oldTag) ? null : fragmentManager.findFragmentByTag(oldTag);
        if (isNew || mCurShowFragment == null) {
            if (mCurShowFragment != null) {
                mCurShowFragment = createFragment(showTag, fragmentBundle);
                fragmentManager.beginTransaction().replace(R.id.view_container, mCurShowFragment, showTag).commitAllowingStateLoss();
            } else {
                mCurShowFragment = createFragment(showTag, fragmentBundle);
                fragmentManager.beginTransaction().add(R.id.view_container, mCurShowFragment, showTag).commitAllowingStateLoss();
            }
        }
        fragmentManager.beginTransaction().show(mCurShowFragment).commitAllowingStateLoss();
        if (oldFragment != null) {
            fragmentManager.beginTransaction().hide(oldFragment).commitAllowingStateLoss();
        }
    }

    private Fragment createFragment(String fragmentTag, Bundle fragmentBundle) {
        Bundle bundle = fragmentBundle;
        if (bundle == null) {
            bundle = new Bundle();
        }
        switch (fragmentTag) {
            case FRAGMENT_TAG_BROW:
                return new BrowFragment();
//            case FRAGMENT_TAG_LOCAL:
//
//                return orderTabFragment;
//            case FRAGMENT_TAG_MY_MUSIC:
//                return mMainController.createMineTab(bundle);
//            case FRAGMENT_TAG_ME:
//                return new TableListFragment();
            default:
                return new BrowFragment();
        }
    }
}
