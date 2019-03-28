/**
 * Copyright 2019 bejson.com
 */
package newui.data.playTeamResponse;

import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Auto-generated: 2019-01-12 9:52:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PlayTeamResult {

    private String imgurl;
    private String playcount;
    private String publish_time;
    private int specialid;
    private int id;
    private String specialname;
    private String cid;

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setSpecialid(int specialid) {
        this.specialid = specialid;
    }

    public int getSpecialid() {
        return specialid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSpecialname(String specialname) {
        this.specialname = specialname;
    }

    public String getSpecialname() {
        if (!TextUtils.isEmpty(specialname) && specialname.contains("-")) {
            return specialname.split("-")[0];
        }
        return specialname;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

}