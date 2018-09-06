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
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<MockInfo> getMockList(@RequestParam(defaultValue = "0") int page) {
        return mockService.getMockList(page);
    }

    @RequestMapping("showMockList")
    public ModelAndView showMockList(@RequestParam(defaultValue = "") String url) {
        ModelAndView modelAndView = new ModelAndView("mockList");
        List<MockInfo> mockInfos = mockService.getMockList(url);
        modelAndView.addObject("mockList", mockInfos);
        int size = mockInfos != null ? mockInfos.size() : 0;
        int pageSize = size / MockDao.PAGE_SIZE;
        if (size % MockDao.PAGE_SIZE != 0) {
            pageSize = pageSize + 1;
        }

        modelAndView.addObject("pageSize", pageSize);
        return modelAndView;
    }

    @RequestMapping("/getMock/**")
    @ResponseBody
    public String getMockInfoContent(HttpServletRequest request,
                                     @RequestParam(required = false, defaultValue = "") String username,
                                     @RequestParam(required = false, defaultValue = "") String param) {
        String url = request.getRequestURI().toString();
        String content = "";
        MockInfo mockInfo = null;
        if (url.startsWith("/mock/mock/getMock")) {
            String uri = url.substring("/mock/mock/getMock/".length(), url.length());
            mockInfo = mockService.getSimilarlyMock(username, param, uri);
            if (mockInfo != null) {
                content = mockInfo.getContent();
            }
        }
        if (content.length() == 0) {
            content = JSON.toJSONString(mockInfo);
        }
        return content;
    }

    @RequestMapping("/getMockInfo")
    @ResponseBody
    public MockInfo getMockInfo(@RequestParam String url) {
        MockInfo mockInfo = mockService.getMockByUri(url);
        return mockInfo;
    }

    @RequestMapping("/update")
    public ModelAndView updateMockInfo(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("updateMock");
        MockInfo mockInfo = mockService.getMockById(id);
        modelAndView.addObject("mock", mockInfo);

        ArrayList<ReqParam> params = new ArrayList<ReqParam>();
        ReqParam param;
        try {
            Map<String, Object> paramMap = JSON.parseObject(mockInfo.getParams());
            if (paramMap != null && paramMap.size() > 0) {
                for (String key : paramMap.keySet()) {
                    param = new ReqParam();
                    param.setKey(key);
                    param.setValue(String.valueOf(paramMap.get(key)));

                    System.out.println("key=" + param.getKey() + ", value=" + param.getValue());
                    params.add(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("paramList", params);

        return modelAndView;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public BaseResult updateMockInfo(@RequestParam String url,
                                     @RequestParam(required = false, defaultValue = "0")int id,
                                     @RequestParam(required = false, defaultValue = "")String username,
                                     @RequestParam(required = false, defaultValue = "")String params,
                                     @RequestParam(required = false, defaultValue = "")String path,
                                     @RequestParam String content) {
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
    public BaseResult deleteMockInfo(@RequestParam int id, @RequestParam String path) {
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
        List<MockInfo> mockInfos = mockDao.getMockByUrl("ee3");

        return createResult(0, mockInfos);
    }
}