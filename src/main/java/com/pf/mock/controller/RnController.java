package com.pf.mock.controller;

import com.alibaba.fastjson.JSON;
import com.pf.mock.Config;
import com.pf.mock.data.BaseHyResult;
import com.pf.mock.data.BaseResult;
import com.pf.mock.data.RnVersion;
import com.pf.mock.data.UpdateInfo;
import com.pf.mock.service.ReactService;
import com.pf.mock.utils.CommandUtil;
import com.pf.mock.utils.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/rn")
public class RnController extends BaseController {
    @Resource
    private ReactService mService;

    @RequestMapping("/latest")
    @ResponseBody
    public String getLatestVersion(@RequestParam String phoneType) {
        RnVersion version = mService.getLatestVersion();
        BaseResult<RnVersion> result = createResult(getSuccessCode(phoneType), version);

        return JSON.toJSONString(result);
    }

    @RequestMapping("/hy")
    @ResponseBody
    public String getHyVersion() {
        RnVersion version = mService.getLatestVersion();
        BaseHyResult<RnVersion> result = new BaseHyResult<RnVersion>();
        result.setErrorCode("G_10000");
        result.setStatus(1);
        result.setMessage("");
        result.setData(mService.getHyVersion());
        return JSON.toJSONString(result);
    }

    @RequestMapping("/cm")
    public @ResponseBody String tempCommand() {
        return CommandUtil.tempCommand();
    }

    @RequestMapping("/downloadLatest")
    public ResponseEntity<byte[]> download(@RequestParam String phoneType, @RequestParam String localVersion) throws IOException {
        File file = getResponseFile(phoneType, localVersion, mService.getLatestVersion().getName());
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        return entity;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String phoneType, @RequestParam String localVersion, @RequestParam String requestVersion) throws IOException {
        if (requestVersion == null || requestVersion.length() < 1) {
            requestVersion = mService.getLatestVersion().getName();
        }

        File file = getResponseFile(phoneType, localVersion, requestVersion);
        System.out.println(String.format("phoneType=%s, localVersion=%s, requestVersion=%s, file=%s",
                phoneType, localVersion, requestVersion, file.getName()));
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        return entity;
    }

    @RequestMapping("/downloadHy")
    public ResponseEntity<byte[]> downloadHyResource() throws IOException {
        File file = new File(Config.ROOT_DIR + File.separator + "hy.zip");
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        return entity;
    }

    @RequestMapping("/updateInfo")
    @ResponseBody
    public String getUpdateInfo(@RequestParam String phoneType, @RequestParam String localVersion) {
        if (localVersion == null || localVersion.length() < 1) {
            return "{}";
        }
        String path = Config.ROOT_DIR + File.separator + "update";
        String type = "andr".equals(phoneType) ? "android" : "ios";
        path = path + File.separator + type + "_";
        path = path + mService.getLatestVersion().getName();
        path = path + "_" + localVersion + ".txt";

        System.out.println(path);
        String updateInfoStr = FileUtil.readFile(path);
        UpdateInfo updateInfo = JSON.parseObject(updateInfoStr, UpdateInfo.class);
        BaseResult<UpdateInfo> result = createResult(getSuccessCode(phoneType), updateInfo);

        return JSON.toJSONString(result);
    }

    private File getResponseFile(String phoneType, String localVersion, String requestVersion) {
        String path = Config.ROOT_DIR + File.separator + "update";
        String type = "andr".equals(phoneType)? "android" : "ios";
        if (localVersion == null || localVersion.length() < 1) {
            path += File.separator + type + "_" + requestVersion + ".zip";
        } else {
            path += File.separator + type + "_" + requestVersion + "_" + localVersion + ".zip";
        }

        return new File(path);
    }

    private int getSuccessCode(String phoneType) {
        int code = 0;
        if (!"andr".equals(phoneType)) {
            return 100000;
        }

        return code;
    }
}
