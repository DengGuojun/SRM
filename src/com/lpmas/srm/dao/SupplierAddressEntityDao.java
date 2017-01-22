package com.lpmas.srm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.lpmas.framework.nosql.mongodb.MongoDB;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.config.SrmMongoConfig;

public class SupplierAddressEntityDao {

	public int insertSupplierAddressEntity(SupplierAddressEntityBean bean) {
		return MongoDB.getInstance().insert(SrmMongoConfig.DB_NAME, SrmMongoConfig.COLLECTION_SUPPLIER_ADDRESS_ENTITY,
				bean);
	}

	public long updateSupplierAddressEntity(SupplierAddressEntityBean bean) {
		return MongoDB.getInstance().update(SrmMongoConfig.DB_NAME, SrmMongoConfig.COLLECTION_SUPPLIER_ADDRESS_ENTITY,
				bean);
	}

	public SupplierAddressEntityBean getSupplierAdressEntity(Integer id) throws Exception {
		return MongoDB.getInstance().getRecordById(SrmMongoConfig.DB_NAME,
				SrmMongoConfig.COLLECTION_SUPPLIER_ADDRESS_ENTITY, id, SupplierAddressEntityBean.class);
	}

	public PageResultBean<SupplierAddressEntityBean> getSupplierAddressEntityPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) throws Exception {
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String supplierName = condMap.get("supplierName");
		if (StringKit.isValid(supplierName)) {
			Pattern pattern = Pattern.compile(supplierName);
			queryMap.put("supplierName", pattern);
		}
		String supplierTypeName = condMap.get("supplierTypeName");
		if (StringKit.isValid(supplierTypeName)) {
			queryMap.put("supplierTypeName", supplierTypeName);
		}
		String supplierAddress = condMap.get("supplierAddress");
		if (StringKit.isValid(supplierAddress)) {
			queryMap.put("supplierAddress", supplierAddress);
		}
		Map<String, Object> orderBy = new HashMap<String, Object>();
		return MongoDB.getInstance().getPageListResult(SrmMongoConfig.DB_NAME,
				SrmMongoConfig.COLLECTION_SUPPLIER_ADDRESS_ENTITY, queryMap, SupplierAddressEntityBean.class, pageBean,
				orderBy);
	}

	public List<SupplierAddressEntityBean> getSupplierAddressEntityListByMap(HashMap<String, String> condMap)
			throws Exception {
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String supplierName = condMap.get("supplierName");
		if (StringKit.isValid(supplierName)) {
			queryMap.put("supplierName", supplierName);
		}
		String supplierId = condMap.get("supplierId");
		if (StringKit.isValid(supplierId)) {
			queryMap.put("supplierId", Integer.valueOf(supplierId));
		}
		String supplierTypeName = condMap.get("supplierTypeName");
		if (StringKit.isValid(supplierTypeName)) {
			queryMap.put("supplierTypeName", supplierTypeName);
		}
		String supplierTypeId = condMap.get("supplierTypeId");
		if (StringKit.isValid(supplierTypeId)) {
			queryMap.put("supplierTypeId",  Integer.valueOf(supplierTypeId));
		}
		String supplierAddress = condMap.get("supplierAddress");
		if (StringKit.isValid(supplierAddress)) {
			queryMap.put("supplierAddress", supplierAddress);
		}
		Map<String, Object> orderBy = new HashMap<String, Object>();
		return MongoDB.getInstance().getRecordListResult(SrmMongoConfig.DB_NAME,
				SrmMongoConfig.COLLECTION_SUPPLIER_ADDRESS_ENTITY, queryMap, SupplierAddressEntityBean.class, orderBy);
	}
}
