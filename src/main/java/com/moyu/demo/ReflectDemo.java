package com.moyu.demo;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取class文件对象的方式
 * A:Object类的getClass()方法
 * B:数据类型的静态属性：.class
 * C:Class.forName("")  //注意写类的全路径
 */


public class ReflectDemo {

    public void getConstructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        Class c = Class.forName("com/moyu/entity/User.java");

        //获取所有公共构造方法
        Constructor[] constructors = c.getConstructors();

        //获取所有构造方法，包括私有构造
        Constructor[] declaredConstructors = c.getDeclaredConstructors();

        //获取无参构造方法
        Constructor constructor = c.getConstructor();

        //获取带参构造方法，参数表示你要获取的构造方法的参数类型
        Constructor constructorParm = c.getConstructor(Integer.class, String.class);

        //通过Constructor对象获取实例
        Object o = constructor.newInstance();


    }

}
