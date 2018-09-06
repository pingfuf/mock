package com.pf.mock;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by fupingfu on 2017/7/27.
 */
public class Config {
    public static final String SERVER_PATH = System.getProperty("mock.root");
    private static final String ROOT_DIR = initMockPath();

    public static final String DATA_DIR = "data";

    public static final String RES_DIR = "res";

    public static String serverIp;

    /**
     * 得到数据的根路径
     *
     * @return mock数据的根路径
     */
    public static String getMockDataDir() {
        String path = ROOT_DIR + File.separator + DATA_DIR;
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
        String path = ROOT_DIR + File.separator + RES_DIR;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    public static boolean isDebug() {
        String path = "/Users/pingfu/mock";
        File file = new File(path);
        if (!file.canRead()) {
            return false;
        }

        return true;
    }

    public static String getServerIp() {
        if (serverIp == null) {
            try {
                InetAddress address = InetAddress.getLocalHost();
                serverIp = address.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return serverIp;
    }

    public static String getServerUrl() {
        String url = "http://" + getServerIp();
        if (isDebug()) {
            url = url + ":8080";
        }
        url = url + "/mock";

        return url;
    }
}
