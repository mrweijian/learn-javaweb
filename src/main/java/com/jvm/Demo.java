package com.jvm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @ClassName Demo
 * @Author weijian
 * @Date 2021/9/29
 */
public class Demo {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public static void main(String[] args) throws Exception{
//        while (true){
//            BTest bTest = new BTest();
//        }
        BTest bTest = new BTest();
        SoftReference<BTest> weakReference = new SoftReference<BTest>(bTest);
        System.out.println(weakReference.get());
        bTest =null;
        System.gc();
        //Thread.sleep(3000);
        System.out.println(weakReference.get());

    }
}
