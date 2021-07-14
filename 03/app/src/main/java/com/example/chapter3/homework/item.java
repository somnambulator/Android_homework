package com.example.chapter3.homework;

import java.io.Serializable;
import java.util.ArrayList;

public class item implements Serializable {
    private static final long serialVersionUID = -6099312954099962806L;
    private String title;
    private String body;
    private String time;

    public item(String title, String body, String time) {
        this.title = title;
        this.body = body;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getTime(){
        return time;
    }

    public static ArrayList<item> getItems() {
        ArrayList<item> items = new ArrayList<item>();
        items.add(new item("Andy", "Hi", "just now"));
        items.add(new item("Bob", "[image]","yesterday"));
        items.add(new item("Cindy", "Hello","10:23"));
        items.add(new item("Donovan", "??","20:12"));
        items.add(new item("Enzo", "go basketball?","12:45"));
        items.add(new item("Francisco", "lol","yesterday"));
        items.add(new item("Godo", "[emoji]","16:30"));
        items.add(new item("Hank", "[emoji]","11:00"));
        items.add(new item("Ivan", "Hello","17:43"));
        items.add(new item("Jimmy", "lunch?","19:56"));
        items.add(new item("Kevin", "lend me some money","20:10"));
        items.add(new item("Leonard", "[image]","yesterday"));
        items.add(new item("Martin", "[image][image]","20:11"));
        items.add(new item("Noah", "correct your homework","19:33"));
        items.add(new item("Owen", "Hi","18:07"));
        items.add(new item("Paul", "bonjour","19:04"));
        items.add(new item("Quentin", "good morning","16:52"));
        return items;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
