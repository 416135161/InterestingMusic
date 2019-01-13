/**
 * Copyright 2019 bejson.com
 */
package newui.data.playTeamResponse;

import java.util.List;

/**
 * Auto-generated: 2019-01-12 9:52:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PlayTeamBean {

    private List<PlayTeamResult> result;
    private boolean success;
    private int rowCount;

    public void setResult(List<PlayTeamResult> result) {
        this.result = result;
    }

    public List<PlayTeamResult> getResult() {
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

}