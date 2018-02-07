package com.example.demo.service;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class TestService {


    public double testMethod(double x, double y){
        double c =  Math.pow(x,y);
        return c;
    }

    @Benchmark
    public void benchmarkTest(Blackhole bh){
        List<String> strings = new ArrayList<>();
        int i = 0;
        Random r = new Random(1000);
        while(i<999){
            String s = "s"+r.nextInt(10)+"s"+r.nextInt(1999);
            strings.add(s);
            i++;
        }
        while(i>500){
            strings.remove(r.nextInt(i-20));
            i--;
        }
        bh.consume(strings);
    }
}
