package com.old.interesting.music.fragments.StreamFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.old.interesting.music.intercepter.GetPicUtil;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harjot on 30-Apr-16.
 */
public class StreamTrackListAdapter extends RecyclerView.Adapter<StreamTrackListAdapter.MyViewHolder> {

    private List<Track> tracks;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView art;
        TextView title, artist;

        public MyViewHolder(View view) {
            super(view);
            art = (ImageView) view.findViewById(R.id.img_2);
            title = (TextView) view.findViewById(R.id.title_2);
            artist = (TextView) view.findViewById(R.id.url_2);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Track track = tracks.get(position);
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getSingerName());
        if (!TextUtils.isEmpty(track.getArtworkURL())) {
            try {
                Picasso.with(context)
                        .load(track.getArtworkURL())
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .into(holder.art);
                Log.d("URL", track.getArtworkURL());
            } catch (Exception e) {
                Log.e("AdapterError", e.getMessage());
            }
        } else {
            holder.art.setImageResource(R.drawable.ic_default);
            new GetPicUtil(track.getFileHash(), new GetPicUtil.GetPicCallBack() {
                @Override
                public void onPicOk(String url) {
                    track.setmArtworkURL(url);
                    notifyDataSetChanged();
                }

                @Override
                public void onPicFail() {

                }
            }).getSongFromCloud();
        }
    }

    @Override
    public int getItemCount() {
        if (tracks != null) {
            return tracks.size();
        } else {
            return 0;
        }

    }


    public StreamTrackListAdapter(Context ctx, List<Track> tracks) {
        super();
        context = ctx;
        this.tracks = tracks;
    }

    public Track getData(int position) {
        if (position >= 0 && tracks != null && tracks.size() > position) {
            return tracks.get(position);
        } else return null;
    }
}
