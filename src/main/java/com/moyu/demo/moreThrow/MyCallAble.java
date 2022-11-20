package com.moyu.demo.moreThrow;

import java.util.concurrent.Callable;

public class MyCallAble implements Callable {

    private int number;

    public MyCallAble(int number) {
        this.number = number;
    }


    @Override
    public Object call() throws Exception {
        int sum =0;
        for (int i=1;i<=number;i++){
            sum += i;
        }

        return sum;
    }
}
