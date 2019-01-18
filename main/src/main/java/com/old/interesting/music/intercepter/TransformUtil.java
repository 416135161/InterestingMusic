package com.old.interesting.music.intercepter;

import android.text.TextUtils;

import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.searchResponse.Info;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;

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

    public static List<Track> searchResponse2Track(SearchResponseBean searchResponseBean) {
        List<Track> trackList = new ArrayList<>();
        if (searchResponseBean != null && searchResponseBean.getData() != null &&
                searchResponseBean.getData().getInfo() != null) {
            List<Info> songList = searchResponseBean.getData().getInfo();
            if (songList.size() > 0) {
                for (Info song : songList) {
                    if (!TextUtils.isEmpty(song.getHash())) {
                        Track track = new Track();
                        track.setmTitle(song.getSongname());
                        track.setFileHash(song.getHash());
                        track.setSingerName(song.getSingername());
                        trackList.add(track);
                    }
                }
            }

        }
        return trackList;
    }
}
