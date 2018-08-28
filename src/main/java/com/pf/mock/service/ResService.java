package com.pf.mock.service;

import com.pf.mock.dao.ResDao;
import com.pf.mock.data.ResInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fupingfu on 2017/7/27.
 */
@Service
public class ResService {
    private ResDao resDao = new ResDao();

    public void insertRes(int type, String date, String name) {
        resDao.insertRes(type, date, name);
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
        resDao.deleteRes(id);
    }
}
