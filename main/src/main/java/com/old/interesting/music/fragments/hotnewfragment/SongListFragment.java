package com.old.interesting.music.fragments.hotnewfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.old.interesting.music.Config;
import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.clickitemtouchlistener.ClickItemTouchListener;
import com.old.interesting.music.custombottomsheets.CustomGeneralBottomSheetDialog;
import com.old.interesting.music.fragments.StreamFragment.StreamMusicFragment;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.UnifiedTrack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import newui.base.BaseFragment;
import newui.data.action.ActionBillSongs;
import newui.data.action.ActionNewSongs;
import newui.data.util.CloudDataUtil;

/**
 * Created by sjning
 * created on: 2019/4/6 上午9:28
 * description:
 */
public class SongListFragment extends BaseFragment {

    private SongListAdapter adapter;
    private List<Track> trackList = new ArrayList<>();
    StreamMusicFragment.OnTrackSelectedListener mCallback;
    Context ctx;

    RecyclerView lv;
    LinearLayout noPlaylistContent;
    ProgressBar mProgressBar;
    ImageView mRefreshIv;
    String from;

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (StreamMusicFragment.OnTrackSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        from = getArguments().getString("from", Config.FROM_US);
        lv = view.findViewById(R.id.trackList);
        adapter = new SongListAdapter(getContext(), trackList);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv.setLayoutManager(mLayoutManager2);
        lv.setItemAnimator(new DefaultItemAnimator());
        lv.setAdapter(adapter);

        lv.addOnItemTouchListener(new ClickItemTouchListener(lv) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                Track track = adapter.getData(position);
                if (HomeActivity.queue.getQueue().size() == 0) {
                    HomeActivity.queueCurrentIndex = 0;
                    HomeActivity.queue.getQueue().add(new UnifiedTrack(false, null, track));
                } else if (HomeActivity.queueCurrentIndex == HomeActivity.queue.getQueue().size() - 1) {
                    HomeActivity.queueCurrentIndex++;
                    HomeActivity.queue.getQueue().add(new UnifiedTrack(false, null, track));
                } else if (HomeActivity.isReloaded) {
                    HomeActivity.isReloaded = false;
                    HomeActivity.queueCurrentIndex = HomeActivity.queue.getQueue().size();
                    HomeActivity.queue.getQueue().add(new UnifiedTrack(false, null, track));
                } else {
                    HomeActivity.queue.getQueue().add(++HomeActivity.queueCurrentIndex, new UnifiedTrack(false, null, track));
                }
                HomeActivity.selectedTrack = track;
                HomeActivity.streamSelected = true;
                HomeActivity.localSelected = false;
                HomeActivity.queueCall = false;
                HomeActivity.isReloaded = false;
                mCallback.onTrackSelected(position);
                return true;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, final int position, long id) {
                CustomGeneralBottomSheetDialog generalBottomSheetDialog = new CustomGeneralBottomSheetDialog();
                generalBottomSheetDialog.setPosition(position);
                generalBottomSheetDialog.setTrack(new UnifiedTrack(false, null, adapter.getData(position)));
                generalBottomSheetDialog.setFragment("Stream");
                generalBottomSheetDialog.show(getActivity().getSupportFragmentManager(), "general_bottom_sheet_dialog");
                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        noPlaylistContent = (LinearLayout) view.findViewById(R.id.noPlaylistContent);
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
        mProgressBar.setVisibility(View.VISIBLE);
        noPlaylistContent.setVisibility(View.GONE);
        if (HotNewFragment.TYPE == HotNewFragment.TYPE_NEW) {
            CloudDataUtil.getNewSongs(ActionNewSongs.TYPE_LIST, from);
        } else if (HotNewFragment.TYPE == HotNewFragment.TYPE_HOT) {
            CloudDataUtil.getBillSongs(ActionBillSongs.TYPE_LIST, from);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPosting(ActionNewSongs event) {
        if (event.type == ActionNewSongs.TYPE_LIST && event.from == from) {
            if (event != null && event.trackList != null && event.trackList.size() > 0) {
                adapter.setPlaylists(event.trackList);
                adapter.notifyDataSetChanged();
                noPlaylistContent.setVisibility(View.GONE);
            } else {
                noPlaylistContent.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPosting(ActionBillSongs event) {
        if (event.type == ActionBillSongs.TYPE_LIST && event.from == from) {
            if (event != null && event.trackList != null && event.trackList.size() > 0) {
                adapter.setPlaylists(event.trackList);
                adapter.notifyDataSetChanged();
                noPlaylistContent.setVisibility(View.GONE);
            } else {
                noPlaylistContent.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}