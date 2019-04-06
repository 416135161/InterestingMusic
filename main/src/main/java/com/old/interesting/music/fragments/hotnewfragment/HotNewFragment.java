package com.old.interesting.music.fragments.hotnewfragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.old.interesting.music.Config;
import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.utilities.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjning
 * created on: 2019/4/6 上午9:23
 * description:
 */
public class HotNewFragment extends Fragment {
    public static int TYPE ;
    public static int TYPE_HOT = 0;
    public static int TYPE_NEW = 1;
    HomeActivity homeActivity;
    View bottomMarginLayout;
    ImageView backBtn;
    TabLayout tabLayout;
    ViewPager viewPager;
    MyPageAdapter adapter;

    public HotNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hot_new, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(Color.parseColor("#111111"));
        tabLayout.setSelectedTabIndicatorColor(HomeActivity.themeColor);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        backBtn = (ImageView) view.findViewById(R.id.all_playlist_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        bottomMarginLayout = view.findViewById(R.id.bottom_margin_layout);
        if (HomeActivity.isReloaded)
            bottomMarginLayout.getLayoutParams().height = 0;
        else
            bottomMarginLayout.getLayoutParams().height = CommonUtils.dpTopx(65, getContext());
        TextView tvTitle = view.findViewById(R.id.tv_title);
        if(TYPE == TYPE_HOT){
            tvTitle.setText(R.string.text_hot_song);
        }else if(TYPE == TYPE_NEW){
            tvTitle.setText(R.string.text_new_song);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewPager != null){
            viewPager.setCurrentItem(0);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            homeActivity = (HomeActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
        adapter = new MyPageAdapter(getChildFragmentManager());
        SongListFragment usList = new SongListFragment();
        Bundle args = new Bundle();
        args.putString("from", Config.FROM_US);
        usList.setArguments(args);
        adapter.addFragment(usList, "US and Europe");

        SongListFragment japanList = new SongListFragment();
        Bundle args2 = new Bundle();
        args2.putString("from", Config.FROM_JAPAN);
        japanList.setArguments(args2);
        adapter.addFragment(japanList, "Japan");
    }


    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}

