package newui.data.action;

import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import java.util.ArrayList;
import java.util.List;

import newui.data.playListResponse.PlayListResult;
import newui.data.playTeamResponse.PlayTeamBean;

/**
 * Created by sjning
 * created on: 2019/1/14 上午9:55
 * description:
 */
public class ActionPlayList extends ActionBase{
    public ArrayList<SongDetailBean> result;

    public ActionPlayList(ArrayList<SongDetailBean> result) {
        this.result = result;
    }
}
