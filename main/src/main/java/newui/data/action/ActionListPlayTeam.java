package newui.data.action;

import java.util.ArrayList;

import newui.data.playTeamResponse.PlayTeamBean;

/**
 * Created by sjning
 * created on: 2019/1/14 上午7:30
 * description:
 */
public class ActionListPlayTeam {
    public String from;
    public ArrayList<PlayTeamBean> playTeamBean;

    public ActionListPlayTeam(ArrayList<PlayTeamBean> playTeamBean, String from) {
        this.playTeamBean = playTeamBean;
        this.from = from;
    }

    public ArrayList<PlayTeamBean> getPlayTeamBean() {
        return playTeamBean;
    }

    public void setPlayTeamBean(ArrayList<PlayTeamBean> playTeamBean) {
        this.playTeamBean = playTeamBean;
    }
}
