package com.old.interesting.music.adapters.horizontalrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.old.interesting.music.R;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.old.interesting.music.models.Playlist;
import com.old.interesting.music.models.UnifiedTrack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import newui.data.playTeamResponse.PlayTeamResult;

/**
 * Created by Harjot on 15-May-16.
 */
public class PlayListsHorizontalAdapter extends RecyclerView.Adapter<PlayListsHorizontalAdapter.MyViewHolder> {

    Context ctx;


    List<PlayTeamResult> playlists;
    Random random = new Random();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img[] = new ImageView[4];
        TextView playlistName, playlistSize;
        RelativeLayout bottomHolder;


        public MyViewHolder(View view) {
            super(view);
            img[0] = itemView.findViewById(R.id.image1);
            img[1] = itemView.findViewById(R.id.image2);
            img[2] = itemView.findViewById(R.id.image3);
            img[3] = itemView.findViewById(R.id.image4);
            playlistName = view.findViewById(R.id.playlist_card_title);
            playlistSize = view.findViewById(R.id.playlist_num_songs);
            bottomHolder = view.findViewById(R.id.playlist_bottomHolder);
        }
    }

    public PlayListsHorizontalAdapter(List<PlayTeamResult> playlists, Context ctx) {
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
        PlayTeamResult pl = playlists.get(position);
        if (playlists.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                int num = random.nextInt(getItemCount());
                Picasso.with(ctx).load(playlists.get(num).getImgurl()).resize(100, 100).into(holder.img[i]);
            }
        }
        holder.playlistName.setText(pl.getSpecialname());
        holder.playlistSize.setText((int) ((Math.random() * 9 + 1) * 10)+ "k count");
    }

    @Override
    public int getItemCount() {
        if (playlists == null) {
            return 0;
        } else {
            return playlists.size();
        }
    }

    public PlayTeamResult getItem(int position) {
        if (playlists == null || position >= playlists.size()) {
            return null;
        } else {
            return playlists.get(position);
        }
    }

    public void setPlaylists(List<PlayTeamResult> playlists) {
        this.playlists = playlists;
    }


}
