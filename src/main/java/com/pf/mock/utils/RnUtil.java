package com.pf.mock.utils;

import com.pf.mock.Config;
import com.pf.mock.data.RnVersion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RnUtil {
    private static HashMap<Integer, RnVersion> versionMap;
    private static RnVersion latestVersion;

    public static HashMap<Integer, RnVersion> getVersionMap() {
        if (versionMap != null) {
            return versionMap;
        }
        versionMap = new HashMap<Integer, RnVersion>();
        System.out.println("filePath = " + Config.getReactVersionFilePath());
        File file = new File(Config.getReactVersionFilePath());
        if (file.canRead()) {
            System.out.println("filePath = " + Config.getReactVersionFilePath());
            BufferedReader reader = null;
            try {
                System.out.println("以行为单位读取文件内容，一次读一整行：");
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    RnVersion version = parseVersion(tempString);
                    versionMap.put(version.getCode(), version);
                    if (latestVersion == null) {
                        latestVersion = version;
                    }

                    if (latestVersion == null || latestVersion.getCode() < version.getCode()) {
                        latestVersion = version;
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }

        return versionMap;
    }

    public static RnVersion getLatestVersion() {
        if (latestVersion == null) {
            getVersionMap();
        }
        return latestVersion;
    }

    private static RnVersion parseVersion(String line) {
        RnVersion version = new RnVersion();
        if (line != null && line.length() > 0) {
            String[] array = line.split(" ");
            version.setCode(Integer.parseInt(array[0]));
            if (array.length > 1) {
                version.setName(array[1]);
            }

            if (array.length > 2) {
                version.setTag(array[2]);
            }

            if (array.length > 3) {
                version.setDate(array[3]);
            }
        }

        return version;
    }

    private static String versionToString(RnVersion version) {
        return "";
    }
}
