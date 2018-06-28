package com.pf.mock.controller;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public ModelAndView showMockListInfo() {
        ModelAndView modelAndView = new ModelAndView("mockList");
        modelAndView.addObject("mockList", mockService.getMockList());
        return modelAndView;
    }

    @RequestMapping("/content/**")
    @ResponseBody
    public String getMockInfoContent(HttpServletRequest request, HttpServletResponse resp, HttpSession session1) {
        String url = request.getRequestURI().toString();
        String content = "";
        if (url.startsWith("/mock/mock/content")) {
            String path = url.substring("/mock/mock/content/".length(), url.length());
            MockInfo mockInfo = mockService.getMockByUrl(path);
            content = mockInfo.getContent();
        }
        return content;
    }

    @RequestMapping("/getMockInfo")
    @ResponseBody
    public MockInfo getMockInfo(@RequestParam String path) {
        MockInfo mockInfo = mockService.getMockByUrl(path);
        return mockInfo;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateMockInfo(@RequestParam String path, @RequestParam String content) {
        return "";
    }
}
