package com.dfusiontech.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	/**
	 * Returns Registered Bean Instance by Class name
	 *
	 * @param beanClass
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	/**
	 * Returns Registered Bean Instance by Class name
	 *
	 * @param beanName
	 * @param beanClass
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(String beanName, @Nullable Class<T> beanClass) {
		return context.getBean(beanName, beanClass);
	}
}
