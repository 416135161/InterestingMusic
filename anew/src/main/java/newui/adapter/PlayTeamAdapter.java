package newui.adapter;

import android.content.Context;

import com.newui.interesting.music.R;

import java.util.List;

import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.playTeamResponse.PlayTeamResult;

/**
 * Created by sjning
 * created on: 2019/1/12 下午4:07
 * description:
 */
public class PlayTeamAdapter extends CommonRecycleAdapter<PlayTeamResult> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;

    public PlayTeamAdapter(Context context, List<PlayTeamResult> dataList, int layoutId) {
        super(context, dataList, R.layout.play_team_list_item);
    }

    public PlayTeamAdapter(Context context, List<PlayTeamResult> dataList, CommonViewHolder.onItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.play_team_list_item);
        this.commonClickListener = commonClickListener;
    }


    @Override
    void bindData(CommonViewHolder holder, PlayTeamResult data) {
        holder.setCommonClickListener(commonClickListener);

    }
}
