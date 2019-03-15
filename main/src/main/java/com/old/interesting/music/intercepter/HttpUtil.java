package com.old.interesting.music.intercepter;

import com.old.interesting.music.Config;
import com.old.interesting.music.interfaces.GetSongCallBack;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.songDetailResponse.Data;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import org.greenrobot.eventbus.EventBus;

import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionStartLoading;
import newui.data.action.ActionStopLoading;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sjning
 * created on: 2019/1/9 下午3:01
 * description:
 */
public final class HttpUtil {

    private HttpUtil() {

    }

    public static void getSongFromCloud(final Track track, final GetSongCallBack callBack) {
        EventBus.getDefault().post(new ActionStartLoading());
        StreamService ss = HttpUtil.getApiService(Config.API_GET_SONG, null);
        Call<SongDetailBean> call = ss.getSongDetail(track.getFileHash());
        call.enqueue(new Callback<SongDetailBean>() {

            @Override
            public void onResponse(Call<SongDetailBean> call, Response<SongDetailBean> response) {
                EventBus.getDefault().post(new ActionStopLoading());
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    Data data = response.body().getData();
                    track.setmArtworkURL(data.getImg());
                    track.setmStreamURL(data.getPlay_url());
                    track.setmDuration(data.getTimelength());
                    track.setLrc(data.getLyrics());
                    if(callBack != null){
                        callBack.onSongGetOk();
                    }
                } else {
                    if(callBack != null){
                        callBack.onSongGetFail();
                    }
                }

            }

            @Override
            public void onFailure(Call<SongDetailBean> call, Throwable t) {
                EventBus.getDefault().post(new ActionStopLoading());
                if(callBack != null){
                    callBack.onSongGetFail();
                }
            }

        });
    }

    public static StreamService getApiService(String host, Interceptor interceptor) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(host);
        if (interceptor != null) {
            builder.client(new OkHttpClient.Builder().addInterceptor(interceptor).build());
        }
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit client = builder.build();
        StreamService ss = client.create(StreamService.class);
        return ss;
    }

}
