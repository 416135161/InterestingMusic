package com.old.interesting.music.fragments.ViewPlaylistFragment;


import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.old.interesting.music.Config;
import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.clickitemtouchlistener.ClickItemTouchListener;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.old.interesting.music.itemtouchhelpers.SimpleItemTouchHelperCallback;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.UnifiedTrack;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;
import com.old.interesting.music.utilities.CommonUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import newui.base.BaseFragment;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionPlayList;
import newui.data.playListResponse.PlayListResult;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.playTeamResponse.PlayTeamResult;
import newui.data.util.CloudDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPlaylistFragment extends BaseFragment implements
        PlaylistTrackAdapter.OnDragStartListener,
        PlaylistTrackAdapter.OnMoveRemoveListener {

    RecyclerView playlistRecyler;
    PlaylistTrackAdapter plAdapter;
    FloatingActionButton playAll;

    ImageView backdrop, backBtn, addToQueueIcon;
    TextView title, songsText, fragmentTitle;

    ItemTouchHelper mItemTouchHelper;

    LinearLayoutManager mLayoutManager2;

    View bottomMarginLayout;

    playlistCallbackListener mCallback;
    public static PlayTeamBean playTeamResult;
    public static String from;
    ProgressBar mProgressBar;
    ImageView mRefreshIv;

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void updateViewPlaylistFragment() {

    }


    public ViewPlaylistFragment() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(ActionPlayList event) {
        ArrayList<SongDetailBean> playListResults = event.result;
        if (playListResults != null && !playListResults.isEmpty()) {
            plAdapter.setSongList(playListResults);
            plAdapter.notifyDataSetChanged();
            String s = "Songs";
            songsText.setText(playListResults.size() + " " + s);
            playAll.setVisibility(View.VISIBLE);


        } else {
            mRefreshIv.setVisibility(View.VISIBLE);
            playAll.setVisibility(View.GONE);
        }

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (playlistCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomMarginLayout = view.findViewById(R.id.bottom_margin_layout);
        if (HomeActivity.isReloaded)
            bottomMarginLayout.getLayoutParams().height = 0;
        else
            bottomMarginLayout.getLayoutParams().height = CommonUtils.dpTopx(65, getContext());

        fragmentTitle = (TextView) view.findViewById(R.id.playlist_fragment_title);
        if (Config.tf4 != null)
            fragmentTitle.setTypeface(Config.tf4);

        title = (TextView) view.findViewById(R.id.playlist_title);
        title.setText(HomeActivity.tempPlaylist.getPlaylistName());

        songsText = (TextView) view.findViewById(R.id.playlist_tracks_text);
        backBtn = (ImageView) view.findViewById(R.id.view_playlist_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        addToQueueIcon = (ImageView) view.findViewById(R.id.add_playlist_to_queue_icon);
        addToQueueIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plAdapter.getItemCount() == 0) {
                    return;
                }
                int size = plAdapter.getItemCount();
                List<UnifiedTrack> trackList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Track track = new Track();
                    track.setmTitle(plAdapter.getItem(i).getSongName());
                    track.setFileHash(plAdapter.getItem(i).getHash());
                    track.setSingerName("");
                    UnifiedTrack unifiedTrack = new UnifiedTrack(false, null, track);
                    trackList.add(unifiedTrack);
                }
                mCallback.playlistAddToQueue(trackList);
            }
        });

        backdrop = (ImageView) view.findViewById(R.id.playlist_backdrop);

        Picasso.with(getContext())
                .load(playTeamResult.getImgUrl())
                .resize(100, 100)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .into(backdrop);

        playlistRecyler = (RecyclerView) view.findViewById(R.id.view_playlist_recycler);
        plAdapter = new PlaylistTrackAdapter(this, getContext());
        mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        playlistRecyler.setLayoutManager(mLayoutManager2);
        playlistRecyler.setItemAnimator(new DefaultItemAnimator());
        playlistRecyler.setAdapter(plAdapter);

        playlistRecyler.addOnItemTouchListener(new ClickItemTouchListener(playlistRecyler) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                if (position >= plAdapter.getItemCount()) {
                    return false;
                }
                Track track = new Track();
                track.setmTitle(plAdapter.getItem(position).getSongName());
                track.setFileHash(plAdapter.getItem(position).getHash());
                track.setSingerName("");
                UnifiedTrack unifiedTrack = new UnifiedTrack(false, null, track);
                mCallback.onPlaylistItemClicked(unifiedTrack);
                return true;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        playAll = (FloatingActionButton) view.findViewById(R.id.play_all_fab);

        playAll.setBackgroundTintList(ColorStateList.valueOf(HomeActivity.themeColor));
        playAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = plAdapter.getItemCount();
                HomeActivity.queue.getQueue().clear();
                for (int i = 0; i < size; i++) {
                    Track track = new Track();
                    track.setmTitle(plAdapter.getItem(i).getSongName());
                    track.setFileHash(plAdapter.getItem(i).getHash());
                    track.setSingerName("");
                    UnifiedTrack unifiedTrack = new UnifiedTrack(false, null, track);
                    HomeActivity.queue.addToQueue(unifiedTrack);
                }
                HomeActivity.queueCurrentIndex = 0;
                mCallback.onPlaylistPlayAll();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(plAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(playlistRecyler);
        mProgressBar = view.findViewById(R.id.progress);
        mRefreshIv = view.findViewById(R.id.view_refresh);
        mRefreshIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        getData();
    }

    private void getData() {
        CloudDataUtil.getPlayList(playTeamResult.getId() + "", from);
        mRefreshIv.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLayoutManager2.scrollToPositionWithOffset(0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playAll.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).setInterpolator(new OvershootInterpolator());
            }
        }, 500);
    }

    public static void setPlayTeamResult(PlayTeamBean data) {
        playTeamResult = data;
    }

    public static void setFrom(String from) {
        ViewPlaylistFragment.from = from;
    }

    public interface playlistCallbackListener {
        void onPlaylistPlayAll();

        void onPlaylistItemClicked(UnifiedTrack ut);

        void playlistRename();

        void playlistAddToQueue(List<UnifiedTrack> tracks);
    }

}
