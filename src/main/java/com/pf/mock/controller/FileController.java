package com.pf.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by fupingfu on 2017/9/7.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/temp")
    @ResponseBody
    public String temp() {
        return "temp";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public  String uploadFile(@RequestParam("audio") MultipartFile file, @RequestParam("score") int score) {
        System.out.println("score = " + score);
        if (file == null) {
            return "null";
        }
        try {
            // 文件存放服务端的位置
            String rootPath = "d:/tmp";
            File dir = new File(rootPath + File.separator + "tmpFiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 写文件到服务器
            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            file.transferTo(serverFile);
            return "You successfully uploaded file=" +  file.getOriginalFilename();
        } catch (Exception e) {

        }
        return "{}";
    }
}
