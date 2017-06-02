package com.example.jongzazaal.sampleretrofit2.model;

import com.google.gson.JsonObject;

/**
 * Created by jongzazaal on 5/30/2017.
 */

public class Dep {
    JsonObject des, des2;
//    String name;

    public Dep() {
    }

    public Dep(JsonObject des, JsonObject des2) {
        this.des = des;
        this.des2 = des2;
    }

    public JsonObject getDes() {
        return des;
    }

    public JsonObject getDes2() {
        return des2;
    }
}
