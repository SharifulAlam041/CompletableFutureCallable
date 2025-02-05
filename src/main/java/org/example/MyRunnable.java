package org.example;

import java.util.List;

public class MyRunnable implements Runnable{
    List<Integer> list;

    MyRunnable(List<Integer> list){
        this.list = list;
    }


    @Override
    public void run() {
        this.list.add(300);
    }
}
