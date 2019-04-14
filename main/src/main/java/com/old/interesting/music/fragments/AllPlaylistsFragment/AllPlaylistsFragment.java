package com.old.interesting.music.fragments.AllPlaylistsFragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.old.interesting.music.Config;
import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.clickitemtouchlistener.ClickItemTouchListener;
import com.old.interesting.music.fragments.LocalMusicFragments.AlbumFragment;
import com.old.interesting.music.fragments.LocalMusicFragments.ArtistFragment;
import com.old.interesting.music.fragments.LocalMusicFragments.LocalMusicFragment;
import com.old.interesting.music.fragments.LocalMusicFragments.RecentlyAddedSongsFragment;
import com.old.interesting.music.fragments.ViewPlaylistFragment.ViewPlaylistFragment;
import com.old.interesting.music.utilities.CommonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import newui.base.BaseFragment;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.util.CloudDataUtil;

;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPlaylistsFragment extends Fragment {


    HomeActivity homeActivity;

    View bottomMarginLayout;

    ImageView backBtn;

    TabLayout tabLayout;
    ViewPager viewPager;
    MyPageAdapter adapter;

    public AllPlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewPager != null) {
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

        AlbumListFragment japanList = new AlbumListFragment();
        Bundle args2 = new Bundle();
        args2.putString("from", Config.FROM_JAPAN);
        japanList.setArguments(args2);
        adapter.addFragment(japanList, getString(R.string.text_title_japan));

        AlbumListFragment usList = new AlbumListFragment();
        Bundle args = new Bundle();
        args.putString("from", Config.FROM_US);
        usList.setArguments(args);
        adapter.addFragment(usList, getString(R.string.text_title_us));

        AlbumListFragment popList = new AlbumListFragment();
        Bundle argsPop = new Bundle();
        argsPop.putString("from", Config.EUROP_POP + "");
        popList.setArguments(argsPop);
        adapter.addFragment(popList, getString(R.string.text_title_pop));

        AlbumListFragment countryList = new AlbumListFragment();
        Bundle argsCountry = new Bundle();
        argsCountry.putString("from", Config.EUROP_COUNTRY + "");
        countryList.setArguments(argsCountry);
        adapter.addFragment(countryList, getString(R.string.text_title_country));
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
