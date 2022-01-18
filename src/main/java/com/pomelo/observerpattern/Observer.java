package com.pomelo.observerpattern;

/**
 * @ClassName Observer
 * @Author weijian
 * @Date 2021/8/30
 */
public abstract class Observer {

    protected Subject subject;
    protected abstract void update();

}
