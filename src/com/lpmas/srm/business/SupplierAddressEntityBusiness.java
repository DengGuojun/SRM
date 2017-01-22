package com.lpmas.srm.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.dao.SupplierAddressEntityDao;

public class SupplierAddressEntityBusiness {

	
	public int insertSupplierAddressEntity(SupplierAddressEntityBean bean) {
		SupplierAddressEntityDao dao = new SupplierAddressEntityDao();
		return dao.insertSupplierAddressEntity(bean);
	}

	public long updateSupplierAddressEntity(SupplierAddressEntityBean bean) {
		SupplierAddressEntityDao dao = new SupplierAddressEntityDao();
		return dao.updateSupplierAddressEntity(bean);
	}

	public SupplierAddressEntityBean getSupplierAdressEntity(Integer id) throws Exception {
		SupplierAddressEntityDao dao = new SupplierAddressEntityDao();
		return dao.getSupplierAdressEntity(id);
	}

	public PageResultBean<SupplierAddressEntityBean> getSupplierAddressEntityPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean)
					throws Exception {
		SupplierAddressEntityDao dao = new SupplierAddressEntityDao();
		return dao.getSupplierAddressEntityPageListByMap(condMap, pageBean);
	}
	
	public List<SupplierAddressEntityBean> getSupplierAddressEntitListByMap(
			HashMap<String, String> condMap)
					throws Exception {
		SupplierAddressEntityDao dao = new SupplierAddressEntityDao();
		return dao.getSupplierAddressEntityListByMap(condMap);
	}
}
