package newui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.itheima.roundedimageview.RoundedImageView;
import com.newui.interesting.music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import newui.data.playListResponse.PlayListResult;
import newui.data.playTeamResponse.PlayTeamResult;

/**
 * Created by sjning
 * created on: 2019/1/14 上午7:53
 * description:
 */
public class PlayListAdapter extends CommonRecycleAdapter<PlayListResult> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;

    public PlayListAdapter(Context context, List<PlayListResult> dataList, int layoutId) {
        super(context, dataList, R.layout.play_list_item);
    }

    public PlayListAdapter(Context context, List<PlayListResult> dataList, CommonViewHolder.onItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.play_list_item);
        this.commonClickListener = commonClickListener;
    }


    @Override
    void bindData(CommonViewHolder holder, PlayListResult data) {
        TextView tvName = holder.getView(R.id.tv_name);
        tvName.setText(data.getFilename());
        RoundedImageView imageView = holder.getView(R.id.iv_logo);
        Picasso.with(mContext).load(data.getImgUrl()).placeholder(R.drawable.ic_default).into(imageView);
        holder.setCommonClickListener(commonClickListener);

    }
}

