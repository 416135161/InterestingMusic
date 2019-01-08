/**
  * Copyright 2019 bejson.com 
  */
package com.happy.interesting.music.models.searchResponse;

/**
 * Auto-generated: 2019-01-08 19:40:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchResponseBean {

    private int status;
    private int error_code;
    private Data data;
    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}