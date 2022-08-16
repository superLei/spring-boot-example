package com.example.service.qa;

import com.google.gson.Gson;

public class ShellTest {


    public static void main(String[] args) {
        AA.A a = new AA.A();
        a.setAa("1");
        a.setBb("2");
        a.setCc("3");
        System.out.println(new Gson().toJson(a));
    }



}
