package com.happy.interesting.music.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Harjot on 30-Apr-16.
 */
public class Track {
    @SerializedName("songname")
    private String mTitle;

    @SerializedName("id")
    private int mID;

    @SerializedName("duration")
    public int mDuration;

    @SerializedName("playUrl")
    private String mStreamURL;

    @SerializedName("imgUrl")
    private String mArtworkURL;

    public String getTitle() {
        return mTitle;
    }

    public int getID() {
        return mID;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    public String getArtworkURL() {
        return mArtworkURL;
    }

}
