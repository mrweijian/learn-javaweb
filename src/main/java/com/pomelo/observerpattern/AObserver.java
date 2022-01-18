package com.pomelo.observerpattern;

/**
 * @ClassName AObserver
 * @Author weijian
 * @Date 2021/8/30
 */
public class AObserver extends Observer {


    public AObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    protected void update() {
        System.out.println("A收到修改信息" + subject.getState());
    }
}
