package com.old.interesting.music.intercepter;

import com.old.interesting.music.Config;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailKuGou;

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
        StreamService ss = HttpUtil.getApiService(Config.HOST_GET_SONG, new DetailIntercepter());
        Call<SongDetailKuGou> call = ss.getSongDetailKuGou(hash);
        call.enqueue(new Callback<SongDetailKuGou>() {

            @Override
            public void onResponse(Call<SongDetailKuGou> call, Response<SongDetailKuGou> response) {
                if (response.isSuccessful() && response.body() != null ) {
                    SongDetailBean data = TransformUtil.detailResponse2Song(response.body());
                    if (callBack != null) {
                        callBack.onPicOk(data.getImgUrl());
                    }
                } else {
                    if (callBack != null) {
                        callBack.onPicFail();
                    }
                }

            }

            @Override
            public void onFailure(Call<SongDetailKuGou> call, Throwable t) {
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
