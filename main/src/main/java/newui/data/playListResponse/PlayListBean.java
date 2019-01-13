/**
  * Copyright 2019 bejson.com 
  */
package newui.data.playListResponse;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2019-01-12 9:59:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PlayListBean implements Serializable{

    private List<PlayListResult> result;
    private boolean success;
    private int rowCount;
    public void setResult(List<PlayListResult> result) {
         this.result = result;
     }
     public List<PlayListResult> getResult() {
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