package com.lpmas.srm.config;

import com.lpmas.framework.util.PropertiesKit;

public class SrmDBConfig {

	public static String DB_LINK_SRM_W = PropertiesKit.getBundleProperties(SrmConfig.SRM_PROP_FILE_NAME,
			"DB_LINK_SRM_W");

	public static String DB_LINK_SRM_R = PropertiesKit.getBundleProperties(SrmConfig.SRM_PROP_FILE_NAME,
			"DB_LINK_SRM_R");
}
