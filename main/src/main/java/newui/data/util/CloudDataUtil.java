package newui.data.util;

import com.old.interesting.music.Config;
import com.old.interesting.music.intercepter.HttpUtil;
import com.old.interesting.music.interfaces.StreamService;

import org.greenrobot.eventbus.EventBus;

import newui.data.action.ActionBrowPlayTeam;
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

    public static void getPlayTeamList(int start, int pageSize, final int type) {
        StreamService ss = HttpUtil.getApiService(Config.API_LIN_HOST, null);
        Call<PlayTeamBean> call = ss.getPlayTeamList(start, pageSize);
        call.enqueue(new Callback<PlayTeamBean>() {

            @Override
            public void onResponse(Call<PlayTeamBean> call, Response<PlayTeamBean> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getResult() != null && !response.body().getResult().isEmpty()) {
                    EventBus.getDefault().post(new ActionBrowPlayTeam(type, response.body()));
                } else {

                }

            }

            @Override
            public void onFailure(Call<PlayTeamBean> call, Throwable t) {

            }

        });
    }

    public void getPlayList(String musicBoardid, int start, int pageSize) {
        StreamService ss = HttpUtil.getApiService(Config.API_LIN_HOST, null);
        Call<PlayListBean> call = ss.getPlayList(musicBoardid, start, pageSize);
        call.enqueue(new Callback<PlayListBean>() {

            @Override
            public void onResponse(Call<PlayListBean> call, Response<PlayListBean> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getResult() != null && !response.body().getResult().isEmpty()) {

                } else {
                    onFailure(null, new Exception("is nothing"));
                }

            }

            @Override
            public void onFailure(Call<PlayListBean> call, Throwable t) {

            }

        });
    }
}
