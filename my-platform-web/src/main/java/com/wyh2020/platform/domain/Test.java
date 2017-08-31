package com.wyh2020.platform.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with wyh.
 * Date: 2017/7/2
 * Time: 上午12:52
 */
@Data
public class Test implements Serializable{

    private int id;


    private String name;


    private int age;


    private int sum;


    public String toString() {
        return "id==" + getId() + "; name==" + getName() + "; age==" + getAge() + "; sum==" + getSum();
    }
}
