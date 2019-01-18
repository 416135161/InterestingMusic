/**
  * Copyright 2019 bejson.com 
  */
package com.old.interesting.music.models.searchResponse;

/**
 * Auto-generated: 2019-01-08 19:40:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchResponseBean {

    private int status;
    private String error;
    private Data data;
    private int errcode;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
    public int getErrcode() {
        return errcode;
    }
}