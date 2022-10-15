package com.breathofdawn.breathofdawn;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;

    private Car car;

    public User(String n, int a, Car c)
    {
        name = n;
        age = a;
        car = c;
    }

    public String getName(){
        return name;
    }

    public String toJsonString()
    {
        return "{\"name\":" + name + ",\"age\":" + age + ",\"car\":{" + car.toJsonString() + "}}";
    }
}
