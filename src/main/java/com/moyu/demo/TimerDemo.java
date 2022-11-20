package com.moyu.demo;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

    public static void main(String[] args){
        Timer timer = new Timer();
        TimerTaskWork work = new TimerTaskWork();

        timer.schedule(work,2000,5000);
    }
}


class TimerTaskWork extends TimerTask{

    @Override
    public void run() {
        System.out.println("这是定式任务的内容");
    }
}
