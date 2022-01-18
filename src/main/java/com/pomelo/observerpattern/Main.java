package com.pomelo.observerpattern;

/**
 * @ClassName Main
 * @Author weijian
 * @Date 2021/8/30
 */
public class Main {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new AObserver(subject);
        subject.setState(1);
        subject.setState(2);
    }
}
