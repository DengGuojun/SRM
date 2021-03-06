package com.lpmas.srm.business.impl;

import java.util.HashMap;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.FreemarkerKit;
import com.lpmas.srm.business.PropertyDisplayUtil;
import com.lpmas.srm.business.PropertyInputDisplay;
import com.lpmas.srm.business.SupplierPropertyOptionBusiness;
import com.lpmas.srm.config.SrmConfig;

public class PropertyInputDisplaySelectImpl extends PropertyInputDisplay {

	public String getPropertyInputStr(Object PropertyTypeBean, Object PropertyBean, boolean isSubType) {

		String fileName = "selectInputDisplay.html";
		FreemarkerKit freemarkerKit = new FreemarkerKit();
		HashMap<String, Object> contentMap = freemarkerKit.getContentMap();

		contentMap.put("Property", PropertyBean);
		contentMap.put("PropertyType", PropertyTypeBean);
		contentMap.put("isTrue", Constants.SELECT_TRUE);
		contentMap.put("selectList", Constants.SELECT_LIST);
		SupplierPropertyOptionBusiness supplierPropertyOptionBusiness = new SupplierPropertyOptionBusiness();
		contentMap.put("PropertyOptionList", supplierPropertyOptionBusiness
				.getSupplierPropertyOptionListByPropertyId((int) invoke(PropertyTypeBean, "getPropertyId")));
		contentMap.put("styleMap",
				PropertyDisplayUtil.inputStyle2Map((String) invoke(PropertyTypeBean, "getInputStyle")));
		contentMap.put("isSubType", isSubType);
		return freemarkerKit.mergeTemplate(contentMap, SrmConfig.TEMPLATE_PATH, fileName);
	}

}
