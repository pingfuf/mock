package com.pf.mock.dao;

import com.pf.mock.data.MockInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fupingfu on 2017/7/28.
 */
@Resource
public class MockDao {

    public List<MockInfo> getMockListByUsername(String username) {
        return null;
    }

    public List<MockInfo> getMockListByUrl(String url) {
        return null;
    }
}