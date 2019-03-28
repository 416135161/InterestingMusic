package com.old.interesting.music.fragments.AllPlaylistsFragment;


import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.old.interesting.music.fragments.ViewPlaylistFragment.ViewPlaylistFragment;
import com.old.interesting.music.utilities.CommonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import newui.base.BaseFragment;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.util.CloudDataUtil;

;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPlaylistsFragment extends BaseFragment {

    public RecyclerView allPlaylistRecycler;
    public ViewAllPlaylistsRecyclerAdapter vpAdapter;

    LinearLayout noPlaylistContent;

    allPlaylistCallbackListener mCallback;

    GridLayoutManager mLayoutManager2;

    FloatingActionButton addPlaylistFAB;

    HomeActivity homeActivity;

    View bottomMarginLayout;

    ImageView backBtn;
    TextView allPlaylistFragmentTitle;
    ImageView[] imgView = new ImageView[10];

    ImageView playlistFragIcon;

    ProgressBar mProgressBar;
    ImageView mRefreshIv;

    public interface allPlaylistCallbackListener {
        void onPlaylistSelected(int pos);

        void onPlaylistMenuPLayAll();

        void onPlaylistRename();

        void newPlaylistListener();
    }


    public AllPlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            homeActivity = (HomeActivity) context;
            mCallback = (allPlaylistCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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

        backBtn = (ImageView) view.findViewById(R.id.all_playlist_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        allPlaylistFragmentTitle = (TextView) view.findViewById(R.id.all_playlist_fragment_title);
        if (Config.tf4 != null)
            allPlaylistFragmentTitle.setTypeface(Config.tf4);

        bottomMarginLayout = view.findViewById(R.id.bottom_margin_layout);
        if (HomeActivity.isReloaded)
            bottomMarginLayout.getLayoutParams().height = 0;
        else
            bottomMarginLayout.getLayoutParams().height = CommonUtils.dpTopx(65, getContext());

        noPlaylistContent = (LinearLayout) view.findViewById(R.id.noPlaylistContent);

        allPlaylistRecycler = (RecyclerView) view.findViewById(R.id.all_playlists_recycler);

        addPlaylistFAB = (FloatingActionButton) view.findViewById(R.id.new_playlist_fab);
        addPlaylistFAB.setBackgroundTintList(ColorStateList.valueOf(HomeActivity.themeColor));
        addPlaylistFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.newPlaylistListener();
            }
        });

        if (Config.tf4 != null)
            ((TextView) view.findViewById(R.id.noPlaylistContentText)).setTypeface(Config.tf4);


        vpAdapter = new ViewAllPlaylistsRecyclerAdapter(getContext());
        mLayoutManager2 = new GridLayoutManager(getContext(), 3);
        allPlaylistRecycler.setLayoutManager(mLayoutManager2);
        allPlaylistRecycler.setItemAnimator(new DefaultItemAnimator());
        allPlaylistRecycler.setAdapter(vpAdapter);

        allPlaylistRecycler.addOnItemTouchListener(new ClickItemTouchListener(allPlaylistRecycler) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                ViewPlaylistFragment.setPlayTeamResult(vpAdapter.getItem(position));
                homeActivity.showFragment("playlist");
                return true;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, final int position, long id) {

                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mProgressBar = view.findViewById(R.id.progress);
        mRefreshIv = view.findViewById(R.id.view_refresh);
        noPlaylistContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        getData();

    }

    private void getData() {
        CloudDataUtil.getPlayTeamList(0, Config.ALL_PLAY_TEAM_PAGE, ActionBrowPlayTeam.TYPE_TEAM_LIST);
        mProgressBar.setVisibility(View.VISIBLE);
        noPlaylistContent.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mLayoutManager2.scrollToPositionWithOffset(0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPosting(ActionListPlayTeam event) {
        PlayTeamBean playTeamBean = event.playTeamBean;
        if (playTeamBean != null && playTeamBean.getResult() != null && !playTeamBean.getResult().isEmpty()) {
            allPlaylistRecycler.setVisibility(View.VISIBLE);
            noPlaylistContent.setVisibility(View.INVISIBLE);
            vpAdapter.setPlaylists(playTeamBean.getResult());
            vpAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            allPlaylistRecycler.setVisibility(View.INVISIBLE);
            noPlaylistContent.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this.getContext(), "please check net state", Toast.LENGTH_SHORT).show();
        }
    }

}
