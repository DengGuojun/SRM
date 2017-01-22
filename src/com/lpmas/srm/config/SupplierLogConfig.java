package com.lpmas.srm.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class SupplierLogConfig {

	// 供应商操作日志配置项
	public static String LOG_SUPPLIER_TYPE = "SUPPLIER_TYPE";
	public static String LOG_SUPPLIER_TYPE_PROPERTY = "SUPPLIER_TYPE_PROPERTY";
	public static String LOG_SUPPLIER_INFO = "SUPPLIER_INFO";
	public static String LOG_SUPPLIER_PROPERTY_TYPE = "SUPPLIER_PROPERTY_TYPE";
	public static String LOG_SUPPLIER_PROPERTY_TYPE_OPTION = "SUPPLIER_PROPERTY_TYPE_OPTION";
	public static String LOG_SUPPLIER_PROPERTY = "SUPPLIER_PROPERTY";
	public static String LOG_SUPPLIER_ADDRESS = "SUPPLIER_ADDRESS";
	public static String LOG_SUPPLIER_PROPERTY_CATEGORY = "SUPPLIER_PROPERTY_CATEGORY";
	public static List<StatusBean<String, String>> LOG_SUPPLIER_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> LOG_SUPPLIER_MAP = new HashMap<String, String>();

	static {

		initLogSupplierList();
		initLogSupplierMap();

	}

	public static void initLogSupplierList() {
		LOG_SUPPLIER_LIST = new ArrayList<StatusBean<String, String>>();
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_TYPE, "供应商类型"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_INFO, "供应商信息"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_PROPERTY_TYPE, "供应商属性配置"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_PROPERTY_TYPE_OPTION, "供应商属性配置选项"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_PROPERTY, "供应商属性"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_ADDRESS, "供应商地址"));
		LOG_SUPPLIER_LIST.add(new StatusBean<String, String>(LOG_SUPPLIER_PROPERTY_CATEGORY, "供应商属性分类配置"));
	}

	public static void initLogSupplierMap() {
		LOG_SUPPLIER_MAP = new HashMap<String, String>();
		LOG_SUPPLIER_MAP = StatusKit.toMap(LOG_SUPPLIER_LIST);
	}
}
