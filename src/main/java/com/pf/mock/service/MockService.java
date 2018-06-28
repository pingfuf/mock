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

    public List<MockInfo> getMockListByUsername(String username) {
        return getMockList();
    }

    public List<MockInfo> getMockList() {
        List<MockInfo> mockInfos = new ArrayList<MockInfo>();
        List<String> files = FileUtil.getMockFileList(Config.ROOT_DIR + File.separator + "server");
        if (files == null || files.size() == 0) {
            return mockInfos;
        }

        int i = 0;
        for (String filePath : files) {
            File file = new File(filePath);
            MockInfo mockInfo = parseMock(filePath);
            if (mockInfo != null) {
                mockInfos.add(mockInfo);
            }
        }
        return mockInfos;
    }

    /**
     * 获取mock数据
     *
     * @param filePath 文件路径
     * @return mock数据
     */
    public MockInfo getMockByUrl(String filePath) {
        return parseMock(filePath);
    }

    public boolean updateMock(MockInfo mockInfo) {
        if (mockInfo == null) {
            return false;
        }

        String path = Config.ROOT_DIR + File.separator + "server" + File.separator + mockInfo.getPath();
        return FileUtil.writeFile(path, mockInfo.getContent());
    }

    private MockInfo parseMock(String filePath) {
        if (filePath == null) {
            return null;
        }

        MockInfo mockInfo = new MockInfo();
        String path = Config.ROOT_DIR + File.separator + "server" + File.separator + filePath;
        mockInfo.setId(0);
        mockInfo.setUrl(filePath.replaceAll("_", "/"));
        mockInfo.setContent(FileUtil.readFile(path));
        mockInfo.setPath(filePath);
        mockInfo.setUsername("");

        return mockInfo;
    }


}
