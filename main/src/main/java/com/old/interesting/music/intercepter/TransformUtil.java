package com.old.interesting.music.intercepter;

import android.text.TextUtils;

import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.searchResponse.Info;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import java.util.ArrayList;
import java.util.List;

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
                if (!TextUtils.isEmpty(song.getHash())) {
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
}
