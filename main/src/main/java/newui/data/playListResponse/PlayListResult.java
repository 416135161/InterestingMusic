/**
  * Copyright 2019 bejson.com 
  */
package newui.data.playListResponse;

import java.io.Serializable;

/**
 * Auto-generated: 2019-01-12 9:59:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PlayListResult implements Serializable{

    private String imgUrl;
    private int musicBoardid;
    private String filename;
    private String rankid;
    private long id;
    private String extname;
    private String hash;
    public void setImgUrl(String imgUrl) {
         this.imgUrl = imgUrl;
     }
     public String getImgUrl() {
         return imgUrl;
     }

    public void setMusicBoardid(int musicBoardid) {
         this.musicBoardid = musicBoardid;
     }
     public int getMusicBoardid() {
         return musicBoardid;
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

}