package com.lpmas.srm.business.impl;

import java.util.HashMap;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.FreemarkerKit;
import com.lpmas.srm.business.PropertyDisplayUtil;
import com.lpmas.srm.business.PropertyInputDisplay;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SupplierPropertyTypeConfig;

public class PropertyInputDisplayTextareaImpl extends PropertyInputDisplay {

	public String getPropertyInputStr(Object PropertyTypeBean, Object PropertyBean, boolean isSubType) {
		String fileName = "textareaInputDisplay.html";
		FreemarkerKit freemarkerKit = new FreemarkerKit();
		HashMap<String, Object> contentMap = freemarkerKit.getContentMap();
		contentMap.put("Property", PropertyBean);
		contentMap.put("PropertyType", PropertyTypeBean);
		contentMap.put("isTrue", Constants.SELECT_TRUE);
		contentMap.put("number", SupplierPropertyTypeConfig.FIELD_TYPE_NUMBER);
		contentMap.put("styleMap",
				PropertyDisplayUtil.inputStyle2Map((String) invoke(PropertyTypeBean, "getInputStyle")));
		contentMap.put("isSubType", isSubType);
		return freemarkerKit.mergeTemplate(contentMap, SrmConfig.TEMPLATE_PATH, fileName);
	}

}
