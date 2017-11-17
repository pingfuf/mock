package com.pf.mock.controller;

import com.alibaba.fastjson.JSON;
import com.pf.mock.Config;
import com.pf.mock.data.BaseResult;
import com.pf.mock.data.RnVersion;
import com.pf.mock.service.ReactService;
import com.pf.mock.utils.CommandUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public @ResponseBody String getLatestVersion() {
        RnVersion version = mService.getLatestVersion();
        BaseResult<RnVersion> result = createResult(0, version);

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
        System.out.println(String.format("phoneType=%s, localVersion=%s, requestVersion=%s", phoneType, localVersion, requestVersion));
        File file = getResponseFile(phoneType, localVersion, requestVersion);
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

    private File getResponseFile(String phoneType, String localVersion, String requestVersion) {
        String path = Config.RN_ROOT_DIR + File.separator + "update";
        String type = phoneType.equals("andr")? "android" : "ios";
        if (localVersion == null || localVersion.length() < 1) {
            path += File.separator + type + "_" + requestVersion + ".zip";
        } else {
            path += File.separator + type + "_" + requestVersion + "_" + localVersion + ".zip";
        }

        return new File(path);
    }
}
