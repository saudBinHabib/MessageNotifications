package com.example.saadi.message;

/**
 * Created by Saadi on 23-Dec-17.
 */

public class DataProvider {
    private  String name;
    private  String mob;

    public String getCategroy() {
        return categroy;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy;
    }

    private String categroy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public DataProvider(String name, String mob){
        this.name = name;
        this.mob = mob;

    }
    public DataProvider(){}
}
