package com.lpmas.srm.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierPropertyOptionBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierPropertyTypeDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierPropertyTypeBusiness {
	public int addSupplierPropertyType(SupplierPropertyTypeBean bean) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		int result = dao.insertSupplierPropertyType(bean);
		if (result > 0) {
			bean.setPropertyId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getPropertyId(), 0, 0,
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_TYPE);
		}
		return result;
	}

	public int updateSupplierPropertyType(SupplierPropertyTypeBean bean) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		if (bean.getStatus() == Constants.STATUS_NOT_VALID) {
			SupplierPropertyOptionBusiness supplierPropertyOptionBusiness = new SupplierPropertyOptionBusiness();
			List<SupplierPropertyOptionBean> supplierPropertyOptionList = supplierPropertyOptionBusiness
					.getSupplierPropertyOptionListByPropertyId(bean.getPropertyId());
			for (SupplierPropertyOptionBean supplierPropertyOptionBean : supplierPropertyOptionList) {
				supplierPropertyOptionBean.setModifyUser(bean.getModifyUser());
				supplierPropertyOptionBean.setStatus(Constants.STATUS_NOT_VALID);
				supplierPropertyOptionBusiness.updateSupplierPropertyOption(supplierPropertyOptionBean);
			}
		}
		SupplierPropertyTypeBean originalBean = dao.getSupplierPropertyTypeByKey(bean.getPropertyId());
		int result = dao.updateSupplierPropertyType(bean);
		if (result > 0) {
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getPropertyId(),
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_TYPE);
		}
		return result;
	}

	public SupplierPropertyTypeBean getSupplierPropertyTypeByKey(int propertyId) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		return dao.getSupplierPropertyTypeByKey(propertyId);
	}

	public PageResultBean<SupplierPropertyTypeBean> getSupplierPropertyTypePageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		return dao.getSupplierPropertyTypePageListByMap(condMap, pageBean);
	}

	public List<SupplierPropertyTypeBean> getSupplierPropertyTypeListByMap(HashMap<String, String> condMap) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		return dao.getSupplierPropertyTypeListByMap(condMap);
	}

	public List<SupplierPropertyTypeBean> getSupplierPropertyTypeListByTypeId(int supplierType) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("supplierTypeId", String.valueOf(supplierType));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierPropertyTypeListByMap(condMap);
	}

	public List<SupplierPropertyTypeBean> getSupplierPropertyTypeListByType(int supplierType, int categoryId) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("supplierTypeId", String.valueOf(supplierType));
		condMap.put("categoryId", String.valueOf(categoryId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierPropertyTypeListByMap(condMap);
	}

	// 获取满足Category及所有子孙（往下继承），TypeId及所有祖辈（往上溯源）的供应商动态属性
	public List<SupplierPropertyTypeBean> getSupplierPropertyTypeTreeListByCantegoryIdAndTypeId(int categoryId,
			int typeId) {
		List<SupplierPropertyTypeBean> supplierPropertyTypeList = new ArrayList<SupplierPropertyTypeBean>();
		List<SupplierPropertyCategoryBean> categoryList = new ArrayList<SupplierPropertyCategoryBean>();
		SupplierTypeBusiness typeBusiness = new SupplierTypeBusiness();
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
		categoryList.add(supplierPropertyCategoryBusiness.getSupplierPropertyCategoryByKey(categoryId));
		List<SupplierPropertyCategoryBean> tempList = new ArrayList<SupplierPropertyCategoryBean>();
		List<SupplierTypeBean> supplierTypeParentTreeList = typeBusiness.getSupplierTypeParentTreeListByKey(typeId);
		while (!categoryList.isEmpty()) {
			for (SupplierPropertyCategoryBean supplierPropertyCategoryBean : categoryList) {
				for (SupplierTypeBean supplierTypeBean : supplierTypeParentTreeList) {
					supplierPropertyTypeList = ListKit.combineList(supplierPropertyTypeList,
							supplierPropertyTypeBusiness.getSupplierPropertyTypeListByType(supplierTypeBean.getTypeId(),
									supplierPropertyCategoryBean.getCategoryId()));
				}
				tempList = ListKit.combineList(tempList, supplierPropertyCategoryBusiness
						.getSupplierPropertyCategoryListByParentId(supplierPropertyCategoryBean.getCategoryId()));
			}
			categoryList.clear();
			for (int i = 0; i < tempList.size(); i++) {
				categoryList.add(tempList.get(i));
			}
			tempList.clear();
		}
		return supplierPropertyTypeList;
	}

	public SupplierPropertyTypeBean getSupplierPropertyTypeByParentId(int parentId) {
		SupplierPropertyTypeDao dao = new SupplierPropertyTypeDao();
		return dao.getSupplierPropertyTypeByParentId(parentId, Constants.STATUS_VALID);
	}

	public ReturnMessageBean verifySupplierPropertyType(SupplierPropertyTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getPropertyCode())) {
			result.setMessage("属性代码必须填写");
		} else if (!isValidPropertyCode(bean.getPropertyCode())) {
			result.setMessage("属性代码格式不符合规范");
		} else if (!StringKit.isValid(bean.getPropertyName())) {
			result.setMessage("属性名称必须填写");
		} else if (bean.getSupplierTypeId() <= 0) {
			result.setMessage("供应商类型必须填写");
		} else if (bean.getCategoryId() <= 0) {
			result.setMessage("属性类型必须填写");
		} else if (bean.getInputMethod() <= 0) {
			result.setMessage("输入方式必须填写");
		} else if (bean.getFieldType() <= 0) {
			result.setMessage("数据字段类型必须填写");
		} else if (bean.getParentPropertyId() != 0) {
			SupplierPropertyTypeBean supplierPropertyTypeBean = getSupplierPropertyTypeByKey(
					bean.getParentPropertyId());
			if (supplierPropertyTypeBean.getStatus() == Constants.STATUS_NOT_VALID) {
				result.setMessage("父属性无效,不能对子属性进行操作");
			}
		}
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		if (supplierTypeBusiness.getSupplierTypeByKey(bean.getSupplierTypeId())
				.getStatus() == Constants.STATUS_NOT_VALID) {
			result.setMessage("供应商类型无效");
		}
		SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
		if (supplierPropertyCategoryBusiness.getSupplierPropertyCategoryByKey(bean.getCategoryId())
				.getStatus() == Constants.STATUS_NOT_VALID) {
			result.setMessage("属性类型无效");
		}
		return result;
	}

	public boolean isValidPropertyCode(String propertyCode) {
		if (!propertyCode.matches("^[a-zA-Z0-9_]+$")) {
			return false;
		}
		return true;
	}

}