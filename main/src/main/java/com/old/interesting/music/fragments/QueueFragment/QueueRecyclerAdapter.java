package com.old.interesting.music.fragments.QueueFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.old.interesting.music.customviews.CustomPlayingIndicator;
import com.old.interesting.music.intercepter.GetPicUtil;
import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperAdapter;
import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperViewHolder;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.models.LocalTrack;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.UnifiedTrack;
import com.old.interesting.music.fragments.PlayerFragment.PlayerFragment;
import com.old.interesting.music.R;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harjot on 16-May-16.
 */


public class QueueRecyclerAdapter extends RecyclerView.Adapter<QueueRecyclerAdapter.MyViewHolder>
        implements ItemTouchHelperAdapter {

    private List<UnifiedTrack> queue;
    Context ctx;
    ImageLoader imgLoader;

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    private final OnDragStartListener mDragStartListener;

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        ImageView art;
        TextView title, artist;
        CustomPlayingIndicator indicator;
        ImageView holderImg;

        public MyViewHolder(View view) {
            super(view);
            art = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.url);
            indicator = (CustomPlayingIndicator) view.findViewById(R.id.currently_playing_indicator);
            holderImg = (ImageView) view.findViewById(R.id.holderImage);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#333333"));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.BLACK);
        }
    }

    public QueueRecyclerAdapter(List<UnifiedTrack> queue, Context ctx, OnDragStartListener listener) {
        this.queue = queue;
        this.ctx = ctx;
        mDragStartListener = listener;
        imgLoader = new ImageLoader(ctx);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_3, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (HomeActivity.queueCurrentIndex == position && !HomeActivity.isReloaded) {
            holder.title.setTextColor(HomeActivity.themeColor);
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.indicator.setVisibility(View.VISIBLE);
            if (PlayerFragment.mMediaPlayer != null) {
                if (PlayerFragment.mMediaPlayer.isPlaying()) {
                    holder.indicator.play();
                } else {
                    holder.indicator.pause();
                }
            }
        } else {
            holder.title.setTextColor(Color.WHITE);
            holder.title.setTypeface(null, Typeface.NORMAL);
            holder.indicator.setVisibility(View.INVISIBLE);
        }

        holder.holderImg.setColorFilter(HomeActivity.themeColor);
        holder.indicator.setDrawColor(HomeActivity.themeColor);

        UnifiedTrack ut = queue.get(position);
        if (ut.getType()) {
            LocalTrack lt = ut.getLocalTrack();
            imgLoader.DisplayImage(lt.getPath(), holder.art);
            holder.title.setText(lt.getTitle());
            holder.artist.setText(lt.getArtist());
        } else {
            final Track t = ut.getStreamTrack();
            if (TextUtils.isEmpty(t.getArtworkURL())) {
                holder.art.setImageResource(R.drawable.ic_default);
//                new GetPicUtil(t.getFileHash(), new GetPicUtil.GetPicCallBack() {
//                    @Override
//                    public void onPicOk(String url) {
//                        t.setmArtworkURL(url);
//                        notifyDataSetChanged();
//                    }
//                    @Override
//                    public void onPicFail() {
//
//                    }
//                }).getSongFromCloud();
            } else {
                Picasso.with(ctx)
                        .load(t.getArtworkURL())
                        .resize(100, 100)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .into(holder.art);
            }

            holder.title.setText(t.getTitle());
            holder.artist.setText("");
        }

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
        return queue.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        UnifiedTrack prev = queue.remove(fromPosition);
        queue.add(toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
        if (fromPosition == HomeActivity.queueCurrentIndex) {
            HomeActivity.queueCurrentIndex = toPosition;
        } else if (fromPosition > HomeActivity.queueCurrentIndex && toPosition == HomeActivity.queueCurrentIndex) {
            HomeActivity.queueCurrentIndex++;
        } else if (fromPosition < HomeActivity.queueCurrentIndex && toPosition == HomeActivity.queueCurrentIndex) {
            HomeActivity.queueCurrentIndex--;
        }
        new HomeActivity.SaveQueue().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onItemDismiss(int position) {
        UnifiedTrack ut = queue.get(position);
        if (HomeActivity.originalQueue != null)
            HomeActivity.originalQueue.removeItem(ut);
        queue.remove(position);
        if (position < HomeActivity.queueCurrentIndex) {
            HomeActivity.queueCurrentIndex--;
        }
        notifyItemRemoved(position);
        new HomeActivity.SaveQueue().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
