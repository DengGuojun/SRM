package com.lpmas.srm.config;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.PropertiesKit;

public class SrmConfig {

	// appId
	public static final String APP_ID = "SRM";

	// 根节点父级ID
	public static final int ROOT_PARENT_ID = 0;

	public static final String SRM_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/srm_config";

	public static final Integer DEFAULT_PAGE_NUM = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 20;

	public static final String PAGE_PATH = Constants.PAGE_PATH + "srm/";
	
	public static final String TEMPLATE_PATH = PropertiesKit.getBundleProperties(SRM_PROP_FILE_NAME, "TEMPLATE_PATH");

}
