package com.pf.mock;

import java.io.File;

/**
 * Created by fupingfu on 2017/7/27.
 */
public class Config {
    public static final String SERVER_PATH = System.getProperty("mock.root");

    public static final String BASE_DIR = "data";

    public static final String ROOT_DIR = initMockPath();

    /**
     * 得到数据的根路径
     *
     * @return mock数据的根路径
     */
    public static String getBaseRootDirPath() {
        File file = new File(SERVER_PATH);
        if (!file.canRead()) {
            return null;
        }

        File parent = file.getParentFile();
        if (parent == null || !parent.canRead()) {
            return null;
        }

        String path = parent.getAbsolutePath() + File.separator + BASE_DIR;
        File rootDir = new File(path);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        return path;
    }

    public static String getReactVersionFilePath() {
        return ROOT_DIR + File.separator + "version.txt";
    }

    private static String initMockPath() {
        String path = "/Users/pingfu/mock";
        File file = new File(path);
        if (!file.canRead()) {
            path = "D:\\mock";
        }

        return path;
    }

    public static String getDatabaseUrl() {
        return ROOT_DIR + File.separator + "mock.db";
    }

    public static String getResourceDir() {
        String path = ROOT_DIR + File.separator + "res";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }
}
