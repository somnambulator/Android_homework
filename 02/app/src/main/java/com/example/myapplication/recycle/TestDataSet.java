package com.example.myapplication.recycle;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {
    public static List<TestData> getData() {
        List<TestData> result = new ArrayList();
        result.add(new TestData("中国开展第12次北极科学考察", "524.6w",1));
        result.add(new TestData("京东宣布全员涨薪两个月", "433.6w",2));
        result.add(new TestData("5年5次离婚男方上诉案将开庭", "357.8w"));
        result.add(new TestData("女儿突然坠楼被妈妈拽住腿", "333.6w",2));
        result.add(new TestData("陆川拍了神马微电影", "285.6w"));
        result.add(new TestData("刘吴呈 家暴", "183.2w",2));
        result.add(new TestData("教育部辟谣取消教师寒暑假", "139.4w",2));
        result.add(new TestData("瘦了30斤后会有什么变化", "75.6w",1));
        result.add(new TestData("郭刚堂曾和儿子擦肩而过", "55w",1));
        result.add(new TestData("俄罗斯女孩荡悬崖秋千绳索割断", "43w"));
        return result;
    }
}
