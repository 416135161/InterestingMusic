package com.happy.interesting.music.intercepter;

import android.text.TextUtils;

import com.happy.interesting.music.models.Track;
import com.happy.interesting.music.models.searchResponse.Data;
import com.happy.interesting.music.models.searchResponse.SearchResponseBean;
import com.happy.interesting.music.models.searchResponse.Song;

import java.util.ArrayList;
import java.util.Collections;
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
                searchResponseBean.getData().getLists() != null) {
            List<Song> songList = searchResponseBean.getData().getLists();
            if (songList.size() > 0) {
                for (Song song : searchResponseBean.getData().getLists()) {
                    if (!TextUtils.isEmpty(song.getFileHash())) {
                        Track track = new Track();
                        track.setmTitle(song.getSongName());
                        track.setFileHash(song.getFileHash());
                        trackList.add(track);
                    }
                }
            }

        }
        return trackList;
    }
}
