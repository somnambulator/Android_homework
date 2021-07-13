package com.example.myapplication.recycle;

public class TestData {
    String title;
    String hot;
    int type;

    public TestData(String title, String hot) {
        this.title = title;
        this.hot = hot;
        this.type = 0;
    }
    public TestData(String title, String hot, int type) {
        this.title = title;
        this.hot = hot;
        this.type = type;
    }
}
