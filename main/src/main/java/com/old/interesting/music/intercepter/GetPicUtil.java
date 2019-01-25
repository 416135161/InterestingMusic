package com.old.interesting.music.intercepter;

import com.old.interesting.music.Config;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.songDetailResponse.Data;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sjning
 * created on: 2019/1/25 上午11:05
 * description:
 */
public class GetPicUtil {
    private String hash;
    private GetPicCallBack callBack;


    public GetPicUtil(String hash, GetPicCallBack callBack) {
        this.hash = hash;
        this.callBack = callBack;
    }

    public void getSongFromCloud() {
        StreamService ss = HttpUtil.getApiService(Config.API_GET_SONG, null);
        Call<SongDetailBean> call = ss.getSongDetail(hash);
        call.enqueue(new Callback<SongDetailBean>() {

            @Override
            public void onResponse(Call<SongDetailBean> call, Response<SongDetailBean> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    Data data = response.body().getData();
                    if (callBack != null) {
                        callBack.onPicOk(data.getImg());
                    }
                } else {
                    if (callBack != null) {
                        callBack.onPicFail();
                    }
                }

            }

            @Override
            public void onFailure(Call<SongDetailBean> call, Throwable t) {
                if (callBack != null) {
                    callBack.onPicFail();
                }
            }

        });
    }

    public interface GetPicCallBack {
        void onPicOk(String url);

        void onPicFail();
    }
}
