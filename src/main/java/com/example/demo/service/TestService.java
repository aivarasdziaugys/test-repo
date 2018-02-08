package com.example.demo.service;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class TestService {
    private static String listValues = "";
    private static List<String> stringList = new ArrayList<>();


    public double testMethod(double x, double y){
        double c =  Math.pow(x,y);
        return c;
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
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
        stringList = new ArrayList<>(strings);
        bh.consume(strings);
    }

    /*logback.xml - for logging*/
    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void benchmarkTest2(Blackhole bh){
        Map<Integer,String> map = new HashMap<>();
        Random random = new Random(1000);
        int counter = 0;
        while (counter < 1000){
            String chars = "qwertyuiopasdfghjklzxcvbnm";
            int charNumber = random.nextInt(50);
            String randomString = "";
            for(int i = 0; i<charNumber; i++){
                randomString.concat(String.valueOf(chars.charAt(random.nextInt(chars.length()))));
            }
            map.put(counter,randomString);
            counter++;
        }
        bh.consume(map);
    }
}
