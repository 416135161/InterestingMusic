package com.old.interesting.music.intercepter;

import com.old.interesting.music.Config;
import com.old.interesting.music.interfaces.GetSongCallBack;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailKuGou;

import org.greenrobot.eventbus.EventBus;

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
        if(track == null){
            return;
        }
        EventBus.getDefault().post(new ActionStartLoading());
        StreamService ss = HttpUtil.getApiService(Config.HOST_GET_SONG, new DetailIntercepter());
        Call<SongDetailKuGou> call = ss.getSongDetailKuGou(track.getFileHash());
        call.enqueue(new Callback<SongDetailKuGou>() {

            @Override
            public void onResponse(Call<SongDetailKuGou> call, Response<SongDetailKuGou> response) {
                EventBus.getDefault().post(new ActionStopLoading());
                if (response.isSuccessful() && response.body() != null ) {
                    SongDetailBean data = TransformUtil.detailResponse2Song(response.body());
                    track.setmArtworkURL(data.getImgUrl());
                    track.setmStreamURL(data.getPlayUrl());
                    track.setmDuration(data.getDuration() * 1000);
                    track.setLrc(data.getLrc());
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
            public void onFailure(Call<SongDetailKuGou> call, Throwable t) {
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
