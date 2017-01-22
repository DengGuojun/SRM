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
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierPropertyCategoryDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierPropertyCategoryBusiness {
	public int addSupplierPropertyCategory(SupplierPropertyCategoryBean bean) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		int result = dao.insertSupplierPropertyCategory(bean);
		if (result > 0) {
			bean.setCategoryId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getCategoryId(), 0, 0,
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_CATEGORY);
		}
		return result;
	}

	public int updateSupplierPropertyCategory(SupplierPropertyCategoryBean bean) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		SupplierPropertyCategoryBean originalBean = dao.getSupplierPropertyCategoryByKey(bean.getCategoryId());
		int result = dao.updateSupplierPropertyCategory(bean);
		if (result > 0) {
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getCategoryId(),
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_CATEGORY);
		}
		return result;
	}

	public SupplierPropertyCategoryBean getSupplierPropertyCategoryByKey(int categoryId) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		return dao.getSupplierPropertyCategoryByKey(categoryId);
	}

	public SupplierPropertyCategoryBean getSupplierPropertyCategoryByCode(String categoryCode) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		return dao.getSupplierPropertyCategoryByCode(categoryCode);
	}

	public PageResultBean<SupplierPropertyCategoryBean> getSupplierPropertyCategoryPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		return dao.getSupplierPropertyCategoryPageListByMap(condMap, pageBean);
	}

	public List<SupplierPropertyCategoryBean> getParentTreeListByKey(int categoryId) {
		List<SupplierPropertyCategoryBean> treeList = new ArrayList<SupplierPropertyCategoryBean>();
		SupplierPropertyCategoryBean bean = getSupplierPropertyCategoryByKey(categoryId);
		while (bean != null) {
			treeList.add(bean);
			bean = getSupplierPropertyCategoryByKey(bean.getParentCategoryId());
		}
		Collections.reverse(treeList);
		return treeList;
	}

	public List<SupplierPropertyCategoryBean> getSupplierPropertyCategoryListByParentId(int parentId) {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("parentCategoryId", String.valueOf(parentId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierPropertyCategoryListByMap(condMap);
	}

	public List<SupplierPropertyCategoryBean> getSupplierPropertyCategoryAllList() {
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierPropertyCategoryListByMap(condMap);
	}

	public ReturnMessageBean verifySupplierPropertyCategoryProperty(SupplierPropertyCategoryBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		SupplierPropertyCategoryDao dao = new SupplierPropertyCategoryDao();
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		if (!StringKit.isValid(bean.getCategoryCode())) {
			result.setMessage("类型代码必须填写");
		} else if (isDuplicateCategoryCode(bean.getCategoryId(), bean.getCategoryCode())) {
			result.setMessage("已存在相同的类型代码");
		} else if (!isValidTypeCode(bean.getCategoryCode())) {
			result.setMessage("类型代码格式不符合规范");
		} else if (!StringKit.isValid(bean.getCategoryName())) {
			result.setMessage("类型名称必须填写");
		} else if (bean.getParentCategoryId() != 0) {
			SupplierPropertyCategoryBean supplierPropertyCategoryBean = getSupplierPropertyCategoryByKey(
					bean.getParentCategoryId());
			if (supplierPropertyCategoryBean.getStatus() == Constants.STATUS_NOT_VALID) {
				result.setMessage("父属性类型无效,不能对子属性进行操作");
			}
		}
		if (bean.getStatus() == Constants.STATUS_NOT_VALID && bean.getCategoryId() != 0) {
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("parentCategoryId", String.valueOf(bean.getCategoryId()));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			if (dao.getSupplierPropertyCategoryListByMap(condMap).size() > 0) {
				result.setMessage("该供应商属性类型下包含子类型，不能设置为无效");
			} else {
				condMap = new HashMap<String, String>();
				condMap.put("categoryId", String.valueOf(bean.getCategoryId()));
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				if (supplierPropertyTypeBusiness.getSupplierPropertyTypeListByMap(condMap).size() > 0) {
					result.setMessage("该供应商属性类型下包含供应商属性，不能设置为无效");
				}
			}
		}
		return result;
	}

	public boolean isDuplicateCategoryCode(int categoryId, String categoryCode) {
		SupplierPropertyCategoryBean bean = getSupplierPropertyCategoryByCode(categoryCode);
		if (bean == null) {
			return false;
		} else {
			if (categoryId > 0 && categoryId == bean.getCategoryId()) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidTypeCode(String typeCode) {
		if (!typeCode.matches("^[a-zA-Z0-9_]+$")) {
			return false;
		}
		if (typeCode.length() > 10) {
			return false;
		}
		return true;
	}

}