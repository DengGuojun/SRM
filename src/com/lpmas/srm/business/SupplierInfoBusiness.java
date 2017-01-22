package com.lpmas.srm.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierInfoDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierInfoBusiness {
	public int addSupplierInfo(SupplierInfoBean bean) {
		SupplierInfoDao dao = new SupplierInfoDao();
		int result = dao.insertSupplierInfo(bean);
		if (result > 0) {
			bean.setSupplierId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getSupplierId(),
					SupplierLogConfig.LOG_SUPPLIER_INFO);
		}
		return result;
	}

	public int updateSupplierInfo(SupplierInfoBean bean) throws Exception {
		SupplierInfoDao dao = new SupplierInfoDao();
		SupplierInfoBean originalBean = dao.getSupplierInfoByKey(bean.getSupplierId());
		int result = dao.updateSupplierInfo(bean);
		if (result > 0) {

			// 把聚合的Bean写入Mongo
			SupplierAddressEntityBusiness entityBusiness = new SupplierAddressEntityBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("supplierId", String.valueOf(bean.getSupplierId()));
			List<SupplierAddressEntityBean> entityList;
			entityList = entityBusiness.getSupplierAddressEntitListByMap(condMap);
			for (SupplierAddressEntityBean entityBean : entityList) {
				entityBean.setSupplierName(bean.getSupplierName());
				entityBusiness.updateSupplierAddressEntity(entityBean);
			}

			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getSupplierId(),
					SupplierLogConfig.LOG_SUPPLIER_INFO);
		}
		return result;
	}

	public SupplierInfoBean getSupplierInfoByKey(int supplierId) {
		SupplierInfoDao dao = new SupplierInfoDao();
		return dao.getSupplierInfoByKey(supplierId);
	}

	public PageResultBean<SupplierInfoBean> getSupplierInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		SupplierInfoDao dao = new SupplierInfoDao();
		return dao.getSupplierInfoPageListByMap(condMap, pageBean);
	}

	public List<SupplierInfoBean> getSupplierInfoListByMap(HashMap<String, String> condMap) {
		SupplierInfoDao dao = new SupplierInfoDao();
		return dao.getSupplierInfoListByMap(condMap);
	}

	public List<SupplierInfoBean> getSupplierInfoAllList() {
		SupplierInfoDao dao = new SupplierInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierInfoListByMap(condMap);
	}

	public ReturnMessageBean verifySupplierInfo(SupplierInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getSupplierName())) {
			result.setMessage("供应商名称必须填写");
		}
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		if (supplierTypeBusiness.getSupplierTypeByKey(bean.getSupplierType()).getStatus() == Constants.STATUS_NOT_VALID) {
			result.setMessage("供应商类型无效");
		}
		return result;
	}

}