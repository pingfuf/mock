package com.pf.mock;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Temp {
    public static void main(String[] argv) {
        String url = "aabb_cc_dd";
        System.out.println(url.replaceAll("_", "/"));
        List<Object> temp = new ArrayList<Object>();
        temp.add("a");
        temp.add("b");
        temp.add(0, "c");
        Object[] temp1 = temp.toArray();
        temp.clear();
        System.out.println(JSON.toJSONString(temp1));
    }
}
