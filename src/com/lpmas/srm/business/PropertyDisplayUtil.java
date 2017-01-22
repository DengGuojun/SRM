package com.lpmas.srm.business;

import java.util.HashMap;
import java.util.Map;

import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.srm.bean.SupplierPropertyBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;

public class PropertyDisplayUtil {

	public static String displayPropertyInput(SupplierPropertyTypeBean typeBean, SupplierPropertyBean bean,
			boolean isSubType) {
		SupplierPropertyBusiness propertyBusiness = new SupplierPropertyBusiness();
		if (bean == null) {
			bean = new SupplierPropertyBean();
		}
		return propertyBusiness.displayPropertyInput(typeBean, bean, isSubType);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> inputStyle2Map(String inputStyle) {
		Map<String, String> styleMap = new HashMap<String, String>();
		if (StringKit.isValid(inputStyle)) {
			styleMap = JsonKit.toBean(inputStyle, Map.class);
		}
		return styleMap;
	}

}
