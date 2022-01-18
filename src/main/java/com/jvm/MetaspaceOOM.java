package com.jvm;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName OOM
 * @Author weijian
 * @Date 2021/10/18
 */
public class MetaspaceOOM {

    // -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\weijian\gc_hprof -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
    public static void main(String[] args) {

        long count = 0;
        while (true) {
            System.out.println("目前创建了对象个数-------" + count++);
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Car.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                    if (method.getName().equals("run")) {
                        System.out.println("汽车马上启动！，请系好安全带！");
                        return methodProxy.invokeSuper(o, objects);
                    } else {
                        return methodProxy.invokeSuper(o, objects);
                    }
                }
            });
            Car car = (Car) enhancer.create();
            car.run();
        }

    }

    static class Car {

        public void run() {
            System.out.println("汽车启动！");
        }
    }

    static class SafeCar extends MetaspaceOOM.Car {
        @Override
        public void run() {
            {
                System.out.println("SafeCar汽车启动！");
                super.run();
            }
        }
    }
}
