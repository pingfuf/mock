package com.pf.mock.service;

import com.pf.mock.dao.ResDao;
import com.pf.mock.data.ResInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by fupingfu on 2017/7/27.
 */
@Service
public class ResService {
    private ResDao resDao = new ResDao();

    public void insertRes(int type, String name, String date) {
        resDao.insertRes(type, name, date);
    }

    public List<ResInfo> getResList(int type) {
        return resDao.getResListByType(type);
    }

    public List<ResInfo> getResList() {
        return resDao.getResList();
    }

    public ResInfo getLatestRes(int type) {
        return resDao.getLatestRes(type);
    }

    public ResInfo getResInfoById(int id) {
        return resDao.getResById(id);
    }

    public void deleteRes(int id) {
        ResInfo resInfo = resDao.getResById(id);
        if (resInfo != null && resInfo.getPath() != null) {
            File file = new File(resInfo.getPath());
            file.delete();
        }
        resDao.deleteRes(id);
    }
}
