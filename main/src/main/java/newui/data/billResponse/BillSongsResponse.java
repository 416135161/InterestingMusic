package newui.data.billResponse;

import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/13 上午10:03
 * description:
 */
public class BillSongsResponse {

    private List<BillSongsResult> result;
    private boolean success;
    private int rowCount;

    public List<BillSongsResult> getResult() {
        return result;
    }

    public void setResult(List<BillSongsResult> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
