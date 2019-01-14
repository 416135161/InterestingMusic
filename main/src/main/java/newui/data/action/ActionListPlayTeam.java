package newui.data.action;

import newui.data.playTeamResponse.PlayTeamBean;

/**
 * Created by sjning
 * created on: 2019/1/14 上午7:30
 * description:
 */
public class ActionListPlayTeam {
    public PlayTeamBean playTeamBean;

    public ActionListPlayTeam(PlayTeamBean playTeamBean) {
        this.playTeamBean = playTeamBean;
    }

    public PlayTeamBean getPlayTeamBean() {
        return playTeamBean;
    }

    public void setPlayTeamBean(PlayTeamBean playTeamBean) {
        this.playTeamBean = playTeamBean;
    }
}
