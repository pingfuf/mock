package com.pf.mock.utils;

import com.pf.mock.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandUtil {
    public static String tempCommand() {
        String command = "cmd /c F:&&cd F:\\react && git status";
        System.out.println(command);
        Runtime runtime = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        try {
            Process process = runtime.exec(command);
            BufferedReader  bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
