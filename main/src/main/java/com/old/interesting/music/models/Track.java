package com.old.interesting.music.models;

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

    private String fileHash;

    private String SingerName;

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

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setmStreamURL(String mStreamURL) {
        this.mStreamURL = mStreamURL;
    }

    public void setmArtworkURL(String mArtworkURL) {
        this.mArtworkURL = mArtworkURL;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getSingerName() {
        return SingerName;
    }

    public void setSingerName(String singerName) {
        SingerName = singerName;
    }
}
