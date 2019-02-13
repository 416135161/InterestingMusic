package newui.data.newSongs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/13 上午9:55
 * description:
 */
public class NewSongsResponse implements Serializable{
    private List<NewSongsResult> result;


    public List<NewSongsResult> getResult() {
        return result;
    }

    public void setResult(List<NewSongsResult> result) {
        this.result = result;
    }
}
