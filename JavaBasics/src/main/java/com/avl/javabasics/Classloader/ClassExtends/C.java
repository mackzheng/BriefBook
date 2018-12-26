package com.avl.javabasics.Classloader;

public class C extends B implements A {

    int i= 2;

    public void px()
    {
        System.out.println(i);  //这里有一个,多从定义；
    }

    public static void main(String[] args) {
        new C().px();
    }
}
