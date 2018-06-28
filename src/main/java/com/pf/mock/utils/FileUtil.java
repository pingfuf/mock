package com.pf.mock.utils;

import com.pf.mock.Config;
import com.pf.mock.data.RnVersion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fupingfu on 2017/7/28.
 */
public class FileUtil {
    /**
     * 读取File
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        File file = new File(fileName);
        String text = "";
        if (file.canRead()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    text += tempString;
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
        return text;
    }

    public static boolean writeFile(String fileName, String content) {
        boolean flag = false;
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(content);
            writer.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static List<String> getMockFileList(String path) {
        mockFiles = new ArrayList<String>();
        traverseFolder(path);
        return mockFiles;
    }

    private static List<String> mockFiles;
    public static void traverseFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        File[] files = file.listFiles();
        if (files.length == 0) {
            return;
        }
        for (File file2 : files) {
            if (file2.isDirectory()) {
                traverseFolder(file2.getAbsolutePath());
            } else {
                mockFiles.add(file2.getAbsolutePath());
            }
        }
    }
}
