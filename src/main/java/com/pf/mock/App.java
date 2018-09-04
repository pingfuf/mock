package com.pf.mock;

import com.alibaba.fastjson.JSON;
import com.pf.mock.data.MockInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
    public static void main(String[] argv) {
//        String url = "aabb_cc_dd";
//        System.out.println(url.replaceAll("_", "/"));
//        List<Object> temp = new ArrayList<Object>();
//        temp.add("a");
//        temp.add("b");
//        temp.add(0, "c");
//        Object[] temp1 = temp.toArray();
//        temp.clear();
//        System.out.println(JSON.toJSONString(temp1));
//
//
        MockInfo mockInfo = new MockInfo();
        mockInfo.setId(1);
        mockInfo.setUsername("aa");
        mockInfo.setPath("bb");
        mockInfo.setUrl("cc");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("aa", "aab");
        paramMap.put("cc", 2);
        paramMap.put("dd", "{\"bb\": 1}");
        mockInfo.setParams(paramMap);

        String json = JSON.toJSONString(mockInfo);
        Map<String, Object> tempMap1 = JSON.parseObject(json);
        Map<String, Object> tempMap2 = JSON.parseObject(json);
        for (String key : tempMap1.keySet()) {
            System.out.print("key = " + key + ", " + tempMap1.get(key) + ", ");
            System.out.println(tempMap1.get(key).equals(tempMap2.get(key)));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }
}
