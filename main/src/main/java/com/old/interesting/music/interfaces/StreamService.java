package com.old.interesting.music.interfaces;

import com.old.interesting.music.models.Result;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import java.util.List;


import newui.data.LrcResponse.LrcResponseBean;
import newui.data.playListResponse.PlayListBean;
import newui.data.playTeamResponse.PlayTeamBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Harjot on 30-Apr-16.
 */
public interface StreamService {
    @GET("/qq/newmusic/list.do")
    Call<Result<List<Track>>> getTracks(@Query("start") int start, @Query("pageSize") int pageSize, @Query("type") int type);

    /**
     * 酷狗搜歌
     *
     * @param keyword
     * @return
     */
    @GET("/api/v3/search/song?format=json&page=1&showtype=1")
    Call<SearchResponseBean> searchSong(@Query("keyword") String keyword, @Query("pagesize") int pagesize);

    /**
     * 获取歌曲播放地址
     *
     * @param hash
     * @return
     */
    @GET("/yy/index.php?r=play/getdata")
    Call<SongDetailBean> getSongDetail(@Query("hash") String hash);

    /**
     * 获取歌单
     *
     * @param start
     * @param pageSize
     * @return
     */
    @GET("/musicBoard/list.do")
    Call<PlayTeamBean> getPlayTeamList(@Query("start") int start, @Query("pageSize") int pageSize);

    /**
     * 获取歌单下的歌曲
     *
     * @param musicBoardid
     * @param start
     * @param pageSize
     * @return
     */
    @GET("/musicBoard/listItem/list.do")
    Call<PlayListBean> getPlayList(@Query("musicBoardid") String musicBoardid, @Query("start") int start,
                                   @Query("pageSize") int pageSize);
    @GET("/yy/index.php?r=play/getdata")
    Call<LrcResponseBean> getSongLrc(@Query("hash") String hash);

//    这是获取新歌的  size弄成240
//    http://39.107.89.143/newSongs/list.do

//    这是获取排行榜数据的
//    http://39.107.89.143/billBoard/list.do
//    获取歌单
//    http://39.107.89.143/musicBoard/list.do?start=0&pageSize=10
    // 获取歌单下的歌曲
//    http://39.107.89.143/musicBoard/listItem/list.do?musicBoardid=XXX&start=0&pageSize=20


//    找图片素材用的
//    https://www.iconfont.cn/

}
