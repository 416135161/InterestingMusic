package newui.data.action;

import newui.data.playTeamResponse.PlayTeamBean;

/**
 * Created by sjning
 * created on: 2019/1/12 下午3:02
 * description:
 */
public class ActionBrowPlayTeam {

    public static final int TYPE_BROW = 0;
    public static final int TYPE_TEAM_LIST =1;

    public int type;
    public PlayTeamBean playTeamBean;

    public ActionBrowPlayTeam(PlayTeamBean playTeamBean) {
        this.playTeamBean = playTeamBean;
    }

    public ActionBrowPlayTeam(int type, PlayTeamBean playTeamBean) {
        this.type = type;
        this.playTeamBean = playTeamBean;
    }
}
