package com.pf.mock.data;

import java.io.Serializable;

public class ResResult implements Serializable{
    private static final long serialVersionUID = 101L;

    private String url;
    private ResInfo resInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResInfo getResInfo() {
        return resInfo;
    }

    public void setResInfo(ResInfo resInfo) {
        this.resInfo = resInfo;
    }
}
