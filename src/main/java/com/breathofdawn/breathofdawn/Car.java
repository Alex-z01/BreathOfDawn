package com.breathofdawn.breathofdawn;

import java.io.Serializable;

public class Car implements Serializable {
    private String brand;
    private int miles;

    public Car(String b, int m)
    {
        brand = b;
        miles = m;
    }

    public String toJsonString(){
        return "\"brand\":" + brand + ",\"miles\":" + miles;
    }
}
