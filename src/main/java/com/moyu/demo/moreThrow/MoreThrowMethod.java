package com.moyu.demo.moreThrow;


public class MoreThrowMethod extends Thread {

    public void run() {
        String name = getName();


        for (int i = 0; i <1000 ; i++) {
            System.out.println(name +"----"+i);
        }



    }


}
