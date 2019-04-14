package newui.activity.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newui.interesting.music.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import newui.adapter.CommonViewHolder;
import newui.adapter.PlayListAdapter;
import newui.base.BaseActivity;
import newui.data.action.ActionPlayList;
import newui.data.playListResponse.PlayListResult;
import newui.data.util.CloudDataUtil;

/**
 * Created by sjning
 * created on: 2019/1/14 上午7:49
 * description:
 */
public class PlayListAct extends BaseActivity {
    public static String musicBoardid;

    private RecyclerView mRecycleView;
    private PlayListAdapter mAdapter;
    private int mStartIndex = 0;
    private int PAGE_SIZE = 40;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_list_activity);
        mRecycleView = findViewById(R.id.rv_items);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlayListAdapter(this, new ArrayList<PlayListResult>(), new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }

            @Override
            public void onItemLongClickListener(int position) {

            }
        });
        mRecycleView.setAdapter(mAdapter);
        startLoadingIndicator();
        CloudDataUtil.getPlayList(musicBoardid, "1");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(ActionPlayList event) {
        stopLoadingIndicator();

    }
}
