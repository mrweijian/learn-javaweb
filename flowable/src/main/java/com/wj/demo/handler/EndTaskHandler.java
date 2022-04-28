package com.wj.demo.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @ClassName EndTaskHandler
 * @Author weijian
 * @Date 2022/3/22
 */
public class EndTaskHandler implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("发送消息通知！");
    }
}
