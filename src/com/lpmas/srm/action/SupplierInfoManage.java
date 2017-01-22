package com.lpmas.srm.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierPropertyBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierInfoManage
 */
@WebServlet("/srm/SupplierInfoManage.do")
public class SupplierInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		SupplierInfoBean bean = null;
		SupplierTypeBean typeBean = null;
		SupplierTypeBusiness typeBusiness = new SupplierTypeBusiness();
		SupplierPropertyBusiness supplierPropertyBusiness = new SupplierPropertyBusiness();
		if (supplierId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.SEARCH)) {
				return;
			}
			SupplierInfoBusiness business = new SupplierInfoBusiness();
			bean = business.getSupplierInfoByKey(supplierId);
			typeBean = typeBusiness.getSupplierTypeByKey(bean.getSupplierType());
			List<Map.Entry<Integer, SupplierPropertyCategoryBean>> propertyCategoryMapList = supplierPropertyBusiness
					.getSupplierPropertyCategoryMapByTypeId(bean.getSupplierType());
			request.setAttribute("propertyCategoryList", propertyCategoryMapList);
		} else {
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.CREATE)) {
				return;
			}
			int typeId = ParamKit.getIntParameter(request, "typeId", 0);
			typeBean = typeBusiness.getSupplierTypeByKey(typeId);
			if (typeBean == null) {
				HttpResponseKit.alertMessage(response, "供应商类型错误", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean = new SupplierInfoBean();
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("SupplierInfo", bean);
		request.setAttribute("SupplierType", typeBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);

		SupplierInfoBean bean = new SupplierInfoBean();
		try {
			bean = BeanKit.request2Bean(request, SupplierInfoBean.class);
			SupplierInfoBusiness business = new SupplierInfoBusiness();

			ReturnMessageBean messageBean = business.verifySupplierInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			int result = 0;
			if (bean.getSupplierId() > 0) {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateSupplierInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addSupplierInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/srm/SupplierInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
