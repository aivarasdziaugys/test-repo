package com.example.demo;

import com.example.demo.service.TestService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkTest {
    public static void main(String args[]) throws Exception {
    }

    static ConfigurableApplicationContext context;

    private TestService service;

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
        TestService service = context.getBean(TestService.class);
        service.testMethod(2,6);
    }
}
