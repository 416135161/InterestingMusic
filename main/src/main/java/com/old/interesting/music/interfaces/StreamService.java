package com.old.interesting.music.interfaces;

import com.old.interesting.music.Config;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailKuGou;

import java.util.ArrayList;
import java.util.List;

import newui.data.billResponse.BillSongsResponse;
import newui.data.newSongs.NewSongsResponse;
import newui.data.playListResponse.PlayListBean;
import newui.data.playTeamResponse.PlayTeamBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Harjot on 30-Apr-16.
 */
public interface StreamService {
    /**
     * 这是获取新歌的  size弄成240
     *
     * @return
     */
    @GET("/music/new")
    Call<ArrayList<SongDetailBean>> getNewSongs(@Query("from") String from);

    /**
     * 这是获取排行榜数据的
     *
     * @return
     */
    @GET("/music/hot")
    Call<ArrayList<SongDetailBean>> getBillSongs(@Query("from") String from);

    /**
     * 酷狗搜歌
     *
     * @param keyword
     * @return
     */
    @GET("/music/search")
    Call<ArrayList<SongDetailBean>> searchSong(@Query("keyword") String keyword, @Query("pageSize") int pageSize);

    /**
     * 获取歌曲播放地址
     *
     * @param hash
     * @return
     */
    @GET("/yy/index.php?r=play/getdata")
    Call<SongDetailKuGou> getSongDetailKuGou(@Query("hash") String hash);
    /**
     * 获取歌曲播放地址
     *
     * @param hash
     * @return
     */
    @GET("/music/detail")
    Call<SongDetailBean> getSongDetail(@Query("hash") String hash);
    /**
     * 获取歌单
     *
     * @param pageSize
     * @return
     */
    @GET("/music/albums")
    Call<ArrayList<PlayTeamBean>> getPlayTeamList(@Query("pageSize") int pageSize, @Query("from") String from);

    /**
     * 获取歌单下的歌曲
     *
     * @param id
     * @return
     */
    @GET("/music/album/songs")
    Call<ArrayList<SongDetailBean>> getPlayList(@Query("id") String id);

    /**
     * 获取欧洲歌单
     *
     * @param pageSize
     * @return
     */
    @GET("/music/europe/albums")
    Call<ArrayList<PlayTeamBean>> getEuropePlayTeamList(@Query("pageSize") int pageSize, @Query("from") String from);

    /**
     * 获取欧洲歌单下的歌曲
     *
     * @param id
     * @return
     */
    @GET("/music/europe/album/songs")
    Call<ArrayList<SongDetailBean>> getEuropePlayList(@Query("id") String id);

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

//     查找版本号
//    http://39.107.89.143/apps/music/list.do?code=android

}
