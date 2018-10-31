package com.happy.interesting.music.interfaces;

import com.happy.interesting.music.Config;
import com.happy.interesting.music.R;
import com.happy.interesting.music.models.Result;
import com.happy.interesting.music.models.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;

/**
 * Created by Harjot on 30-Apr-16.
 */
public interface StreamService {
    @GET("/qq/newmusic/list.do")
    Call<Result<List<Track>>> getTracks(@Query("start") int start, @Query("pageSize") int pageSize, @Query("type") int type);
}
