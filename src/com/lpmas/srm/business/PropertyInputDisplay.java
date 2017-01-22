package com.lpmas.srm.business;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyInputDisplay {

	private static Logger log = LoggerFactory.getLogger(PropertyInputDisplay.class);

	public abstract String getPropertyInputStr(Object PropertyTypeBean, Object PropertyBean, boolean isSubType);

	protected Object invoke(Object bean, String methodName) {
		Object result = "";
		try {
			Method method = bean.getClass().getMethod(methodName);
			result = method.invoke(bean);
		} catch (Exception e) {
			log.error("", e);
			return result;
		}
		return result;
	}
}
