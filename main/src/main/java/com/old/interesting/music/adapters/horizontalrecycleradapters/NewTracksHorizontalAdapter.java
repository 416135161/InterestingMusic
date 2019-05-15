package com.old.interesting.music.adapters.horizontalrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.old.interesting.music.R;
import com.old.interesting.music.intercepter.GetPicUtil;
import com.old.interesting.music.models.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/12 下午7:19
 * description:
 */
public class NewTracksHorizontalAdapter extends RecyclerView.Adapter<NewTracksHorizontalAdapter.MyViewHolder> {

    Context ctx;
    List<Track> playlists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img1;
        TextView playlistName, playlistSize;
        RelativeLayout bottomHolder;


        public MyViewHolder(View view) {
            super(view);
            img1 = itemView.findViewById(R.id.image1);
            playlistName = view.findViewById(R.id.playlist_card_title);
            playlistSize = view.findViewById(R.id.playlist_num_songs);
            bottomHolder = view.findViewById(R.id.playlist_bottomHolder);
        }
    }

    public NewTracksHorizontalAdapter(List<Track> playlists, Context ctx) {
        this.playlists = playlists;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_item_card_holder, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Track pl = playlists.get(position);
        holder.playlistName.setText(pl.getTitle());
        holder.playlistSize.setText((int) ((Math.random() * 9 + 1) * 10) + "k count");
        if (TextUtils.isEmpty(pl.getArtworkURL())) {
            new GetPicUtil(pl.getFileHash(), new GetPicUtil.GetPicCallBack() {
                @Override
                public void onPicOk(String url) {
                    pl.setmArtworkURL(url);
                    notifyDataSetChanged();
                }

                @Override
                public void onPicFail() {

                }
            }).getSongFromCloud();

        }
        Picasso.with(ctx).load(playlists.get(position).getArtworkURL())

                .placeholder(R.drawable.ic_default).into(holder.img1);
    }

    @Override
    public int getItemCount() {
        if (playlists == null) {
            return 0;
        } else {
            return playlists.size();
        }
    }

    public Track getItem(int position) {
        if (playlists == null || position >= playlists.size()) {
            return null;
        } else {
            return playlists.get(position);
        }
    }

    public void setPlaylists(List<Track> playlists) {
        if (playlists != null && playlists.size() > 15) {
            this.playlists = playlists.subList(0, 15);
        } else {
            this.playlists = playlists;
        }
    }

    public List<Track> getPlaylists() {
        return playlists;
    }
}