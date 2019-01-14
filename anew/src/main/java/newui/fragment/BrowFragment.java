package newui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.itheima.roundedimageview.RoundedImageView;
import com.newui.interesting.music.R;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import newui.activity.team.PlayTeamListActivity;
import newui.activity.SearchActivity;
import newui.data.action.ActionBrowPlayTeam;
import newui.base.BaseFragment;
import newui.data.playTeamResponse.PlayTeamBean;
import newui.data.playTeamResponse.PlayTeamResult;
import newui.data.util.CloudDataUtil;

/**
 * Created by sjning
 * created on: 2019/1/10 下午5:14
 * description:
 */
public class BrowFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mIvBack, mIvSearch;
    private LinearLayout mLLPlayList;
    private TextView mTvPlayList;


    private ImageLoader mImageLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.brow_fragment, container, false);
        mIvBack = rootView.findViewById(R.id.iv_back);
        mIvSearch = rootView.findViewById(R.id.iv_right);
        mTvPlayList = rootView.findViewById(R.id.tv_play_list);
        mLLPlayList = rootView.findViewById(R.id.ll_play_list);
        initView();
        return rootView;
    }


    private void initView() {
        mIvBack.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mIvSearch.setVisibility(View.VISIBLE);
        mTvPlayList.setOnClickListener(this);

        CloudDataUtil.getPlayTeamList(0, 3, ActionBrowPlayTeam.TYPE_BROW);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            getActivity().onBackPressed();
        } else if (v.getId() == R.id.iv_right) {
            Intent intent = new Intent();
            intent.setClass(this.getActivity(), SearchActivity.class);
            getActivity().startActivity(intent);
        }else if(v.getId() == R.id.tv_play_list){
            Intent intent = new Intent();
            intent.setClass(this.getActivity(), PlayTeamListActivity.class);
            getActivity().startActivity(intent);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPosting(ActionBrowPlayTeam event) {
        PlayTeamBean playTeamBean = event.playTeamBean;
        if (playTeamBean.getResult() != null && !playTeamBean.getResult().isEmpty()) {
            int count = 0;
            for (PlayTeamResult item : playTeamBean.getResult()) {
                LinearLayout viewItem = (LinearLayout) getLayoutInflater().inflate(R.layout.brow_paly_list_item, null);
                RoundedImageView imageView = viewItem.findViewById(R.id.iv_logo);
                TextView tvName = viewItem.findViewById(R.id.tv_name);
                TextView tvCount = viewItem.findViewById(R.id.tv_count);
                Picasso.with(this.getContext()).load(item.getImgurl()).placeholder(R.drawable.ic_default).into(imageView);
                tvName.setText(item.getSpecialname());
                mLLPlayList.addView(viewItem);
                count++;
                if (count == 3) {
                    break;
                }
            }
        }
    }

}
