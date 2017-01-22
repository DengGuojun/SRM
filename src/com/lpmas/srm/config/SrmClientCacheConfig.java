package com.lpmas.srm.config;

public class SrmClientCacheConfig {

	private static final String SUPPLIER_ALL_LIST_CACHE_KEY = "SUPPLIER_ALL_LIST_CACHE_KEY";
	private static final String SUPPLIER_INFO_CACHE_KEY = "SUPPLIER_INFO_CACHE_";
	private static final String SUPPLIER_INFO_BY_ADDRESS_CACHE_KEY = "SUPPLIER_INFO_BY_ADDRESS_CACHE_";

	public static String getSupplierAllListCacheKey() {
		return SUPPLIER_ALL_LIST_CACHE_KEY;
	}

	public static String getSupplierInfoCacheKeyByKey(int supplierId) {
		return SUPPLIER_INFO_CACHE_KEY + supplierId;
	}

	public static String getSupplierInfoCacheKeyByAddressKey(int addressId) {
		return SUPPLIER_INFO_BY_ADDRESS_CACHE_KEY + addressId;
	}

}
