package com.lpmas.srm.client.cache;

import java.util.ArrayList;
import java.util.List;

import com.lpmas.framework.cache.LocalCache;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.client.SrmServiceClient;
import com.lpmas.srm.config.SrmClientCacheConfig;
import com.opensymphony.oscache.base.NeedsRefreshException;

public class SupplierInfoClientCache {

	public List<SupplierInfoBean> getSupplierInfoAllList() {
		String key = SrmClientCacheConfig.getSupplierAllListCacheKey();
		LocalCache localCache = LocalCache.getInstance();
		Object obj = null;
		List<SupplierInfoBean> result = new ArrayList<SupplierInfoBean>(1);

		try {
			obj = localCache.get(key);
			result = JsonKit.toList((String) obj, SupplierInfoBean.class);
		} catch (NeedsRefreshException e) {
			SrmServiceClient srmServiceClient = new SrmServiceClient();
			result = srmServiceClient.getSupplierInfoAllList();
			if (result != null && !result.isEmpty()) {
				localCache.set(key, JsonKit.toJson(result), Constants.CACHE_TIME_1_HOUR);
			} else {
				localCache.cancelUpdate(key);
			}
		}
		return result;
	}

	public SupplierInfoBean getSupplierInfoByKey(int supplierId) {
		String key = SrmClientCacheConfig.getSupplierInfoCacheKeyByKey(supplierId);
		LocalCache localCache = LocalCache.getInstance();
		Object obj = null;
		SupplierInfoBean result = null;
		try {
			obj = localCache.get(key);
			result = JsonKit.toBean((String) obj, SupplierInfoBean.class);
		} catch (NeedsRefreshException e) {
			SrmServiceClient srmServiceClient = new SrmServiceClient();
			result = srmServiceClient.getSupplierInfoByKey(supplierId);
			if (result != null && result.getSupplierId() == supplierId) {
				localCache.set(key, JsonKit.toJson(result), Constants.CACHE_TIME_1_HOUR);
			} else {
				localCache.cancelUpdate(key);
			}
		}
		return result;
	}

	public SupplierInfoBean getSupplierInfoByAddressKey(int addressId) {
		String key = SrmClientCacheConfig.getSupplierInfoCacheKeyByAddressKey(addressId);
		LocalCache localCache = LocalCache.getInstance();
		Object obj = null;
		SupplierInfoBean result = null;
		try {
			obj = localCache.get(key);
			result = JsonKit.toBean((String) obj, SupplierInfoBean.class);
		} catch (NeedsRefreshException e) {
			SrmServiceClient srmServiceClient = new SrmServiceClient();
			result = srmServiceClient.getSupplierInfoByAddressKey(addressId);
			if (result != null && result.getSupplierId() > 0) {
				localCache.set(key, JsonKit.toJson(result), Constants.CACHE_TIME_1_HOUR);
			} else {
				localCache.cancelUpdate(key);
			}
		}
		return result;
	}
}
