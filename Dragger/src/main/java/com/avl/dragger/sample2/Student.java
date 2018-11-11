package com.avl.dragger.sample2;

import javax.inject.Inject;

public class Student {

    private int id;
    private String name;

    @Inject
    public Student() {
        System.out.printf("Create student");
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
