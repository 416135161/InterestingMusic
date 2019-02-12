package newui.data.util;

import com.old.interesting.music.Config;
import com.old.interesting.music.intercepter.HttpUtil;
import com.old.interesting.music.interfaces.StreamService;

import org.greenrobot.eventbus.EventBus;

import newui.data.LrcResponse.LrcResponseBean;
import newui.data.action.ActionBase;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.action.ActionPlayList;
import newui.data.playListResponse.PlayListBean;
import newui.data.playTeamResponse.PlayTeamBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sjning
 * created on: 2019/1/12 上午10:09
 * description:
 */
public final class CloudDataUtil {
    //获取歌单
    public static void getPlayTeamList(int start, int pageSize, final int type) {
        StreamService ss = HttpUtil.getApiService(Config.API_LIN_HOST, null);
        Call<PlayTeamBean> call = ss.getPlayTeamList(start, pageSize);
        call.enqueue(new Callback<PlayTeamBean>() {

            @Override
            public void onResponse(Call<PlayTeamBean> call, Response<PlayTeamBean> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getResult() != null && !response.body().getResult().isEmpty()) {
                    if (type == ActionBrowPlayTeam.TYPE_TEAM_LIST) {
                        EventBus.getDefault().post(new ActionListPlayTeam(response.body()));
                    } else if (type == ActionBrowPlayTeam.TYPE_BROW) {
                        EventBus.getDefault().post(new ActionBrowPlayTeam(response.body()));

                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<PlayTeamBean> call, Throwable t) {
                if (type == ActionBrowPlayTeam.TYPE_TEAM_LIST) {
                    EventBus.getDefault().post(new ActionListPlayTeam(null));
                } else if (type == ActionBrowPlayTeam.TYPE_BROW) {
                    EventBus.getDefault().post(new ActionBrowPlayTeam(null));
                }
            }

        });
    }

    //获取歌单下的歌曲
    public static void getPlayList(String musicBoardid, int start, int pageSize) {
        StreamService ss = HttpUtil.getApiService(Config.API_LIN_HOST, null);
        Call<PlayListBean> call = ss.getPlayList(musicBoardid, start, pageSize);
        call.enqueue(new Callback<PlayListBean>() {

            @Override
            public void onResponse(Call<PlayListBean> call, Response<PlayListBean> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getResult() != null && !response.body().getResult().isEmpty()) {
                    EventBus.getDefault().post(new ActionPlayList(response.body().getResult()));
                } else {
                    onFailure(null, new Exception("is nothing"));
                }

            }

            @Override
            public void onFailure(Call<PlayListBean> call, Throwable t) {
                ActionPlayList action = new ActionPlayList(null);
                action.isOK = false;
                EventBus.getDefault().post(action);

            }

        });
    }


}
