package com.lpmas.srm.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.constant.info.InfoTypeConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.config.SupplierLogConfig;
import com.lpmas.srm.dao.SupplierAddressDao;
import com.lpmas.srm.util.SupplierLogSendHelper;

public class SupplierAddressBusiness {
	public int addSupplierAddress(SupplierAddressBean bean) {
		SupplierAddressDao dao = new SupplierAddressDao();
		int result = dao.insertSupplierAddress(bean);
		if (result > 0) {
			//把聚合的Bean写入Mongo
			SupplierAddressEntityBusiness entityBusiness = new SupplierAddressEntityBusiness();
			SupplierAddressEntityBean entityBean = new SupplierAddressEntityBean();
			entityBean.set_id(result);
			SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
			SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(bean.getSupplierId());
			entityBean.setAddressId(result);
			entityBean.setSupplierAddress(bean.getCompleteAddress());
			entityBean.setSupplierId(supplierInfoBean.getSupplierId());
			entityBean.setSupplierName(supplierInfoBean.getSupplierName());
			SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
			SupplierTypeBean typeBean = supplierTypeBusiness.getSupplierTypeByKey(supplierInfoBean.getSupplierType());
			entityBean.setSupplierTypeId(typeBean.getTypeId());
			entityBean.setSupplierTypeName(typeBean.getTypeName());
			entityBusiness.insertSupplierAddressEntity(entityBean);
			//日志记录
			bean.setAddressId(result);
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendAddLog(bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getAddressId(),
					SupplierLogConfig.LOG_SUPPLIER_ADDRESS);
		}
		return result;
	}

	public int updateSupplierAddress(SupplierAddressBean bean) {
		SupplierAddressDao dao = new SupplierAddressDao();
		SupplierAddressBean originalBean = dao.getSupplierAddressByKey(bean.getAddressId());
		int result = dao.updateSupplierAddress(bean);
		if (result > 0) {
			
			//把聚合的Bean写入Mongo
			SupplierAddressEntityBusiness entityBusiness = new SupplierAddressEntityBusiness();
			SupplierAddressEntityBean entityBean = new SupplierAddressEntityBean();
			entityBean.set_id(bean.getAddressId());
			SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
			SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(bean.getSupplierId());
			entityBean.setAddressId(bean.getAddressId());
			entityBean.setSupplierAddress(bean.getCompleteAddress());
			entityBean.setSupplierName(supplierInfoBean.getSupplierName());
			SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
			SupplierTypeBean typeBean = supplierTypeBusiness.getSupplierTypeByKey(supplierInfoBean.getSupplierType());
			entityBean.setSupplierTypeName(typeBean.getTypeName());
			entityBusiness.updateSupplierAddressEntity(entityBean);
			
			SupplierLogSendHelper helper = new SupplierLogSendHelper();
			helper.sendUpdateLog(originalBean, bean, InfoTypeConfig.INFO_TYPE_SUPPLIER, bean.getAddressId(),
					SupplierLogConfig.LOG_SUPPLIER_ADDRESS);
		}
		return result;
	}

	// 有更新操作，记录日志，清除缓存带上我
	public int resetDefaultSuppliserAddress(SupplierAddressBean bean, int adminUserId) {
		int result = 0;
		SupplierAddressDao dao = new SupplierAddressDao();
		result = dao.resetDefaultSuppliserAddress(bean, adminUserId);
		return result;
	}

	public SupplierAddressBean getSupplierAddressByKey(int addressId) {
		SupplierAddressDao dao = new SupplierAddressDao();
		return dao.getSupplierAddressByKey(addressId);
	}

	public PageResultBean<SupplierAddressBean> getSupplierAddressPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		SupplierAddressDao dao = new SupplierAddressDao();
		return dao.getSupplierAddressPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifySupplierAddressProperty(SupplierAddressBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getCountry().equals("")) {
			result.setMessage("[国家]缺失");
		} else if (bean.getProvince().equals("")) {
			result.setMessage("[省]缺失");
		} else if (bean.getCity().equals("")) {
			result.setMessage("[市]缺失");
		} else if (bean.getRegion().equals("")) {
			result.setMessage("[区]缺失");
		} else if (bean.getAddress().equals("")) {
			result.setMessage("[地址]缺失");
		} else if (bean.getContactName().equals("")) {
			result.setMessage("[联系人]缺失");
		} else if (bean.getZipCode().equals("")) {
			result.setMessage("[邮政编码]缺失");
		} else if (bean.getTelephone().equals("")) {
			result.setMessage("[电话号码]缺失");
		} else if (bean.getMobile().equals("")) {
			result.setMessage("[手机号码]缺失");
		}
		if (StringKit.isValid(result.getMessage())) {
			return result;
		}

		SupplierAddressBean orgBean = null;
		orgBean = getSupplierAddressByKey(bean.getAddressId());
		if (orgBean != null) {
			// 修改
			if (bean.getStatus() == Constants.STATUS_NOT_VALID && orgBean.getIsDefault() == Constants.STATUS_VALID) {
				result.setMessage("不能删除默认地址");
			}
			// 如果原来是默认地址，不可以改成不默认
			else if (orgBean.getIsDefault() == Constants.STATUS_VALID
					&& bean.getIsDefault() == Constants.STATUS_NOT_VALID) {
				result.setMessage("默认地址，不可以改成非默认");
			}
		} else {
			// 新增
			// 检查是否新建一个状态为不可以的地址
			if (bean.getStatus() == Constants.STATUS_NOT_VALID) {
				result.setMessage("不能新建一个非有效状态的地址");
			}
			// 获取当前供应商的地址信息
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("supplierId", String.valueOf(bean.getSupplierId()));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<SupplierAddressBean> addressList = getSupplierAddressListByMap(condMap);
			// 如果当前供应商没有地址，第一个是默认地址
			if (addressList.isEmpty()) {
				bean.setIsDefault(Constants.STATUS_VALID);
			}
		}
		return result;
	}

	public List<SupplierAddressBean> getSupplierAddressListByMap(HashMap<String, String> condMap) {
		SupplierAddressDao dao = new SupplierAddressDao();
		return dao.getSupplierAddressListByMap(condMap);
	}

}