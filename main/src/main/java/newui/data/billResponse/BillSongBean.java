package newui.data.billResponse;

import android.text.TextUtils;

/**
 * Created by sjning
 * created on: 2019/2/13 上午10:11
 * description:
 */
public class BillSongBean {
    private String imgUrl;
    private String mvUrl;
    private String filename;
    private String rankid;
    private long passtime;
    private long id;
    private String extname;
    private String hash;
    private String playUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        if(!TextUtils.isEmpty(imgUrl)){
           imgUrl = imgUrl.replace("{size}", "240");
        }
        return imgUrl;
    }

    public void setMvUrl(String mvUrl) {
        this.mvUrl = mvUrl;
    }

    public String getMvUrl() {
        return mvUrl;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setRankid(String rankid) {
        this.rankid = rankid;
    }

    public String getRankid() {
        return rankid;
    }

    public void setPasstime(long passtime) {
        this.passtime = passtime;
    }

    public long getPasstime() {
        return passtime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public String getExtname() {
        return extname;
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
