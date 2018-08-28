package com.pf.mock.controller;

import com.alibaba.fastjson.JSON;
import com.pf.mock.Config;
import com.pf.mock.data.ResInfo;
import com.pf.mock.service.ResService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fupingfu on 2017/9/7.
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");

    @Resource
    private ResService mService;

    @RequestMapping(value = "/getLatestRes")
    @ResponseBody
    public String getLatestRes(@RequestParam int type) {
        ResInfo resInfo = mService.getLatestRes(type);
        return JSON.toJSONString(createResult(0, resInfo));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam MultipartFile file, @RequestParam int type) {
        if (file == null) {
            return "null";
        }

        System.out.println(file.getName() + ", type=" + type);
        try {
            // 文件存放服务端的位置
            String fileName = file.getOriginalFilename();
            String date = dateFormat.format(new Date());
            if (type == ResInfo.STUDENT_H5_TYPE || type == ResInfo.TEACHER_H5_TYPE) {
                fileName = date + "_" + fileName;
            }
            // 写文件到服务器
            File serverFile = new File(Config.getResourceDir() + File.separator + fileName);
            file.transferTo(serverFile);
            mService.insertRes(type, date, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam int type) throws IOException {
        File file = getResponseFile(type);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        int i = is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<byte[]>(body, headers, statusCode);
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam int id) throws IOException {
        File file = getResponseFileById(id);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        int i = is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<byte[]>(body, headers, statusCode);
    }

    @RequestMapping("/fileList")
    public ModelAndView showResList() {
        ModelAndView modelAndView = new ModelAndView("fileList");
        modelAndView.addObject("files", mService.getResList());

        return modelAndView;
    }

    @RequestMapping("/downloadInfo")
    public ModelAndView downloadInfo(int id) {
        ModelAndView modelAndView = new ModelAndView("downloadFile");
        ResInfo resInfo = mService.getResInfoById(id);
        modelAndView.addObject("file", resInfo);
        String ip = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //返回IP地址
        String url = "http://" + ip;
        if (Config.isDebug()) {
            url = url + ":8080";
        }
        url = url + "/mock/file/downloadFile?id=" + id;
        modelAndView.addObject("url", url);
        return modelAndView;
    }

    @RequestMapping("/deleteFile")
    @ResponseBody
    public String deleteFile(int id) {
        mService.deleteRes(id);
        return "";
    }

    private File getResponseFile(int type) {
        ResInfo resInfo = mService.getLatestRes(type);
        File file = null;
        if (resInfo != null ) {
            file = new File(resInfo.getPath());
        }

        return file;
    }

    private File getResponseFileById(int id) {
        ResInfo resInfo = mService.getResInfoById(id);
        File file = null;
        if (resInfo != null ) {
            file = new File(resInfo.getPath());
        }

        return file;
    }
}
