package com.happy.interesting.music.fragments.LocalMusicFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happy.interesting.music.activities.HomeActivity;
import com.happy.interesting.music.MusicDNAApplication;
import com.happy.interesting.music.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    public AlbumRecyclerAdapter abAdapter;

    public RecyclerView rv;

    public onAlbumClickListener mCallback;
    GridLayoutManager glManager;

    View bottomMarginLayout;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public interface onAlbumClickListener {
        public void onAlbumClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (onAlbumClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomMarginLayout = view.findViewById(R.id.bottom_margin_layout);
        if (HomeActivity.isReloaded)
            bottomMarginLayout.setVisibility(View.GONE);
        else
            bottomMarginLayout.setVisibility(View.VISIBLE);

        rv = (RecyclerView) view.findViewById(R.id.albums_recycler);
        abAdapter = new AlbumRecyclerAdapter(HomeActivity.finalAlbums, getContext());
        glManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(glManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(abAdapter);

        rv.addOnItemTouchListener(new ClickItemTouchListener(rv) {
            @Override
            boolean onClick(RecyclerView parent, View view, int position, long id) {
                HomeActivity.tempAlbum = HomeActivity.finalAlbums.get(position);
                mCallback.onAlbumClick();
                return true;
            }

            @Override
            boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        glManager.scrollToPositionWithOffset(0, 0);
    }

    public void updateAdapter() {
        if (abAdapter != null)
            abAdapter.notifyDataSetChanged();
    }
}
