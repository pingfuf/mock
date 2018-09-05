package com.pf.mock.controller;

import com.alibaba.fastjson.JSON;
import com.pf.mock.dao.MockDao;
import com.pf.mock.data.BaseResult;
import com.pf.mock.data.MockInfo;
import com.pf.mock.data.ReqParam;
import com.pf.mock.service.MockService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mock")
public class MockController extends BaseController {
    @Resource
    private MockService mockService;

    @RequestMapping("/getMockList")
    @ResponseBody
    public List<MockInfo> getMockList(@org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page) {
        return mockService.getMockList(page);
    }

    @RequestMapping("showMockList")
    public ModelAndView showMockList(@org.springframework.web.bind.annotation.RequestParam(defaultValue = "") String url, @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("mockList");
        List<MockInfo> mockInfos = mockService.getMockList(url, page);
        modelAndView.addObject("mockList", mockInfos);
        int size = mockInfos != null ? mockInfos.size() : 0;
        int pageSize = size / MockDao.PAGE_SIZE;
        if (size % MockDao.PAGE_SIZE != 0) {
            pageSize = pageSize + 1;
        }

        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentPage", page + 1);
        return modelAndView;
    }

    @RequestMapping("/getMock/**")
    @ResponseBody
    public String getMockInfoContent(HttpServletRequest request) {
        String url = request.getRequestURI().toString();
        String content = "";
        if (url.startsWith("/mock/mock/getMock")) {
            String uri = url.substring("/mock/mock/getMock/".length(), url.length());
            MockInfo mockInfo = mockService.getMockByUri(uri);
            content = mockInfo.getContent();
        }
        if (content.length() == 0) {
            content = url;
        }
        return content;
    }

    @RequestMapping("/getMockInfo")
    @ResponseBody
    public MockInfo getMockInfo(@org.springframework.web.bind.annotation.RequestParam String url) {
        MockInfo mockInfo = mockService.getMockByUri(url);
        return mockInfo;
    }

    @RequestMapping("/update")
    public ModelAndView updateMockInfo(@org.springframework.web.bind.annotation.RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("updateMock");
        MockInfo mockInfo = mockService.getMockById(id);
        modelAndView.addObject("mock", mockInfo);

        ArrayList<ReqParam> params = new ArrayList<ReqParam>();
//        try {
//            Map<String, Object> paramMap = JSON.parseObject(mockInfo.getParams());
//            if (paramMap != null && paramMap.size() > 0) {
//                for (String key : paramMap.keySet()) {
//                    System.out.println("key=" + key + ", value=" + paramMap.get(key));
//                    ReqParam param = new ReqParam();
//                    param.setKey(key);
//                    param.setValue(String.valueOf(paramMap.get(key)));
//
//                    params.add(param);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        ReqParam param1 = new ReqParam();
        param1.setKey("aa");
        param1.setValue("bb");
        params.add(param1);

        ReqParam param2 = new ReqParam();
        param2.setKey("aa");
        param2.setValue("bb");
        params.add(param2);
        modelAndView.addObject("paramTemp", param1);
        modelAndView.addObject("paramList", params);

        return modelAndView;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public BaseResult updateMockInfo(@org.springframework.web.bind.annotation.RequestParam String url,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "0")int id,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "")String username,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "")String params,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "")String path,
                                     @org.springframework.web.bind.annotation.RequestParam String content) {
        if (url == null) {
            return createResult(-1, null);
        }

        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(id);
        mockInfo.setUrl(url);
        mockInfo.setUsername(username);
        mockInfo.setParams(params);
        mockInfo.setPath(path);
        mockInfo.setContent(content);
        System.out.println(JSON.toJSONString(mockInfo));
        int code = -1;
        if (id > 0 || mockService.hasMockExisted(mockInfo) ) {
            code = mockService.updateMock(mockInfo) ? 0 : 1;
        } else {
            code = mockService.addMock(mockInfo) ? 0 : 1;
        }

        return createResult(code, null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult deleteMockInfo(@org.springframework.web.bind.annotation.RequestParam int id, @org.springframework.web.bind.annotation.RequestParam String path) {
        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(id);
        mockInfo.setPath(path);
        int code = mockService.deleteMock(mockInfo) ? 0 : 1;

        return createResult(code, null);
    }

    @RequestMapping("/userAgent")
    public ModelAndView tempUserAgent() {
        ModelAndView modelAndView = new ModelAndView("userAgent");

        return modelAndView;
    }

    @RequestMapping("/temp")
    @ResponseBody
    public BaseResult temp(HttpServletRequest request) {
        MockDao mockDao = new MockDao();
        List<MockInfo> mockInfos = mockDao.getMockByUrl("ee3", 0);

        return createResult(0, mockInfos);
    }
}