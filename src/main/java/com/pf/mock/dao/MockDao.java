package com.pf.mock.dao;

import com.alibaba.fastjson.JSON;
import com.pf.mock.SqliteManager;
import com.pf.mock.data.MockInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fupingfu on 2017/7/28.
 */
@Resource
public class MockDao {
    private JdbcTemplate mJdbcTemplate = SqliteManager.getInstance().getJdbcTemplate();

    public List<MockInfo> getMockByUrl(String url) {
        mJdbcTemplate.execute("SELECT * FROM mock");
        String sql = "select * from mock where url='" + url + "'";
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();

        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        if (maps != null) {
            for (Map<String, Object> map : maps) {
                MockInfo mockInfo = parseMock(map);
                if (mockInfo != null) {
                    mockInfos.add(mockInfo);
                }
            }
        }

        return mockInfos;
    }

    public List<MockInfo> getMockByUrl(String url, String params) {
        return null;
    }

    public boolean updateMock(int id, String url, String params, String username, String path, String content) {
        return false;
    }

    public boolean insertMock(String url, String params, String username, String path, String content) {
        return false;
    }

    public boolean deleteMock(int id) {
        return false;
    }

    private MockInfo parseMock(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        MockInfo mockInfo = null;
        try {
            mockInfo = JSON.parseObject(JSON.toJSONString(map), MockInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mockInfo;
    }
}