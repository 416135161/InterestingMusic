package newui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newui.interesting.music.R;
import com.old.interesting.music.models.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

import newui.data.util.RandomUtil;

/**
 * Created by sjning
 * created on: 2019/1/12 下午4:07
 * description:
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Track> dataList;
    private LayoutInflater layoutInflater;

    public SearchListAdapter(Context context, List<Track> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.search_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setDataList(List<Track> list){
        if(list != null && !list.isEmpty()){
            this.dataList.clear();
            this.dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addData(List<Track> list){
        if(list != null && !list.isEmpty()){
            this.dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(dataList.get(position).getTitle());
        holder.tv_count.setText(dataList.get(position).getSingerName());
        Picasso.with(mContext).load(dataList.get(position).getArtworkURL())
                .placeholder(R.drawable.ic_default).into(holder.iv_logo);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_count;
        private ImageView iv_logo;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv_logo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_count = itemView.findViewById(R.id.tv_count);
        }
    }

}

