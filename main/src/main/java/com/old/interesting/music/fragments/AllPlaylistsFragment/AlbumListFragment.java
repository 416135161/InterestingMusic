package com.old.interesting.music.fragments.AllPlaylistsFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import newui.base.BaseFragment;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.util.CloudDataUtil;

/**
 * Created by sjning
 * created on: 2019/4/5 下午11:22
 * description:
 */
public class AlbumListFragment extends BaseFragment {
    public RecyclerView allPlaylistRecycler;
    public ViewAllPlaylistsRecyclerAdapter vpAdapter;
    GridLayoutManager mLayoutManager2;
    HomeActivity homeActivity;
    LinearLayout noPlaylistContent;
    ProgressBar mProgressBar;
    ImageView mRefreshIv;
    String from;


    public AlbumListFragment() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        from = getArguments().getString("from", Config.FROM_US);
        noPlaylistContent = (LinearLayout) view.findViewById(R.id.noPlaylistContent);
        allPlaylistRecycler = (RecyclerView) view.findViewById(R.id.all_playlists_recycler);

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
        CloudDataUtil.getPlayTeamList(Config.ALL_PLAY_TEAM_PAGE, ActionBrowPlayTeam.TYPE_TEAM_LIST, from);
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
        ArrayList<PlayTeamBean> teamList = event.playTeamBean;
        if (teamList != null && !teamList.isEmpty()) {
            if (TextUtils.equals(this.from, event.from)) {
                allPlaylistRecycler.setVisibility(View.VISIBLE);
                noPlaylistContent.setVisibility(View.INVISIBLE);
                vpAdapter.setPlaylists(teamList);
                vpAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        } else {
            allPlaylistRecycler.setVisibility(View.INVISIBLE);
            noPlaylistContent.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this.getContext(), "please check net state", Toast.LENGTH_SHORT).show();
        }
    }
}
