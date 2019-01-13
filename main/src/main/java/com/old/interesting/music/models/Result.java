package com.old.interesting.music.models;

import com.google.gson.Gson;

/**
 * Created by sjning
 * created on: 2018/10/25 下午2:55
 * description:
 */
public class Result<T> {
    public boolean success;
    public int rowCount;
    public T result;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
