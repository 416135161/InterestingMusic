package newui.data.newSongs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/13 上午9:57
 * description:
 */
public class NewSongsResult implements Serializable{
    private String title;
    private List<NewSongBean> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewSongBean> getItems() {
        return items;
    }

    public void setItems(List<NewSongBean> items) {
        this.items = items;
    }
}
