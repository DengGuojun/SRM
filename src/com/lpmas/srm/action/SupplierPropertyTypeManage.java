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
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

@WebServlet("/srm/SupplierPropertyTypeManage.do")
public class SupplierPropertyTypeManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyTypeManage.class);
	private static final long serialVersionUID = 1L;

	public SupplierPropertyTypeManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int propertyId = ParamKit.getIntParameter(request, "propertyId", 0);
		int parentPropertyId = ParamKit.getIntParameter(request, "parentPropertyId", 0);
		SupplierPropertyTypeBean bean = new SupplierPropertyTypeBean();
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		SupplierPropertyTypeBusiness business = new SupplierPropertyTypeBusiness();
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
		if (propertyId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getSupplierPropertyTypeByKey(propertyId);
			parentPropertyId = bean.getParentPropertyId();
			SupplierTypeBean supplierTypeBean = supplierTypeBusiness.getSupplierTypeByKey(bean.getSupplierTypeId());
			SupplierPropertyCategoryBean supplierPropertyCategoryBean = supplierPropertyCategoryBusiness
					.getSupplierPropertyCategoryByKey(bean.getCategoryId());
			request.setAttribute("supplierTypeBean", supplierTypeBean);
			request.setAttribute("supplierPropertyCategoryBean", supplierPropertyCategoryBean);
		} else {
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
			bean.setIsModifiable(Constants.STATUS_VALID);
		}
		if (parentPropertyId > 0) {
			SupplierPropertyTypeBean parentPropertyTypeBean = business.getSupplierPropertyTypeByKey(parentPropertyId);
			request.setAttribute("ParentPropertyTypeBean", parentPropertyTypeBean);
		}

		request.setAttribute("ParentPropertyId", parentPropertyId);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("PropertyTypeBean", bean);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "/SupplierPropertyTypeManage.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SupplierPropertyTypeBean propertyTypeBean = new SupplierPropertyTypeBean();
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		try {
			propertyTypeBean = BeanKit.request2Bean(request, SupplierPropertyTypeBean.class);
			SupplierPropertyTypeBusiness business = new SupplierPropertyTypeBusiness();
			int result = 0;
			int userId = adminUserHelper.getAdminUserId();

			ReturnMessageBean messageBean = business.verifySupplierPropertyType(propertyTypeBean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (propertyTypeBean.getPropertyId() > 0) {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)) {
					return;
				}
				if (propertyTypeBean.getStatus() == Constants.STATUS_NOT_VALID) {
					// 有子属性不能删
					if (business.getSupplierPropertyTypeByParentId(propertyTypeBean.getPropertyId()) != null) {
						HttpResponseKit.alertMessage(response, "已经存在子属性,不能删除父属性", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
				}

				propertyTypeBean.setModifyUser(userId);
				result = business.updateSupplierPropertyType(propertyTypeBean);
			} else {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.CREATE)) {
					return;
				}
				propertyTypeBean.setCreateUser(userId);
				result = business.addSupplierPropertyType(propertyTypeBean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/srm/SupplierPropertyTypeList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}
}
