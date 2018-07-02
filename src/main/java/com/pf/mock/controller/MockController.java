package com.pf.mock.controller;

import com.pf.mock.data.BaseResult;
import com.pf.mock.data.MockInfo;
import com.pf.mock.service.MockService;
import java.util.List;

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
    public List<MockInfo> getMockList() {
        return mockService.getMockList();
    }

    @RequestMapping("/showMockList")
    public String showMockList() {
        return "showMockList";
    }

    @RequestMapping("showMockListInfo")
    public ModelAndView showMockListInfo(@RequestParam(defaultValue = "") String mockName ) {
        ModelAndView modelAndView = new ModelAndView("mockList");
        modelAndView.addObject("mockList", mockService.getMockList(mockName));
        return modelAndView;
    }

    @RequestMapping("/content/**")
    @ResponseBody
    public String getMockInfoContent(HttpServletRequest request) {
        String url = request.getRequestURI().toString();
        String content = "";
        if (url.startsWith("/mock/mock/content")) {
            String uri = url.substring("/mock/mock/content/".length(), url.length());
            MockInfo mockInfo = mockService.getMockByUri(uri);
            content = mockInfo.getContent();
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
    public BaseResult updateMockInfo(@RequestParam String url, @RequestParam String content) {
        MockInfo mockInfo = new MockInfo();
        mockInfo.setUrl(url);
        mockInfo.setContent(content);
        int code = mockService.updateMock(mockInfo) ? 0 : 1;
        return createResult(code, null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult deleteMockInfo(@RequestParam String url) {
        int code = mockService.deleteMock(url) ? 0 : 1;
        return createResult(code, null);
    }
}