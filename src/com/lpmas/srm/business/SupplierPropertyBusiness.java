package com.lpmas.srm.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierPropertyBean;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.config.SupplierPropertyTypeConfig;
import com.lpmas.srm.dao.SupplierPropertyDao;
import com.lpmas.srm.factory.SupplierPropertyFactory;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierPropertyBusiness {
	public int addSupplierProperty(SupplierPropertyBean bean) {
		SupplierPropertyDao dao = new SupplierPropertyDao();
		int result = dao.insertSupplierProperty(bean);
		if (result > 0) {
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getSupplierId(), 0, 0,
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY, bean.getPropertyCode(), "");
		}
		return result;
	}

	public int updateSupplierProperty(SupplierPropertyBean bean) {
		SupplierPropertyDao dao = new SupplierPropertyDao();
		SupplierPropertyBean originalBean = dao.getSupplierPropertyByKey(bean.getSupplierId(), bean.getPropertyCode());
		int result = dao.updateSupplierProperty(bean);
		if (result > 0) {
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getSupplierId(), 0, 0,
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY, bean.getPropertyCode(), "");
		}
		return result;
	}

	public SupplierPropertyBean getSupplierPropertyByKey(int supplierId, String propertyCode) {
		SupplierPropertyDao dao = new SupplierPropertyDao();
		return dao.getSupplierPropertyByKey(supplierId, propertyCode);
	}

	public List<SupplierPropertyBean> getSupplierPropertyListBySupplierId(int supplierId) {
		SupplierPropertyDao dao = new SupplierPropertyDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("supplierId", String.valueOf(supplierId));
		return dao.getSupplierPropertyListByMap(condMap);
	}

	public PageResultBean<SupplierPropertyBean> getSupplierPropertyPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		SupplierPropertyDao dao = new SupplierPropertyDao();
		return dao.getSupplierPropertyPageListByMap(condMap, pageBean);
	}

	public String displayPropertyInput(SupplierPropertyTypeBean supplierPropertyTypeBean,
			SupplierPropertyBean supplierPropertyBean, boolean isSubType) {
		SupplierPropertyFactory factory = new SupplierPropertyFactory();
		PropertyInputDisplay display = factory.getPropertyInputDisplay(supplierPropertyTypeBean.getInputMethod());
		return display.getPropertyInputStr(supplierPropertyTypeBean, supplierPropertyBean, isSubType);
	}

	public ReturnMessageBean verifySupplierInfoProperty(Map<Integer, String> parameters) {
		ReturnMessageBean result = new ReturnMessageBean();
		String msg = "";
		SupplierPropertyTypeBusiness propertyBusiness = new SupplierPropertyTypeBusiness();
		for (int propertyId : parameters.keySet()) {
			SupplierPropertyTypeBean propertyType = propertyBusiness.getSupplierPropertyTypeByKey(propertyId);
			if (propertyType != null) {
				ReturnMessageBean msgTemp = verifySupplierInfoProperty(propertyType, parameters.get(propertyId));
				if (StringKit.isValid(msgTemp.getMessage())) {
					msg += "[" + propertyType.getPropertyName() + "]" + msgTemp.getMessage() + ",";
				}
			}
		}
		if (msg.length() > 0) {
			msg = msg.substring(0, msg.length() - 1);
		}
		if (StringKit.isValid(msg)) {
			result.setMessage(msg);
		}
		return result;
	}

	public ReturnMessageBean verifySupplierInfoProperty(SupplierPropertyTypeBean propertyType, String value) {
		ReturnMessageBean result = new ReturnMessageBean();
		// 校验是否必填
		if (propertyType.getIsRequired() == Constants.STATUS_VALID) {
			if (!StringKit.isValid(value)) {
				result.setMessage("该字段是必填字段;");
				return result;
			}
		}
		// 校验长度
		if (StringKit.isValid(propertyType.getInputStyle()) && StringKit.isValid(value)) {
			Map<String, String> inputStyleMap = JsonKit.toBean(propertyType.getInputStyle(), Map.class);
			int maxlength = StringKit.isValid(inputStyleMap.get(SupplierPropertyTypeConfig.MAX_LENGTH))
					? Integer.parseInt(inputStyleMap.get(SupplierPropertyTypeConfig.MAX_LENGTH)) : 0;
			int minlength = StringKit.isValid(inputStyleMap.get(SupplierPropertyTypeConfig.MIN_LENGTH))
					? Integer.parseInt(inputStyleMap.get(SupplierPropertyTypeConfig.MIN_LENGTH)) : 0;

			if (maxlength > 0) {
				if (value.length() > maxlength) {
					result.setMessage("最大只能录入" + maxlength + "个字");
					return result;
				}
			}
			if (minlength > 0) {
				if (value.length() < minlength) {
					result.setMessage("最小需要录入" + minlength + "个字");
					return result;
				}
			}
		}
		// 判断字段格式
		if (StringKit.isValid(propertyType.getFieldFormat()) && StringKit.isValid(value)) {
			String[] msgArr = new String[2];
			if (propertyType.getFieldFormat().indexOf("#") > 0) {
				msgArr = propertyType.getFieldFormat().split("#");
			} else {
				msgArr[0] = propertyType.getFieldFormat();
			}
			Pattern p = Pattern.compile(msgArr[0]);// 复杂匹配
			Matcher m = p.matcher(value);
			if (!m.matches()) {
				result.setMessage("格式不符！" + (StringKit.isValid(msgArr[1]) ? "正确格式例如：" + msgArr[1] : ""));
				return result;
			}
		}
		// 判断字段类型
		if (StringKit.isValid(value)) {
			SupplierPropertyFactory propertyFactory = new SupplierPropertyFactory();
			PropertyInputVerify propertyVerify = propertyFactory.getPropertyInputVerify(propertyType.getFieldType());
			if (propertyVerify != null) {
				result = propertyVerify.verifyProperty(value);
			}
		}
		return result;
	}

	public List<Map.Entry<Integer, SupplierPropertyCategoryBean>> getSupplierPropertyCategoryMapByTypeId(int typeId) {
		Map<Integer, Boolean> count = new HashMap<Integer, Boolean>();
		Map<Integer, SupplierPropertyCategoryBean> propertyCategoryMap = new HashMap<Integer, SupplierPropertyCategoryBean>();
		SupplierTypeBusiness typeBusiness = new SupplierTypeBusiness();
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
		// 得到该supplierType到祖先的所有list
		List<SupplierTypeBean> supplierTypeParentTreeList = typeBusiness.getSupplierTypeParentTreeListByKey(typeId);
		for (SupplierTypeBean supplierTypeBean : supplierTypeParentTreeList) {
			List<SupplierPropertyTypeBean> supplierPropertyTypeList = supplierPropertyTypeBusiness
					.getSupplierPropertyTypeListByTypeId(supplierTypeBean.getTypeId());
			for (SupplierPropertyTypeBean supplierPropertyTypeBean : supplierPropertyTypeList) {
				if (!count.containsKey(supplierPropertyTypeBean.getCategoryId())) {
					// 得到该CategoryId到祖先的所有list(去重)
					SupplierPropertyCategoryBean bean = supplierPropertyCategoryBusiness
							.getSupplierPropertyCategoryByKey(supplierPropertyTypeBean.getCategoryId());
					while (bean != null && !count.containsKey(bean.getCategoryId())) {
						count.put(bean.getCategoryId(), true);
						if (bean.getParentCategoryId() == 0) {
							// 只需要CategoryId的祖先
							propertyCategoryMap.put(bean.getCategoryId(), bean);
						}
						bean = supplierPropertyCategoryBusiness
								.getSupplierPropertyCategoryByKey(bean.getParentCategoryId());
					}
				}
			}
		}
		// 将Map转化为List集合，List采用ArrayList
		List<Map.Entry<Integer, SupplierPropertyCategoryBean>> list_Data = new ArrayList<Map.Entry<Integer, SupplierPropertyCategoryBean>>(
				propertyCategoryMap.entrySet());
		// 通过Collections.sort(List I,Comparator c)方法进行排序
		Collections.sort(list_Data, new Comparator<Map.Entry<Integer, SupplierPropertyCategoryBean>>() {
			@Override
			public int compare(java.util.Map.Entry<Integer, SupplierPropertyCategoryBean> o1,
					java.util.Map.Entry<Integer, SupplierPropertyCategoryBean> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().getPriority() - o2.getValue().getPriority();
			}
		});
		return list_Data;
	}

}