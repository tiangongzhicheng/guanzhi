package com.moyu.demo.moreThrow;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MoreThrowDemo {


    /**
     * 线程有两种调度模型
     *  1、分时调度模型（平均）
     *  2、抢占式调度模型（优先级）
     *
     * 线程优先级默认为5，范围1~10
     * 线程优先级高仅仅表示获取的CPU时间片的几率高，并不绝对。
     *
     * join:等待该线程终止，然后才执行别的线程。
     *
     * yield：线程礼让，暂停当前线程对象，执行其他线程。但是不能保证一人一次轮流执行。
     *
     * setDaemon：守护线程（用户线程），当正在运行的线程都是守护线程时，java虚拟机退出。该方法必须在启动线程前调用。
     *
     * stop：停止线程。由于线程停止后后面的代码无法执行，不建议使用。
     *
     * interrupt：中断线程，把线程的状态终止，并抛出一个InterruptedExectption.
     *
     * 面试题：线程的生命周期
     *  新建：创建线程对象
     *  就绪：有执行资格，没有执行权
     *  运行：有执行资格，有执行权
     *      阻塞：由于一些操作让线程阻塞，没有执行资格没有执行权，而另一些操作可以吧他给激活，然后出于就绪状态。
     *
     *  死亡：线程对象变成垃圾，等待被回收
     *
     *
     */

    @Test
    public void test(){


        String name = Thread.currentThread().getName();
        System.out.println(name);

        MoreThrowMethod m1 = new MoreThrowMethod();
        MoreThrowMethod m2 = new MoreThrowMethod();
        m1.setName("线程1");
        m2.setName("线程2");
        m1.start();
        m2.start();

    }


    /**
     * 第三种实现多线程的方法，依赖于线程池，带分返回值.
     */
    @Test
    public void testCallAble() throws ExecutionException, InterruptedException {
        //创建线程池对象
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future s1 = pool.submit(new MyCallAble(10));
        Future s2 = pool.submit(new MyCallAble(5));
        System.out.println(s1.get());

        System.out.println(s2.get());

    }


    /**
     *
     */
    @Test
    public void test3(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("你好啊");
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("你好啊");
            }
        })
        {}.start();


        /** 下面这种方式走的是Tread子类对象的方法 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runable方法");
            }
        })
        {
            @Override
            public void run() {
                System.out.println("thread方法");
            }
        }.start();
    }

}
