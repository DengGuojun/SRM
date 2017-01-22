package com.lpmas.srm.config;

import java.util.HashMap;
import java.util.Map;

public class SupplierLogInfoDisConfig {

	public static Map<String, Map<?, ?>> CONFIG_VALUE_MAP = new HashMap<String, Map<?, ?>>();
	static {
		CONFIG_VALUE_MAP = new HashMap<String, Map<?, ?>>();
		CONFIG_VALUE_MAP.put("输入方式，文本框、选择框", SupplierPropertyTypeConfig.INPUT_METHOD_MAP);
		CONFIG_VALUE_MAP.put("数据字段类型文本、数字", SupplierPropertyTypeConfig.FIELD_TYPE_MAP);
		CONFIG_VALUE_MAP.put("是否可修改", SupplierPropertyTypeConfig.PROPERTY_MODIFI_MAP);
		CONFIG_VALUE_MAP.put("供应商状态", SupplierInfoConfig.SUPPLIER_STATUS_MAP);
	}

}
