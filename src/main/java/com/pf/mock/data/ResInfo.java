package com.pf.mock.data;

import java.io.Serializable;

public class ResInfo implements Serializable {
    private static final long serialVersionUID = 110L;

    public static final int APK_TYPE = 1;

    public static final int IPA_TYPE = 2;

    public static final int STUDENT_H5_TYPE = 3;

    public static final int TEACHER_H5_TYPE = 4;

    public static final String APK_TYPE_NAME = "android安装包";
    public static final String IPA_TYPE_NAME = "ios安装包";
    public static final String STUDENT_RES_TYPE = "学生端H5资源";
    public static final String TEACHER_RES_TYPE = "教师端H5资源";

    private int id;
    private int type;
    private String typeName;
    private String date;
    private String name;
    private String path;
    private String fileSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
