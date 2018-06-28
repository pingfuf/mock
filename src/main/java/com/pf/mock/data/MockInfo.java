package com.pf.mock.data;

import java.io.Serializable;

/**
 * Created by fupingfu on 2017/7/28.
 */
public class MockInfo implements Serializable {
    private static final long serialVersionUID = 10l;

    private int id;
    private String username;
    private String url;
    private String path;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
