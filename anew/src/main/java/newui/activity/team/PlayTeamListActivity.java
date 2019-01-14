package newui.activity.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newui.interesting.music.R;
import com.old.interesting.music.models.Track;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import newui.adapter.CommonViewHolder;
import newui.adapter.PlayTeamAdapter;
import newui.adapter.SearchListAdapter;
import newui.base.BaseActivity;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.playTeamResponse.PlayTeamResult;
import newui.data.util.CloudDataUtil;

/**
 * Created by sjning
 * created on: 2019/1/12 下午4:03
 * description:
 */
public class PlayTeamListActivity extends BaseActivity {

    private RecyclerView mRecycleView;
    private PlayTeamAdapter mAdapter;
    private int mStartIndex = 0;
    private int PAGE_SIZE = 40;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_team_list_activity);
//        EventBus.getDefault().register(this);
        mRecycleView = findViewById(R.id.rv_items);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlayTeamAdapter(this, new ArrayList<PlayTeamResult>(), new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }

            @Override
            public void onItemLongClickListener(int position) {

            }
        });
        mRecycleView.setAdapter(mAdapter);
        startLoadingIndicator();
        CloudDataUtil.getPlayTeamList(mStartIndex, PAGE_SIZE, ActionBrowPlayTeam.TYPE_TEAM_LIST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(ActionListPlayTeam event) {

        PlayTeamBean playTeamBean = event.playTeamBean;
        if (playTeamBean.getResult() != null && !playTeamBean.getResult().isEmpty()) {
            mStartIndex += playTeamBean.getResult().size();
            mAdapter.addData(playTeamBean.getResult());
        }
    }
}
