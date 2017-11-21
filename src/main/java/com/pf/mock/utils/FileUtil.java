package com.pf.mock.utils;

import com.pf.mock.Config;
import com.pf.mock.data.RnVersion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public static void writeFile(String fileName, String content) {

    }


}
