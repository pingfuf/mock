package com.pf.mock.dao;

import com.alibaba.fastjson.JSON;
import com.pf.mock.SqliteManager;
import com.pf.mock.data.MockInfo;
import com.pf.mock.utils.FileUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by fupingfu on 2017/7/28.
 */
public class MockDao {
    private JdbcTemplate mJdbcTemplate = SqliteManager.getInstance().getJdbcTemplate();
    public static final int PAGE_SIZE = 15;

    public void addMock(MockInfo mockInfo) {
        String sql = "insert into mock(username, params, filePath, url) values('%s', '%s', '%s', '%s')";
        sql = String.format(sql, mockInfo.getUsername(), mockInfo.getParams(), mockInfo.getPath(), mockInfo.getUrl());

        mJdbcTemplate.execute(sql);
    }

    public List<MockInfo> getMockByUrl(String url) {
        String sql = "select * from mock ";
        if (url != null && url.length() > 0 ) {
            sql = sql + "where url='" + url + "'";
        }
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        return parseMockList(maps);
    }

    public MockInfo getMockById(int id) {
        String sql = "select * from mock where id=" + id;

        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        if (maps != null && maps.size() > 0) {
            return parseMockList(maps).get(0);
        }
        return null;
    }

    public int getMockSize() {
        String sql = "select count(*) as num from mock";
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        int size = 0;
        if (maps != null && maps.size() > 0) {
            size = Integer.valueOf(maps.get(0).get("num").toString());
        }

        return size;
    }

    public List<MockInfo> getMockList(int page) {
        String sql = String.format("select * from mock limit %s offset %s", PAGE_SIZE, page * PAGE_SIZE);
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);

        return parseMockList(maps);
    }

    public void updateMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            System.out.println("update mock = null");
            return;
        }

        String sql = "update mock set ";
        sql += "username='" + mockInfo.getUsername() + "',";
        sql += "params='" + mockInfo.getParams() + "',";
        sql += "filePath='" + mockInfo.getPath() + "',";
        sql += "url='" + mockInfo.getUrl() + "' ";
        sql += "where id=" + mockInfo.getId();
        System.out.println("update mock = "  + sql);

        mJdbcTemplate.execute(sql);
    }

    public boolean hasMockExisted(MockInfo mockInfo) {
        boolean flag = false;
        if (mockInfo != null && mockInfo.getUrl() != null && mockInfo.getUrl().length() > 0) {
            String sql = "select * as num from mock where url='";
            sql += mockInfo.getUrl() + "'";
            if (mockInfo.getUsername() != null && mockInfo.getUsername().length() > 0) {
                sql += " and username='" + mockInfo.getUsername() + "'";
            }
            if (mockInfo.getParams() != null) {
                sql += " and params='" + JSON.toJSONString(mockInfo.getParams().toString()) + "'";
            }

            List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
            if (maps != null && maps.size() > 0) {
                List<MockInfo> mockInfos = parseMockList(maps);
                if (mockInfos.size() > 0) {
                    mockInfo.setId(mockInfos.get(0).getId());
                    flag = true;
                }
            }
        }

        return flag;
    }

    public List<MockInfo> getMockList(String username, String params, String url) {
        List<Map<String, Object>> maps = getMockByUsernameAndUrl(username, url);
        List<MockInfo> mockInfos = parseMockList(maps);
        List<MockInfoTemp> tempMocks = new ArrayList<MockInfoTemp>();
        if (mockInfos != null && mockInfos.size() > 0) {
            for(MockInfo mockInfo : mockInfos) {
                float similarity = getSimilarity(mockInfo.getUsername(), mockInfo.getParams(), username, params);
                if (similarity > 0.5f) {
                    MockInfoTemp temp = new MockInfoTemp();
                    temp.similarity = similarity;
                    temp.mockInfo = mockInfo;
                    tempMocks.add(temp);
                }
            }
        }
        if (tempMocks.size() > 0) {
            Collections.sort(tempMocks, new Comparator<MockInfoTemp>() {
                @Override
                public int compare(MockInfoTemp o1, MockInfoTemp o2) {
                    int i = 0;
                    if (o2.similarity - o1.similarity > 0) {
                        i = 1;
                    } else if (o2.similarity - o1.similarity < 0) {
                        i = -1;
                    }

                    //逆序排序
                    return -i;
                }
            });

            mockInfos.clear();
            for (MockInfoTemp temp : tempMocks) {
                mockInfos.add(temp.mockInfo);
            }
        }


        return mockInfos;
    }

    public MockInfo getMockInfo(String username, String params, String url) {
        List<MockInfo> mockInfos = getMockList(username, params, url);
        MockInfo mockInfo = null;
        if (mockInfos != null && mockInfos.size() > 0) {
            mockInfo = mockInfos.get(0);
        }

        return mockInfo;
    }

    public void deleteMock(int id) {
        String sql = "delete from mock where id=" + id;
        mJdbcTemplate.execute(sql);
    }

    private List<Map<String, Object>> getMockByUsernameAndUrl(String username, String url) {
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();
        String sql = "select * from mock where url='" + url + "'";

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

        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(Integer.valueOf(map.get("id").toString()));
        mockInfo.setUsername(String.valueOf(map.get("username")));
        mockInfo.setParams(String.valueOf(map.get("params")));
        mockInfo.setUrl(String.valueOf(map.get("url")));
        mockInfo.setPath(String.valueOf(map.get("filePath")));

        if (mockInfo.getPath() != null) {
            mockInfo.setContent(FileUtil.readFile(mockInfo.getPath()));
        }

        return mockInfo;
    }

    /**
     * 得到两个参数的相似度
     * 计算规则如下：如果
     *
     * @param username
     * @param params
     * @param targetUsername
     * @param targetParams
     * @return
     */
    private float getSimilarity(String username, String params, String targetUsername, String targetParams) {
        if (username == null && params == null) {
            return 0;
        }

        float similarity = 0;
        if (username != null && username.equalsIgnoreCase(targetUsername)) {
            similarity = similarity + 1.0f;
        }

        Map<String, Object> paramMap = null;
        Map<String, Object> targetMap = null;
        try {
            paramMap = JSON.parseObject(params);
            targetMap = JSON.parseObject(targetParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (paramMap != null && paramMap.size() > 0 && targetMap != null && targetMap.size() > 0) {
            int size = paramMap.size();
            float score = 0;
            for (String key : targetMap.keySet()) {
                if (paramMap.containsKey(key)) {
                    score += 0.5f;

                    if (paramMap.get(key) != null && paramMap.get(key).equals(targetMap.get(key))) {
                        score += 0.5f;
                    }
                }
            }
            similarity = similarity + (score / (float)size);
        }

        return similarity;
    }

    private static class MockInfoTemp {
        MockInfo mockInfo;
        float similarity;
    }
}