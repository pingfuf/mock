package com.pf.mock.service;

import com.pf.mock.Config;
import com.pf.mock.dao.MockDao;
import com.pf.mock.data.MockInfo;
import com.pf.mock.utils.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fupingfu on 2017/7/28.
 */
@Service
public class MockService {
    private MockDao mockDao = new MockDao();

    /**
     * 得到所有的mock数据列表
     *
     * @return 本地的mock数据列表
     */
    public List<MockInfo> getMockList(int page) {
        return mockDao.getMockList(page);
    }

    public int getMockSize() {
        return mockDao.getMockSize();
    }

    /**
     * 得到相似的mock数据
     *
     * @param uri mock的uri
     * @return 和uri相似的mock数据列表
     */
    public List<MockInfo> getMockList(String uri) {
        return mockDao.getMockByUrl(uri);
    }

    /**
     * 获取mock数据
     *
     * @param uri 文件路径
     * @return mock数据
     */
    public MockInfo getMockByUri(String uri) {
        return mockDao.getMockByUrl(uri).get(0);
    }

    public MockInfo getMockById(int id) {
        return mockDao.getMockById(id);
    }

    public MockInfo getSimilarlyMock(String username, String param, String uri) {
        return mockDao.getMockInfo(username, param, uri);
    }

    /**
     * 更新本地mock
     *
     * @param mockInfo mock数据
     * @return 是否更新成功
     */
    public boolean updateMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            return false;
        }
        String filePath = mockInfo.getPath();
        if (filePath == null || filePath.length() == 0 || !(new File(filePath).exists())) {
            filePath = Config.getMockDataDir() + File.separator + mockInfo.getUsername() + "_" + System.currentTimeMillis();
            mockInfo.setPath(filePath);
        }
        mockDao.updateMock(mockInfo);

        return FileUtil.writeFile(filePath, mockInfo.getContent());
    }

    public boolean addMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            return false;
        }
        String filePath = mockInfo.getPath();
        if (filePath == null || filePath.length() == 0) {
            filePath = mockInfo.getUsername() + System.currentTimeMillis();
            mockInfo.setPath(filePath);
        }
        mockDao.addMock(mockInfo);

        return FileUtil.writeFile(filePath, mockInfo.getContent());
    }

    public boolean hasMockExisted(MockInfo mockInfo) {
        return mockDao.hasMockExisted(mockInfo);
    }

    public boolean deleteMock(MockInfo mockInfo) {
        return new File(mockInfo.getPath()).delete() && mockDao.deleteMock(mockInfo.getId());
    }

    private MockInfo parseMockByUri(String uri) {
        if (uri == null || uri.length() == 0) {
            return new MockInfo();
        }

        return parseMock(uri.replaceAll("/", "_"));
    }

    /**
     * 根据mock的uri，获取本地mock数据
     *
     * @param path  uri转path
     * @return mock数据
     */
    private MockInfo parseMock(String path) {
        if (path == null || path.length() == 0) {
            return new MockInfo();
        }

        String url = path.replaceAll("_", "/");
        System.out.println("parseMock path=" + path + ", url=" + url);
        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(0);
        mockInfo.setUrl(url);
        mockInfo.setContent(FileUtil.readFile(getLocalPath(path)));
        mockInfo.setPath(path);
        mockInfo.setUsername("");

        return mockInfo;
    }

    private String getLocalPath(String path) {
        String localPath = "";
        if (path != null) {
            localPath = Config.ROOT_DIR + File.separator + "server" + File.separator + path;
        }

        return localPath;
    }
}
