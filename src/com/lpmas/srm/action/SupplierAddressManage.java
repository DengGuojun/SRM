package com.lpmas.srm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.region.bean.CountryInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.business.SupplierAddressBusiness;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;
import com.lpmas.srm.config.SupplierAddressConfig;

/**
 * Servlet implementation class SupplierAddressManage
 */
@WebServlet("/srm/SupplierAddressManage.do")
public class SupplierAddressManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierAddressManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierAddressManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int addressId = ParamKit.getIntParameter(request, "addressId", 0);
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		boolean isFirstAddress = false;
		SupplierAddressBean bean = new SupplierAddressBean();
		SupplierAddressBusiness business = new SupplierAddressBusiness();
		if (addressId > 0) {
			// 是修改
			if (!readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getSupplierAddressByKey(addressId);
			supplierId = bean.getSupplierId();
		} else {
			// 是新增
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.CREATE)) {
				return;
			}
			// 获取当前供应商的地址信息
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("supplierId", String.valueOf(supplierId));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<SupplierAddressBean> addressList = business.getSupplierAddressListByMap(condMap);
			// 首个地址自动成为默认地址
			if (addressList.isEmpty()) {
				bean.setIsDefault(Constants.STATUS_VALID);
				isFirstAddress = true;
			}
			bean.setSupplierId(supplierId);
			bean.setStatus(Constants.STATUS_VALID);
		}
		SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
		SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(Integer.valueOf(supplierId));
		if (supplierInfoBean == null) {
			HttpResponseKit.alertMessage(response, "供应商ID不合法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取国家ID
		RegionServiceClient client = new RegionServiceClient();
		List<CountryInfoBean> countryList = client.getCountryInfoAllList();
		CountryInfoBean countryBeanChina = new CountryInfoBean();
		for (CountryInfoBean country : countryList) {
			if (country.getCountryCode().equals(SupplierAddressConfig.REGION_COUNTRY_CODE_CHINA)) {
				countryBeanChina = country;
				break;
			}
		}
		request.setAttribute("CountryName", countryBeanChina.getCountryName());
		request.setAttribute("SupplierAddress", bean);
		request.setAttribute("SupplierInfo", supplierInfoBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("isFirstAddress", isFirstAddress);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierAddressManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		SupplierAddressBean bean = new SupplierAddressBean();
		try {
			bean = BeanKit.request2Bean(request, SupplierAddressBean.class);
			// 验证SUPPLIER是否存在
			SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
			SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(bean.getSupplierId());
			if (supplierInfoBean == null) {
				HttpResponseKit.alertMessage(response, "供应商ID不合法", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 验证字段
			SupplierAddressBusiness business = new SupplierAddressBusiness();
			ReturnMessageBean messageBean = business.verifySupplierAddressProperty(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			// 开始实际数据库写操作
			int result = 0;
			if (bean.getAddressId() > 0) {
				// 更新
				// 检查权限
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateSupplierAddress(bean);
			} else {
				// 新建
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addSupplierAddress(bean);
				if (result > 0) {
					bean.setAddressId(result);
				}
			}
			// 当该地址为默认地址时，需要更新SupplierInfoBean的地址为此地址
			// 并且把其他地址都变成非默认地址
			if (result > 0 && bean.getIsDefault() == Constants.STATUS_VALID) {
				supplierInfoBean.setCountry(bean.getCountry());
				supplierInfoBean.setProvince(bean.getProvince());
				supplierInfoBean.setCity(bean.getCity());
				supplierInfoBean.setRegion(bean.getRegion());
				supplierInfoBean.setAddress(bean.getAddress());
				supplierInfoBean.setContactName(bean.getContactName());
				supplierInfoBean.setZipCode(bean.getZipCode());
				supplierInfoBean.setTelephone(bean.getTelephone());
				supplierInfoBean.setMobile(bean.getMobile());
				supplierInfoBean.setFax(bean.getFax());
				supplierInfoBean.setModifyUser(adminUserHelper.getAdminUserId());
				result = supplierInfoBusiness.updateSupplierInfo(supplierInfoBean);

				if (result > 0) {
					result = business.resetDefaultSuppliserAddress(bean, adminUserHelper.getAdminUserId());
				}
			}

			if (result >= 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"SupplierAddressList.do?supplierId=" + bean.getSupplierId());
				return;
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} catch (Exception e) {
			log.error("", e);
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}
}
