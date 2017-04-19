package com.cxh.busdemo;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:41.
 */
public class Event {
    public String name;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}