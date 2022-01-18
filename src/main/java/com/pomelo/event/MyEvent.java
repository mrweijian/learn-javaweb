package com.pomelo.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyEvent
 * @Author weijian
 * @Date 2021/8/30
 */
public class MyEvent extends ApplicationEvent {

	public MyEvent(Object source) {
		super(source);
	}


}
