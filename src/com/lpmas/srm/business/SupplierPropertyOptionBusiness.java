package com.lpmas.srm.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierPropertyOptionBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierPropertyOptionDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierPropertyOptionBusiness {
	public int addSupplierPropertyOption(SupplierPropertyOptionBean bean) {
		SupplierPropertyOptionDao dao = new SupplierPropertyOptionDao();
		int result = dao.insertSupplierPropertyOption(bean);
		if (result > 0) {
			bean.setOptionId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getOptionId(), 0, 0,
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_TYPE_OPTION);
		}
		return result;
	}

	public int updateSupplierPropertyOption(SupplierPropertyOptionBean bean) {
		SupplierPropertyOptionDao dao = new SupplierPropertyOptionDao();
		SupplierPropertyOptionBean originalBean = dao.getSupplierPropertyOptionByKey(bean.getOptionId());
		int result = dao.updateSupplierPropertyOption(bean);
		if (result > 0) {
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getOptionId(),
					SupplierLogConfig.LOG_SUPPLIER_PROPERTY_TYPE_OPTION);
		}
		return result;
	}

	public SupplierPropertyOptionBean getSupplierPropertyOptionByKey(int optionId) {
		SupplierPropertyOptionDao dao = new SupplierPropertyOptionDao();
		return dao.getSupplierPropertyOptionByKey(optionId);
	}

	public List<SupplierPropertyOptionBean> getSupplierPropertyOptionListByPropertyId(int propertyId) {
		SupplierPropertyOptionDao dao = new SupplierPropertyOptionDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("propertyId", String.valueOf(propertyId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getSupplierPropertyOptionListByMap(condMap);
	}

	public PageResultBean<SupplierPropertyOptionBean> getSupplierPropertyOptionPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		SupplierPropertyOptionDao dao = new SupplierPropertyOptionDao();
		return dao.getSupplierPropertyOptionPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifySupplierPropertyOption(SupplierPropertyOptionBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getOptionContent())) {
			result.setMessage("属性选项显示内容必须填写");
		} else if (!StringKit.isValid(bean.getOptionValue())) {
			result.setMessage("属性选项值必须填写");
		}

		return result;
	}

}