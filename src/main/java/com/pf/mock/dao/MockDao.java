package com.pf.mock.dao;

import com.alibaba.fastjson.JSON;
import com.pf.mock.SqliteManager;
import com.pf.mock.data.MockInfo;
import com.pf.mock.utils.FileUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fupingfu on 2017/7/28.
 */
public class MockDao {
    private JdbcTemplate mJdbcTemplate = SqliteManager.getInstance().getJdbcTemplate();
    public static final int PAGE_SIZE = 50;

    public List<MockInfo> getMockByUrl(String url) {
        String sql = "select * from mock where url='" + url + "'";
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);

        return parseMockList(maps);
    }

    public List<MockInfo> getMockList(int page) {
        String sql = String.format("select * from mock limit %s offset %s", PAGE_SIZE, page * PAGE_SIZE);
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);

        return parseMockList(maps);
    }

    public boolean updateMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            return false;
        }

        String sql = "update mock set ";
        sql += "username='" + mockInfo.getUsername() + "',";
        sql += "params='" + mockInfo.getParams() + "',";
        sql += "filePath='" + mockInfo.getPath() + "',";
        sql += "url='" + mockInfo.getUrl() + "' ";
        sql += "where id=" + mockInfo.getId();
        mJdbcTemplate.execute(sql);
        return FileUtil.writeFile(mockInfo.getPath(), mockInfo.getContent());
    }

    public List<MockInfo> getMockList(String username, String params, String url) {
        List<Map<String, Object>> maps = getMockByUsernameAndUrl(username, url);
        List<MockInfo> tempMocks = parseMockList(maps);
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();
        if (tempMocks != null && tempMocks.size() > 0) {

        }

        return mockInfos;
    }

    public boolean insertMock(MockInfo mockInfo) {
        return false;
    }

    public boolean deleteMock(int id, String filePath) {
        return false;
    }

    private List<Map<String, Object>> getMockByUsernameAndUrl(String username, String url) {
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();
        String sql = "select * from mock where ";
        if (url != null && url.length() > 0) {
            sql += "url='" + url + "' and";
        }

        if (username != null && username.length() > 0) {
            sql += "username='" + username + "'";
        }

        return mJdbcTemplate.queryForList(sql);
    }

    private List<MockInfo> parseMockList(List<Map<String, Object>> maps) {
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();
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

    /**
     * 得到两个参数的相似度
     *
     * @param params
     * @param targetParams
     * @return
     */
    private double getSimilarity(String params, String targetParams) {
        if (params == null || params.length() == 0) {
            return 0;
        }

        if (targetParams == null || targetParams.length() == 0) {
            return 1;
        }

        Map<String, Object> pMap = JSON.parseObject(params);

        return 0;
    }
}