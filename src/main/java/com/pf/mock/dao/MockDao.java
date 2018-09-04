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

    public List<MockInfo> getMockByUrl(String url, int page) {
        String sql = "select * from mock ";
        if (url != null && url.length() > 0 ) {
            sql = sql + "where url='" + url + "'";
        }
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        return parseMockList(maps);
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

    public boolean updateMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            return false;
        }

        String sql = "update mock set ";
        sql += "username='" + mockInfo.getUsername() + "',";
        sql += "params='" + JSON.toJSONString(mockInfo.getParams()) + "',";
        sql += "filePath='" + mockInfo.getPath() + "',";
        sql += "url='" + mockInfo.getUrl() + "' ";
        sql += "where id=" + mockInfo.getId();
        mJdbcTemplate.execute(sql);
        return FileUtil.writeFile(mockInfo.getPath(), mockInfo.getContent());
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

            sql += mockInfo.getUsername();
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

    public boolean addMock(MockInfo mockInfo) {
        return false;
    }

    public List<MockInfo> getMockList(String username, String params, String url) {
        List<Map<String, Object>> maps = getMockByUsernameAndUrl(username, url);
        List<MockInfo> mockInfos = parseMockList(maps);
        List<MockInfoTemp> tempMocks = new ArrayList<MockInfoTemp>();
        if (mockInfos != null && mockInfos.size() > 0) {
            for(MockInfo mockInfo : mockInfos) {
                float similarity = getSimilarity(mockInfo, username, params);
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

    public boolean insertMock(MockInfo mockInfo) {
        return false;
    }

    public boolean deleteMock(int id, String filePath) {
        return false;
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
        if (map.get("params") != null) {
            String params = map.get("params").toString();
            Map<String, Object> paramMap = null;
            try {
                paramMap = JSON.parseObject(map.get("params").toString());
            } catch (Exception e) {
//                e.printStackTrace();
            }
            mockInfo.setParams(paramMap);
        }
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
     * @param mockInfo
     * @param targetUsername
     * @param targetParams
     * @return
     */
    private float getSimilarity(MockInfo mockInfo, String targetUsername, String targetParams) {
        if (mockInfo == null) {
            return 0;
        }

        float similarity = 0;
        if (mockInfo.getUsername() != null && mockInfo.getUsername().equalsIgnoreCase(targetUsername)) {
            similarity = similarity + 1.0f;
        }

        if (mockInfo.getParams() != null && mockInfo.getParams().size() > 0) {
            int size = mockInfo.getParams().size();
            Map<String, Object> targetMap = JSON.parseObject(targetParams);
            float score = 0;
            if (targetMap != null) {
                for (String key : targetMap.keySet()) {
                    if (mockInfo.getParams().containsKey(key)) {
                        score += 0.5f;

                        if (mockInfo.getParams().get(key) != null && mockInfo.getParams().get(key).equals(targetMap.get(key))) {
                            score += 0.5f;
                        }
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