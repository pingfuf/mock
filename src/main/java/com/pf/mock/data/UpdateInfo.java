package com.pf.mock.data;

import java.io.Serializable;
import java.util.List;

public class UpdateInfo implements Serializable {
    private static final long serialVersionUID = 4117251956057845067L;
    private List<String> deleteFiles;
    private List<String> changeFiles;

    public List<String> getDeleteFiles() {
        return deleteFiles;
    }

    public void setDeleteFiles(List<String> deleteFiles) {
        this.deleteFiles = deleteFiles;
    }

    public List<String> getChangeFiles() {
        return changeFiles;
    }

    public void setChangeFiles(List<String> changeFiles) {
        this.changeFiles = changeFiles;
    }
}
