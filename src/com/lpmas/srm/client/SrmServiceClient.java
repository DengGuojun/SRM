package com.lpmas.srm.client;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.component.ComponentClient;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.component.SrmServicePrx;
import com.lpmas.srm.config.SrmClientConfig;
import com.lpmas.srm.config.SrmConfig;

public class SrmServiceClient {

	private static Logger log = LoggerFactory.getLogger(SrmServiceClient.class);

	private String rpc(String method, String params) {
		ComponentClient client = new ComponentClient();
		SrmServicePrx srmService = (SrmServicePrx) client.getProxy(SrmConfig.APP_ID, SrmServicePrx.class);
		String result = srmService.rpc(method, params);
		log.info("result : {}", result);
		return result;
	}

	public SupplierInfoBean getSupplierInfoByKey(int supplierId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("supplierId", String.valueOf(supplierId));
		return JsonKit.toBean(rpc(SrmClientConfig.GET_SUPPLIER_BY_KEY, JsonKit.toJson(param)), SupplierInfoBean.class);
	}
	
	public SupplierInfoBean getSupplierInfoByAddressKey(int addressId){
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("addressId", String.valueOf(addressId));
		return JsonKit.toBean(rpc(SrmClientConfig.GET_SUPPLIER_BY_ADDRESS_KEY, JsonKit.toJson(param)), SupplierInfoBean.class);
	}

	public List<SupplierInfoBean> getSupplierInfoAllList() {
		String remoteResult = rpc(SrmClientConfig.GET_SUPPLIER_ALL_LIST, "");
		return JsonKit.toList(remoteResult, SupplierInfoBean.class);
	}
	
}
