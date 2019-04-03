package com.old.interesting.music.fragments.FavouritesFragment;

import android.content.Context;
import android.graphics.Color;
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

import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperAdapter;
import com.old.interesting.music.itemtouchhelpers.ItemTouchHelperViewHolder;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.models.LocalTrack;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.UnifiedTrack;
import com.old.interesting.music.R;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harjot on 20-May-16.
 */
public class FavouriteTrackAdapter extends RecyclerView.Adapter<FavouriteTrackAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private List<UnifiedTrack> favouriteList;
    private Context ctx;
    private ImageLoader imgLoader;

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    public interface onEmptyListener {
        void onEmpty();
    }

    public interface onMoveRemoveistener {
        void updateFavFragment();
    }

    private final OnDragStartListener mDragStartListener;
    private onEmptyListener mCallback;
    private onMoveRemoveistener mCallback2;

    public FavouriteTrackAdapter(List<UnifiedTrack> favouriteList, FavouritesFragment favContext, Context ctx) {
        this.mDragStartListener = favContext;
        this.mCallback = favContext;
        this.mCallback2 = favContext;
        this.favouriteList = favouriteList;
        this.ctx = ctx;
        imgLoader = new ImageLoader(ctx);
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
            itemView.setBackgroundColor(Color.DKGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.parseColor("#111111"));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        UnifiedTrack ut = favouriteList.get(position);
        if (ut.getType()) {
            LocalTrack lt = ut.getLocalTrack();
            imgLoader.DisplayImage(lt.getPath(), holder.art);
            holder.title.setText(lt.getTitle());
            holder.artist.setText(lt.getArtist());
        } else {
            Track t = ut.getStreamTrack();
            Picasso.with(ctx)
                    .load(t.getArtworkURL())
                    .resize(100, 100)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .into(holder.art);
            if(!TextUtils.isEmpty(t.getTitle()) && t.getTitle().contains("-")){
                holder.title.setText(t.getTitle().split("-")[1]);
                holder.artist.setText(t.getTitle().split("-")[0]);
            }else {
                holder.title.setText(t.getTitle());
                holder.artist.setText(t.getSingerName());
            }
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
        return favouriteList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        UnifiedTrack prev = favouriteList.remove(fromPosition);
        favouriteList.add(toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);

        mCallback2.updateFavFragment();

        new HomeActivity.SaveFavourites().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onItemDismiss(int position) {
        favouriteList.remove(position);
        notifyItemRemoved(position);

        if (favouriteList.size() == 0) {
            mCallback.onEmpty();
        } else {
            mCallback2.updateFavFragment();
        }

        new HomeActivity.SaveFavourites().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
