package newui.data.LrcResponse;

/**
 * Created by sjning
 * created on: 2019/1/18 上午11:42
 * description:
 */
public class LrcResponseBean {
    private int status;
    private int err_code;
    private LrcData data;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }
    public int getErr_code() {
        return err_code;
    }

    public void setData(LrcData data) {
        this.data = data;
    }
    public LrcData getData() {
        return data;
    }
}
