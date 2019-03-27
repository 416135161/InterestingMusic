package com.old.interesting.music.fragments.ViewPlaylistFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.intercepter.GetPicUtil;
import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperAdapter;
import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import newui.data.playListResponse.PlayListResult;

/**
 * Created by Harjot on 20-May-16.
 */
public class PlaylistTrackAdapter extends RecyclerView.Adapter<PlaylistTrackAdapter.MyViewHolder>
        implements ItemTouchHelperAdapter {

    private List<PlayListResult> songList;
    private Context ctx;
    HomeActivity homeActivity;

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    public interface OnMoveRemoveListener {
        void updateViewPlaylistFragment();
    }

    public interface onPlaylistEmptyListener {
        void onPlaylistEmpty();
    }

    private final OnDragStartListener mDragStartListener;
    public onPlaylistEmptyListener mCallback;
    public OnMoveRemoveListener mCallback2;

    public PlaylistTrackAdapter(ViewPlaylistFragment fragContext, Context ctx) {
        mDragStartListener = fragContext;
        mCallback2 = fragContext;

        mCallback = (onPlaylistEmptyListener) ctx;
        this.ctx = ctx;
        homeActivity = (HomeActivity) ctx;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
    }

    @Override
    public void onItemDismiss(int position) {
        songList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        ImageView art;
        TextView title, artist;
        View indicator;
        ImageView holderImg;

        public MyViewHolder(View view) {
            super(view);
            art = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.url);
            indicator = view.findViewById(R.id.currently_playing_indicator);
            holderImg = (ImageView) view.findViewById(R.id.holderImage);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#444444"));
            if (Build.VERSION.SDK_INT >= 21) {
                itemView.setTranslationZ(12);
            }
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.parseColor("#111111"));
            if (Build.VERSION.SDK_INT >= 21) {
                itemView.setTranslationZ(0);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaylistTrackAdapter.MyViewHolder holder, int position) {
        final PlayListResult item = songList.get(position);
        if (TextUtils.isEmpty(item.getImgUrl())) {
            new GetPicUtil(item.getHash(), new GetPicUtil.GetPicCallBack() {
                @Override
                public void onPicOk(String url) {
                    item.setImgUrl(url);
                    notifyDataSetChanged();
                }

                @Override
                public void onPicFail() {

                }
            }).getSongFromCloud();

        }
        Picasso.with(ctx)
                .load(item.getImgUrl())
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .into(holder.art);
        holder.title.setText(item.getFilename());
        holder.artist.setText("");


        holder.holderImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onDragStarted(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (songList == null) {
            return 0;
        } else {
            return songList.size();
        }
    }

    public PlayListResult getItem(int position) {
        if (songList == null || songList.size() == 0 || position >= songList.size()) {
            return null;
        } else {
            return songList.get(position);
        }
    }

    public void setSongList(List<PlayListResult> songList) {
        this.songList = songList;
    }
}
