package com.happy.interesting.music.interfaces;

import com.happy.interesting.music.models.Result;
import com.happy.interesting.music.models.searchResponse.SearchResponseBean;
import com.happy.interesting.music.models.Track;
import com.happy.interesting.music.models.songDetailResponse.SongDetailBean;

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

    @GET("/song_search_v2?page=1&pagesize=1&iscorrection=7&clientver=2.6.6&area_code=1")
    Call<SearchResponseBean> searchSong(@Query("keyword") String keyword);

    @GET("/yy/index.php?r=play/getdata")
    Call<SongDetailBean> getSongDetail(@Query("hash") String hash);

//    private void sss(){
//        HttpClient client = new DefaultHttpClient();
//
//        HttpGet httpGet = new HttpGet("http://www.kugou.com/yy/index.php?r=play/getdata&hash="+module.getHash());
//
//        HttpResponse response = client.execute(httpGet);
//
//        musicName = EntityUtils.toString(response.getEntity(), "UTF-8");
//        Gson jGson = new Gson();
//        Map allobj = jGson.fromJson(musicName, Map.class);
//
//        Map obj = (Map)allobj.get("data");
//
//        if(module.getImg() == null){
//            module.setImg(obj.get("img").toString());
//        }
//
//        if(obj.get("play_url") != null){
//            module.setPlayUrl(obj.get("play_url").toString());
//
//        }
//    }
}
