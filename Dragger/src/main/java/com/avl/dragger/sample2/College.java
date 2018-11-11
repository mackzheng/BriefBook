package com.avl.dragger.sample2;

import javax.inject.Inject;

public class College {

    @Inject
    Student student;

    public College()
    {
        DaggerCollegeComponet.builder().build().inject(this);
    }
}
