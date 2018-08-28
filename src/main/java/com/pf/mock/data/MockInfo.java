package com.pf.mock.data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by fupingfu on 2017/7/28.
 */
public class MockInfo implements Serializable {
    private static final long serialVersionUID = 101L;
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String URL = "url";
    public static final String PATH = "path";
    public static final String PARAMS = "params";

    private int id;
    private String username;
    private String url;
    private String path;
    private String content;
    private Map<String, Object> params;

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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
