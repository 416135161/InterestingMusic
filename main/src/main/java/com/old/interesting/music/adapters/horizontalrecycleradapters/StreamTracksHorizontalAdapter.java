package com.old.interesting.music.adapters.horizontalrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.old.interesting.music.intercepter.GetPicUtil;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.R;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harjot on 15-May-16.
 */
public class StreamTracksHorizontalAdapter extends RecyclerView.Adapter<StreamTracksHorizontalAdapter.MyViewHolder> {

    List<Track> streamList;
    Context ctx;
    ImageLoader imgLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView art;
        TextView title, artist;
        RelativeLayout bottomHolder;

        public MyViewHolder(View view) {
            super(view);
            art = (ImageView) view.findViewById(R.id.backImage);
            title = (TextView) view.findViewById(R.id.card_title);
            artist = (TextView) view.findViewById(R.id.card_artist);
            bottomHolder = (RelativeLayout) view.findViewById(R.id.bottomHolder);
        }
    }

    public StreamTracksHorizontalAdapter(List<Track> streamList, Context ctx) {
        this.streamList = streamList;
        this.ctx = ctx;
        imgLoader = new ImageLoader(ctx);
    }

    @Override
    public StreamTracksHorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_layout2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StreamTracksHorizontalAdapter.MyViewHolder holder, final int position) {

        final Track track = streamList.get(position);
        try {
            if (!TextUtils.isEmpty(track.getArtworkURL())) {
                Log.d("ARTWORK_URL", track.getArtworkURL());
                Picasso.with(ctx)
                        .load(track.getArtworkURL())
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .into(holder.art);
            } else {
                holder.art.setImageResource(R.drawable.ic_default);
                new GetPicUtil(track.getFileHash(), new GetPicUtil.GetPicCallBack() {
                    @Override
                    public void onPicOk(String url) {
                        track.setmArtworkURL(url);
                        notifyItemChanged(position);
                    }

                    @Override
                    public void onPicFail() {

                    }
                }).getSongFromCloud();
            }
        } catch (Exception e) {
            Log.e("AdapterError", e.getMessage());
        }
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getSingerName());
    }

    @Override
    public int getItemCount() {
        return streamList.size();
    }
}
