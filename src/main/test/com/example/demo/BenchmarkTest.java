package com.example.demo;

import com.example.demo.service.TestService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput) @OutputTimeUnit(TimeUnit.MINUTES)
@State(Scope.Thread)
public class BenchmarkTest {
    public static void main(String args[]) throws Exception {

        URLClassLoader classLoader = (URLClassLoader) Main.class.getClassLoader();
        StringBuilder classpath = new StringBuilder();
        for(URL url : classLoader.getURLs())
            classpath.append(url.getPath()).append(File.pathSeparator);
        classpath.append("/C:/Users/cu0091/Projects/demo/src/main/resources/").append(File.pathSeparator);
        System.out.print(classpath.toString());
        System.setProperty("java.class.path", classpath.toString());

        Options opt = new OptionsBuilder()
                .include(Main.class.getName() + ".*")
                .timeUnit(TimeUnit.MILLISECONDS)
                .threads(1)

                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
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
            System.out.println(service);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void test(){
        service.testMethod(2,6);
    }
}
