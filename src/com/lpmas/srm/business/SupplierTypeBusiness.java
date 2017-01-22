package com.lpmas.srm.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierTypeDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierTypeBusiness {
	public int addSupplierType(SupplierTypeBean bean) {
		SupplierTypeDao dao = new SupplierTypeDao();
		int result = dao.insertSupplierType(bean);
		if (result > 0) {
			bean.setTypeId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getTypeId(),
					SupplierLogConfig.LOG_SUPPLIER_TYPE);
		}
		return result;
	}

	public int updateSupplierType(SupplierTypeBean bean) throws Exception {
		SupplierTypeDao dao = new SupplierTypeDao();
		SupplierTypeBean originalBean = dao.getSupplierTypeByKey(bean.getTypeId());
		int result = dao.updateSupplierType(bean);
		if (result > 0) {
			// 把聚合的Bean写入Mongo
			SupplierAddressEntityBusiness entityBusiness = new SupplierAddressEntityBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("supplierTypeId", String.valueOf(bean.getTypeId()));
			List<SupplierAddressEntityBean> entityList;
			entityList = entityBusiness.getSupplierAddressEntitListByMap(condMap);
			for (SupplierAddressEntityBean entityBean : entityList) {
				entityBean.setSupplierTypeName(bean.getTypeName());
				entityBusiness.updateSupplierAddressEntity(entityBean);
			}
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getTypeId(),
					SupplierLogConfig.LOG_SUPPLIER_TYPE);
		}
		return result;
	}

	public SupplierTypeBean getSupplierTypeByKey(int typeId) {
		SupplierTypeDao dao = new SupplierTypeDao();
		return dao.getSupplierTypeByKey(typeId);
	}

	public PageResultBean<SupplierTypeBean> getSupplierTypePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		SupplierTypeDao dao = new SupplierTypeDao();
		return dao.getSupplierTypePageListByMap(condMap, pageBean);
	}

	public List<SupplierTypeBean> getSupplierTypeParentTreeListByKey(int typeId) {
		List<SupplierTypeBean> result = new ArrayList<SupplierTypeBean>();
		SupplierTypeBean bean = getSupplierTypeByKey(typeId);
		while (bean != null) {
			result.add(bean);
			bean = getSupplierTypeByKey(bean.getParentTypeId());
		}
		Collections.reverse(result);
		return result;
	}

	public List<SupplierTypeBean> getSupplierTypeAllList() {
		SupplierTypeDao dao = new SupplierTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierTypeListByMap(condMap);
	}

	public List<SupplierTypeBean> getSupplierTypeListByParentId(int parentId) {
		SupplierTypeDao dao = new SupplierTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("parentTypeId", String.valueOf(parentId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierTypeListByMap(condMap);
	}

	public ReturnMessageBean validateSupplierTypeBean(SupplierTypeBean bean) {
		ReturnMessageBean messageBean = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getTypeCode())) {
			messageBean.setMessage("类型代码不能为空");
		} else if (isDuplicateSupplierTypeCode(bean.getTypeId(), bean.getTypeCode())) {
			messageBean.setMessage("类型代码不能重复");
		} else if (!StringKit.isValid(bean.getTypeName())) {
			messageBean.setMessage("类型名称不能为空");
		} else if (bean.getParentTypeId() != 0) {
			SupplierTypeBean supplierTypeBean = getSupplierTypeByKey(bean.getParentTypeId());
			if (supplierTypeBean.getStatus() == Constants.STATUS_NOT_VALID) {
				messageBean.setMessage("父类型无效,不能对子类型进行操作");
			}
		}
		return messageBean;
	}

	public Boolean isDuplicateSupplierTypeCode(int typeId, String typeCode) {
		List<SupplierTypeBean> typeList = null;
		SupplierTypeDao dao = new SupplierTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeCode", typeCode);
		typeList = dao.getSupplierTypeListByMap(condMap);
		if (typeList.isEmpty()) {
			return false;
		} else if (typeList.size() == 1) {
			SupplierTypeBean temp = typeList.get(0);
			if (typeId != 0 && typeId == temp.getTypeId()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

}