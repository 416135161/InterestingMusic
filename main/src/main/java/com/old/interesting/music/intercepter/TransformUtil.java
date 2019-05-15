package com.old.interesting.music.intercepter;

import android.text.TextUtils;

import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.searchResponse.Info;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;
import com.old.interesting.music.models.songDetailResponse.Data;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailKuGou;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sjning
 * created on: 2019/1/9 下午4:03
 * description:
 */
public final class TransformUtil {
    private TransformUtil() {

    }

    public static List<Track> searchResponse2Track(List<SongDetailBean> searchResponseBean) {
        List<Track> trackList = new ArrayList<>();
        if (searchResponseBean != null && searchResponseBean.size() > 0) {
            for (SongDetailBean song : searchResponseBean) {
                if (!TextUtils.isEmpty(song.getHash()) && !isContainChinese(song.getSongName())
                        && !isContainChinese(song.getSingerName())) {
                    Track track = new Track();
                    track.setmTitle(song.getSongName());
                    track.setFileHash(song.getHash());
                    track.setSingerName(song.getSingerName());
                    trackList.add(track);
                }
            }
        }
        return trackList;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public synchronized static SongDetailBean detailResponse2Song(SongDetailKuGou songDetailBean) {

        SongDetailBean song = new SongDetailBean();
        if (songDetailBean != null && songDetailBean.getData() != null) {
            Data data = songDetailBean.getData();
            song.setSongName(data.getSong_name());
            song.setHash(data.getHash());
            song.setSingerName(data.getAuthor_name());
            song.setDuration(data.getTimelength() / 1000);
            song.setPlayUrl(data.getPlay_url());
            song.setLrc(data.getLyrics());
            song.setImgUrl(data.getImg());
        }
        return song;
    }
}
