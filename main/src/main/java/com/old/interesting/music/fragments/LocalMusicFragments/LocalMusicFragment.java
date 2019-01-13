package com.old.interesting.music.fragments.LocalMusicFragments;


//import android.app.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.old.interesting.music.clickitemtouchlistener.ClickItemTouchListener;
import com.old.interesting.music.custombottomsheets.CustomLocalBottomSheetDialog;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.models.LocalTrack;
import com.old.interesting.music.models.UnifiedTrack;
import com.old.interesting.music.MusicDNAApplication;
import com.old.interesting.music.R;
import com.old.interesting.music.utilities.CommonUtils;


import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocalMusicFragment extends Fragment {

    LocalTrackRecyclerAdapter adapter;
    OnLocalTrackSelectedListener mCallback;
    Context ctx;

    RecyclerView lv;
    LinearLayoutManager mLayoutManager2;

    FloatingActionButton shuffleFab;

    HomeActivity activity;

    View bottomMarginLayout;

    public LocalMusicFragment() {
        // Required empty public constructor
    }

    public interface OnLocalTrackSelectedListener {
        void onLocalTrackSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnLocalTrackSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
        ctx = context;
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_music, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomMarginLayout = view.findViewById(R.id.bottom_margin_layout);
        if (HomeActivity.isReloaded)
            bottomMarginLayout.getLayoutParams().height = 0;
        else
            bottomMarginLayout.getLayoutParams().height = CommonUtils.dpTopx(65, getContext());

        shuffleFab = (FloatingActionButton) view.findViewById(R.id.play_all_fab_local);

        if (HomeActivity.localTrackList.size() == 0) {
            shuffleFab.setVisibility(View.INVISIBLE);
        }

        shuffleFab.setBackgroundTintList(ColorStateList.valueOf(HomeActivity.themeColor));
        shuffleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.queue.getQueue().clear();
                for (int i = 0; i < HomeActivity.localTrackList.size(); i++) {
                    UnifiedTrack ut = new UnifiedTrack(true, HomeActivity.localTrackList.get(i), null);
                    HomeActivity.queue.getQueue().add(ut);
                }
                if (HomeActivity.queue.getQueue().size() > 0) {
                    Random r = new Random();
                    int tmp = r.nextInt(HomeActivity.queue.getQueue().size());
                    HomeActivity.queueCurrentIndex = tmp;
                    LocalTrack track = HomeActivity.localTrackList.get(tmp);
                    HomeActivity.localSelectedTrack = track;
                    HomeActivity.streamSelected = false;
                    HomeActivity.localSelected = true;
                    HomeActivity.queueCall = false;
                    HomeActivity.isReloaded = false;
                    mCallback.onLocalTrackSelected(-1);
                }
            }
        });

        lv = (RecyclerView) view.findViewById(R.id.localMusicList);
        adapter = new LocalTrackRecyclerAdapter(HomeActivity.finalLocalSearchResultList, getContext());
        mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv.setLayoutManager(mLayoutManager2);
        lv.setItemAnimator(new DefaultItemAnimator());
        lv.setAdapter(adapter);

        lv.addOnItemTouchListener(new ClickItemTouchListener(lv) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {

                if (position >= 0) {
                    HomeActivity.queue.getQueue().clear();
                    for (int i = 0; i < HomeActivity.localTrackList.size(); i++) {
                        UnifiedTrack ut = new UnifiedTrack(true, HomeActivity.localTrackList.get(i), null);
                        HomeActivity.queue.getQueue().add(ut);
                    }
                    HomeActivity.queueCurrentIndex = getPosition(HomeActivity.finalLocalSearchResultList.get(position));
                    LocalTrack track = HomeActivity.finalLocalSearchResultList.get(position);
                    HomeActivity.localSelectedTrack = track;
                    HomeActivity.streamSelected = false;
                    HomeActivity.localSelected = true;
                    HomeActivity.queueCall = false;
                    HomeActivity.isReloaded = false;
                    mCallback.onLocalTrackSelected(-1);
                }

                return true;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, final int position, long id) {
                if (position >= 0) {
                    CustomLocalBottomSheetDialog localBottomSheetDialog = new CustomLocalBottomSheetDialog();
                    localBottomSheetDialog.setPosition(position);
                    localBottomSheetDialog.setLocalTrack(activity.finalLocalSearchResultList.get(position));
                    localBottomSheetDialog.show(activity.getSupportFragmentManager(), "local_song_bottom_sheet");
                }
                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        Button mEndButton = new Button(getContext());
        mEndButton.setBackgroundColor(HomeActivity.themeColor);
        mEndButton.setTextColor(Color.WHITE);

    }

    @Override
    public void onResume() {
        super.onResume();
        mLayoutManager2.scrollToPositionWithOffset(0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shuffleFab.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).setInterpolator(new OvershootInterpolator());
            }
        }, 500);
    }

    public int getPosition(LocalTrack lt) {
        for (int i = 0; i < HomeActivity.localTrackList.size(); i++) {
            if (HomeActivity.localTrackList.get(i).getTitle().equals(lt.getTitle())) {
                return i;
            }
        }
        return -1;
    }



    public void hideShuffleFab() {
        if (shuffleFab != null)
            shuffleFab.setVisibility(View.INVISIBLE);
    }

    public void showShuffleFab() {
        if (shuffleFab != null)
            shuffleFab.setVisibility(View.VISIBLE);
    }

    public void updateAdapter() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

}
