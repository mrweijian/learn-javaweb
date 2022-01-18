package com.pomelo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyAppListener
 * @Author weijian
 * @Date 2021/8/30
 */
@Component
public class MyAppListener implements ApplicationListener<MyEvent> {

	@Override
	public void onApplicationEvent(MyEvent event) {
		Object source = event.getSource();
		System.out.println(source);
		System.out.println("监听到事件！");
	}
}
