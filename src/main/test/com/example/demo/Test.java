package com.example.demo;

import com.example.demo.service.TestService;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Main.class})
@State(Scope.Benchmark)
@Component
public class Test{

    private TestService service;

    static ConfigurableApplicationContext context;

    @Setup (Level.Trial)
    public synchronized void  initialize() {
        try {
            String args = "";
            if(context == null) {
                context = SpringApplication.run(Main.class, args );
            }
            service = context.getBean(TestService.class);
//            System.out.println(service);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void test(){

        List<Integer> list = new ArrayList<>();
        Random rn = new Random();
        rn.setSeed(1000);

        for (int i =0; i< 100; i++){
            list.add(rn.nextInt(100));
        }
        list.remove(rn.nextInt(50));
        service.testMethod(2,3);

    }
}
