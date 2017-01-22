package com.lpmas.srm.business.impl;

import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.business.PropertyInputVerify;

public class PropertyInputVerifyNumberImpl extends PropertyInputVerify {

	public ReturnMessageBean verifyProperty(String value) {
		ReturnMessageBean result = new ReturnMessageBean();
		try {
			Double.parseDouble(value);
		} catch (Exception e) {
			result.setMessage("该字段必须符合数值格式;");
		}
		return result;
	}

}
