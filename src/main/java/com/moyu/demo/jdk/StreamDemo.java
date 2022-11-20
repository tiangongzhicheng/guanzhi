package com.moyu.demo.jdk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {


    @Test
    public void test(){

        ArrayList<String> list = new ArrayList<>();

        list.add("张三");
        list.add("张三丰");
        list.add("张无忌");
        list.add("高尔基");

        list.stream().filter(a -> a.startsWith("张")).filter(a -> a.length() == 3).forEach(System.out::println);


        //limit取前几个，并且统计个数
        long count = list.stream().limit(2).count();
        System.out.println(count);

        //skip跳过前几个
        list.stream().skip(2).forEach(System.out::println);


        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.stream().map(Integer::parseInt).map(a -> a+100).forEach(System.out::println);

    }

    @Test
    public void test2(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("aaa");
        list2.add("bbb");
        list2.add("ccc");

        //concat将两个流合在一起
        Stream.concat(list2.stream(),list.stream()).forEach(System.out::println);

    }

}
