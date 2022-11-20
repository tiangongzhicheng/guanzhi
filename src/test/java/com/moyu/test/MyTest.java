package com.moyu.test;

import com.alibaba.fastjson.JSONObject;
import com.moyu.entity.Worker;
import org.junit.Test;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/4/5.
 */

public class MyTest {

    /**
     * 控制台输入信息
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        while ((s=br.readLine())!=null){

            System.out.println(s);
            if("ok".equals(s)){
                break;
            }
        }
    }


    @Test
    public void test2() throws IOException, InterruptedException {

        Thread.sleep(5000);
        System.out.println("123");


    }



    /**
     * 递归查询
     */
    public void getNumberDemo(){
        List<Worker> workers = Arrays.asList(
                new Worker(1, 1, 1,true),
                new Worker(2, 2, 2,true),
                new Worker(10, 10, 1,true),
                new Worker(11, 20, 1,true),
                new Worker(100, 10, 10,false),
                new Worker(101, 10, 10,false),
                new Worker(103, 20, 11,false),
                new Worker(104, 20, 11,false),
                new Worker(105, 2, 2,false)
        );

        getNumber(workers,1);
    }


    public void getNumber(List<Worker> workers, int userId){
        for (Worker worker : workers){
            if(worker.getLeader().equals(userId) && worker.getId().equals(worker.getLeader())){
                System.out.println(worker.getId());
                continue;
            }
            if(worker.getLeader().equals(userId) && worker.getHeader()){
                System.out.println(worker.getId());
                getNumber(workers, worker.getId());
            }
            if(worker.getLeader().equals(userId) && !worker.getHeader()){
                System.out.println(worker.getId());
            }

        }


    }


    @Test
    public void test6(){

        Random random = new Random();
        Random random2 = new Random();
        int nanWin = 0;
        int nvWin = 0;

        for(int i=0;i<10000;i++){

            int count=1;
            int nv = 100;
            int nan = 100;
            while (true){

                int nvQ = random.nextInt(2);
                int nanQ = random.nextInt(2);
                //System.out.println("nvQ=="+nvQ+";nanQ=="+nanQ);
                if(nvQ ==0 && nanQ == 0){
                    nv = nv+3;
                    nan = nan-3;
                    //System.out.println("男的+3,nan="+nan+"，女的-3,nv="+nv);
                }else if(nvQ ==1 && nanQ == 1){
                    nv = nv+1;
                    nan = nan-1;
                    //System.out.println("男的+1,nan="+nan+"，女的-1,nv="+nv);
                }else {
                    nv = nv-2;
                    nan = nan+2;
                    //System.out.println("男的-2,nan="+nan+"，女的+2,nv="+nv);
                }
                count++;

                if(nv<=0){
//                    System.out.println("游戏结束，男的剩下"+nan+"元；女的剩下"+nv+"元"+"一共"+count+"次");
                    nanWin++;
                    break;
                }

                if(nan<=0){
//                    System.out.println("游戏结束，男的剩下"+nan+"元；女的剩下"+nv+"元"+"一共"+count+"次");
                    nvWin++;
                    break;
                }
            }
        }
        System.out.println("男的获胜==="+nanWin);
        System.out.println("女的获胜==="+nvWin);
    }



    @Test
    public void test7() throws IOException {
       String q ="qqq.xls";

        String substring = q.substring(q.lastIndexOf("."));
        System.out.println(substring);
    }


}
