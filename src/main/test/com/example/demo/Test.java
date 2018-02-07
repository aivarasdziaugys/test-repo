package com.example.demo;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.example.demo.service.TestService;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Main.class})
public class Test{

    @Rule
    public TestRule benchmarkRule = new BenchmarkRule();

    @Autowired
    private TestService service;

    @org.junit.Test
    @BenchmarkOptions(warmupRounds = 5,benchmarkRounds = 10)
    public void test(){

        List<Integer> list = new ArrayList<>();
        Random rn = new Random();
        rn.setSeed(1000);

        for (int i =0; i< 100; i++){
            list.add(rn.nextInt(100));
        }
        list.remove(rn.nextInt(50));
//        bh.consume(list);
        service.testMethod(2,3);

    }
}
