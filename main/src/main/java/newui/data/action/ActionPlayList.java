package newui.data.action;

import java.util.List;

import newui.data.playListResponse.PlayListResult;

/**
 * Created by sjning
 * created on: 2019/1/14 上午9:55
 * description:
 */
public class ActionPlayList extends ActionBase{
    public List<PlayListResult> result;

    public ActionPlayList(List<PlayListResult> result) {
        this.result = result;
    }
}
