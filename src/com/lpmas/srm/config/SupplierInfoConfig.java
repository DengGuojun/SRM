package com.lpmas.srm.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class SupplierInfoConfig {

	// 供应商状态
	public static final String SUPPLIER_STATUS_PARTNER = "PARTNER";
	public static final String SUPPLIER_STATUS_NON_PARTNER = "NON_PARTNER";
	public static List<StatusBean<String, String>> SUPPLIER_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> SUPPLIER_STATUS_MAP = new HashMap<String, String>();
	
	static{
		initSupplierStatusList();
		initSupplierStatusMap();
	}
	
	private static void initSupplierStatusList() {
		SUPPLIER_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		SUPPLIER_STATUS_LIST.add(new StatusBean<String, String>(SUPPLIER_STATUS_PARTNER, "已合作"));
		SUPPLIER_STATUS_LIST.add(new StatusBean<String, String>(SUPPLIER_STATUS_NON_PARTNER, "未合作"));
	}

	private static void initSupplierStatusMap() {
		SUPPLIER_STATUS_MAP = StatusKit.toMap(SUPPLIER_STATUS_LIST);
	}
}
