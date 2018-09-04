package com.pf.mock.controller;

import com.alibaba.fastjson.JSON;
import com.pf.mock.dao.MockDao;
import com.pf.mock.data.BaseResult;
import com.pf.mock.data.MockInfo;
import com.pf.mock.service.MockService;
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
    public ModelAndView showMockList(@RequestParam(defaultValue = "") String url, @RequestParam(defaultValue = "0") int page) {
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
    public MockInfo getMockInfo(@RequestParam String url) {
        MockInfo mockInfo = mockService.getMockByUri(url);
        return mockInfo;
    }

    @RequestMapping("/update")
    public ModelAndView updateMockInfo(@RequestParam(defaultValue = "", required = false) String url) {
        ModelAndView modelAndView = new ModelAndView("updateMock");
        MockInfo mockInfo = mockService.getMockByUri(url);
        modelAndView.addObject("mock", mockInfo);

        return modelAndView;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public BaseResult updateMockInfo(@RequestParam String url,
                                     @RequestParam(required = false, defaultValue = "0")int id,
                                     @RequestParam(required = false, defaultValue = "")String username,
                                     @RequestParam(required = false, defaultValue = "")String params,
                                     @RequestParam String content) {
        if (url == null) {
            return createResult(-1, null);
        }

        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(id);
        mockInfo.setUrl(url);
        mockInfo.setContent(content);
        mockInfo.setUsername(username);
        if (params != null && params.length() > 0) {
            Map<String, Object> paramMap = JSON.parseObject(params);
            mockInfo.setParams(paramMap);
        }

        int code = -1;
        if (mockService.hasMockExisted(mockInfo)) {
            code = mockService.updateMock(mockInfo) ? 0 : 1;
        } else {
            code = mockService.addMock(mockInfo) ? 0 : 1;
        }

        return createResult(code, null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult deleteMockInfo(@RequestParam String url) {
        int code = mockService.deleteMock(url) ? 0 : 1;
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