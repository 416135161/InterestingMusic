package com.old.interesting.music.fragments.AllPlaylistsFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.old.interesting.music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import newui.data.playTeamResponse.PlayTeamResult;

;

/**
 * Created by Harjot on 29-May-16.
 */
public class ViewAllPlaylistsRecyclerAdapter extends RecyclerView.Adapter<ViewAllPlaylistsRecyclerAdapter.MyViewHolder> {

    List<PlayTeamResult> playlists;
    Context ctx;

    public ViewAllPlaylistsRecyclerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img ;
        TextView playListName;

        public MyViewHolder(View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.image);
            playListName =  itemView.findViewById(R.id.name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_all_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlayTeamResult p = playlists.get(position);
        holder.playListName.setText(p.getSpecialname());
        Picasso.with(ctx).load(p.getImgurl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if(playlists == null){
            return 0;
        }else {
            return playlists.size();
        }
    }

    public void setPlaylists(List<PlayTeamResult> playlists) {
        this.playlists = playlists;
    }

    public PlayTeamResult getItem(int position){
        if(position < getItemCount()){
            return playlists.get(position);
        }else {
            return null;
        }
    }
}
