package newui.data.newSongs;

import android.text.TextUtils;

/**
 * Created by sjning
 * created on: 2019/2/13 上午9:58
 * description:
 */
public class NewSongBean {
    private String mvUrl;
    private String img;
    private String filename;
    private long passtime;
    private String albumId;
    private String time;
    private int id;
    private String type;
    private String hash;
    private String playUrl;

    public void setMvUrl(String mvUrl) {
        this.mvUrl = mvUrl;
    }

    public String getMvUrl() {
        return mvUrl;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        if(!TextUtils.isEmpty(img)){
            img = img.replace("{size}", "240");
        }
        return img;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setPasstime(long passtime) {
        this.passtime = passtime;
    }

    public long getPasstime() {
        return passtime;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }
}
