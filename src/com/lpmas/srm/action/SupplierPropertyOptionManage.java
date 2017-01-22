package com.lpmas.srm.action;

import java.io.IOException;

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
import com.lpmas.srm.bean.SupplierPropertyOptionBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.business.SupplierPropertyOptionBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

@WebServlet("/srm/SupplierPropertyOptionManage.do")
public class SupplierPropertyOptionManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyOptionManage.class);
	private static final long serialVersionUID = 1L;

	public SupplierPropertyOptionManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int propertyOptionId = ParamKit.getIntParameter(request, "propertyOptionId", 0);
		SupplierPropertyOptionBean supplierPropertyOptionBean = new SupplierPropertyOptionBean();
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		if (!readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)) {
			return;
		}
		if (propertyOptionId > 0) {
			SupplierPropertyOptionBusiness business = new SupplierPropertyOptionBusiness();
			supplierPropertyOptionBean = business.getSupplierPropertyOptionByKey(propertyOptionId);
		} else {
			supplierPropertyOptionBean.setStatus(Constants.STATUS_VALID);
		}

		int propertyId = ParamKit.getIntParameter(request, "propertyId", 0);
		if (propertyId <= 0) {
			propertyId = supplierPropertyOptionBean.getPropertyId();
		}
		request.setAttribute("PropertyId", propertyId);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		SupplierPropertyTypeBean supplierPropertyTypeBean = supplierPropertyTypeBusiness
				.getSupplierPropertyTypeByKey(propertyId);
		request.setAttribute("supplierPropertyTypeBean", supplierPropertyTypeBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("supplierPropertyOptionBean", supplierPropertyOptionBean);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "/SupplierPropertyOptionManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int userId = adminUserHelper.getAdminUserId();
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)) {
			return;
		}
		SupplierPropertyOptionBean optionBean = new SupplierPropertyOptionBean();
		try {
			optionBean = BeanKit.request2Bean(request, SupplierPropertyOptionBean.class);
			SupplierPropertyOptionBusiness business = new SupplierPropertyOptionBusiness();
			ReturnMessageBean messageBean = business.verifySupplierPropertyOption(optionBean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			int result = 0;
			if (optionBean.getOptionId() > 0) {
				optionBean.setModifyUser(userId);
				result = business.updateSupplierPropertyOption(optionBean);
			} else {
				optionBean.setCreateUser(userId);
				result = business.addSupplierPropertyOption(optionBean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/srm/SupplierPropertyOptionList.do?propertyId=" + optionBean.getPropertyId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
