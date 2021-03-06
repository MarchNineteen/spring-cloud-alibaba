package com.tuling.controller;

import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class TestSentinelRule {

    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

//        for (int i = 0; i < 1000; i++) {
//            restTemplate.postForObject("http://localhost:8080/saveOrder", null, String.class);
//            Thread.sleep(200);
//        }

        testWaiting();
    }

    public static void testWaiting() {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++) {
            restTemplate.postForObject("http://localhost:8080/findAll", null, String.class);
            System.out.println(new Date().getTime());
        }
    }
}
