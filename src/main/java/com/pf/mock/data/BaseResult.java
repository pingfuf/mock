package com.pf.mock.data;

import java.io.Serializable;

/**
 * Created by fupingfu on 2017/7/27.
 */
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 101l;

    private int code;
    private String message;
    private T res;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }
}