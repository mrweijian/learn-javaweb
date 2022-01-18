package com.pomelo.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Subject
 * @Author weijian
 * @Date 2021/8/30
 */
public class Subject {

    private int state;

    List<Observer> observers = new ArrayList<>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        // 通知所有的观察者
        notifyAllObserver();
    }

    // 注册监听器
    public void attach(Observer observer){
        this.observers.add(observer);
    }
    private void notifyAllObserver(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
