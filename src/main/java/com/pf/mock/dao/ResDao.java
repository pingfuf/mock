package com.pf.mock.dao;

import com.pf.mock.Config;
import com.pf.mock.SqliteManager;
import com.pf.mock.data.ResInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResDao {
    private JdbcTemplate mJdbcTemplate = SqliteManager.getInstance().getJdbcTemplate();
    public static final int PAGE_SIZE = 50;

    public List<ResInfo> getResListByType(int type) {
        String sql = "select * from tb_res where type=" + type + " order by date asc";
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);

        return parseResList(maps);
    }

    public ResInfo getLatestRes(int type) {
        String sql = "select * from tb_res where type=" + type + " order by date asc limit 1";
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        List<ResInfo> resInfos = parseResList(maps);
        ResInfo resInfo = null;
        if (resInfos != null && resInfos.size() > 0) {
            resInfo = resInfos.get(0);
        }

        return resInfo;
    }

    public List<ResInfo> getResList() {
        String sql = "select * from tb_res ORDER BY DATE limit 50";
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);

        return parseResList(maps);
    }

    public void insertRes(int type, String date, String fileName) {
        String sql = "insert into tb_res(type, date, path, name) values(%s, \"%s\", \"%s\", \"%s\")";
        String filePath = Config.getResourceDir() + File.separator + fileName;
        sql = String.format(sql, type, date, filePath, fileName);
        mJdbcTemplate.execute(sql);
    }

    public void deleteRes(int id) {
        String sql = "delete from tb_res where id=" + id;
        mJdbcTemplate.execute(sql);
    }

    public ResInfo getResById(int id) {
        String sql = "select * from tb_res where id=" + id;
        List<Map<String, Object>> maps = mJdbcTemplate.queryForList(sql);
        List<ResInfo> resInfos = parseResList(maps);
        ResInfo resInfo = null;
        if (resInfos != null && resInfos.size() > 0) {
            resInfo = resInfos.get(0);
        }

        return resInfo;
    }

    private List<ResInfo> parseResList(List<Map<String, Object>> maps) {
        List<ResInfo> resInfos = new ArrayList<ResInfo>();
        if (maps != null && maps.size() > 0) {
            for (Map<String, Object> map : maps) {
                if (map != null) {
                    resInfos.add(parseRes(map));
                }
            }
        }
        return resInfos;
    }

    private ResInfo parseRes(Map<String, Object> map) {
        ResInfo resInfo = new ResInfo();
        resInfo.setId(Integer.valueOf(map.get("id").toString()));
        resInfo.setType(Integer.valueOf(map.get("type").toString()));
        resInfo.setDate(String.valueOf(map.get("date")));
        resInfo.setPath(String.valueOf(map.get("path")));
        resInfo.setName(String.valueOf(map.get("name")));
        resInfo.setTypeName(getTypeName(resInfo.getType()));
        return resInfo;
    }

    private String getTypeName(int type) {
        String name = "";
        switch (type) {
            case ResInfo.APK_TYPE:
                name = ResInfo.APK_TYPE_NAME;
                break;
            case ResInfo.IPA_TYPE:
                name = ResInfo.IPA_TYPE_NAME;
                break;
            case ResInfo.STUDENT_H5_TYPE:
                name = ResInfo.STUDENT_RES_TYPE;
                break;
            case ResInfo.TEACHER_H5_TYPE:
                name = ResInfo.TEACHER_RES_TYPE;
            default:
                break;
        }

        return name;
    }
}
