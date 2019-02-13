package newui.data.billResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/13 上午10:08
 * description:
 */
public class BillSongsResult implements Serializable{

    private String imgUrl;
    private String rankid;
    private String rankname;
    private int id;
    private int sort;
    private List<BillSongBean> items;
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setRankid(String rankid) {
        this.rankid = rankid;
    }
    public String getRankid() {
        return rankid;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }
    public String getRankname() {
        return rankname;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    public int getSort() {
        return sort;
    }

    public List<BillSongBean> getItems() {
        return items;
    }

    public void setItems(List<BillSongBean> items) {
        this.items = items;
    }
}
