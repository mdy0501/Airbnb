package com.android.airbnb.domain;

/**
 * Created by JunHee on 2017. 8. 2..
 */

public class Amenities {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + "]";
    }
}