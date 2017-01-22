package com.lpmas.srm.component.impl;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.util.JsonKit;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.business.SupplierAddressBusiness;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.component._SrmServiceDisp;
import com.lpmas.srm.config.SrmClientConfig;

import Ice.Current;

public class SrmServiceImpl extends _SrmServiceDisp {

	private static final long serialVersionUID = 1L;

	private String getSupplierInfoByKey(String params) {
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		SupplierInfoBusiness business = new SupplierInfoBusiness();
		SupplierInfoBean bean = business.getSupplierInfoByKey(Integer.valueOf(paramMap.get("supplierId")));
		if (bean == null) {
			bean = new SupplierInfoBean();
		}
		return JsonKit.toJson(bean);
	}

	private String getSupplierInfoAllList() {
		SupplierInfoBusiness business = new SupplierInfoBusiness();
		List<SupplierInfoBean> supplierInfoList = business.getSupplierInfoAllList();
		return JsonKit.toJson(supplierInfoList);
	}

	public String getSupplierInfoByAddressKey(String params) {
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		int addressId = Integer.valueOf(paramMap.get("addressId"));
		SupplierInfoBean bean = null;
		SupplierAddressBusiness addressBusiness = new SupplierAddressBusiness();
		SupplierAddressBean addressBean = addressBusiness.getSupplierAddressByKey(addressId);
		if (addressBean != null) {
			SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
			bean = supplierInfoBusiness.getSupplierInfoByKey(addressBean.getSupplierId());
		}
		if (bean == null) {
			bean = new SupplierInfoBean();
		}
		return JsonKit.toJson(bean);
	}

	@Override
	public String rpc(String method, String params, Current __current) {
		String result = "";
		if (method.equals(SrmClientConfig.GET_SUPPLIER_BY_KEY)) {
			result = getSupplierInfoByKey(params);
		} else if (method.equals(SrmClientConfig.GET_SUPPLIER_BY_ADDRESS_KEY)) {
			result = getSupplierInfoByAddressKey(params);
		} else if (method.equals(SrmClientConfig.GET_SUPPLIER_ALL_LIST)) {
			result = getSupplierInfoAllList();
		}

		return result;
	}
}
