package com.lpmas.srm.action;

import java.io.IOException;
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
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierPropertyCategoryManage
 */
@WebServlet("/srm/SupplierPropertyCategoryManage.do")
public class SupplierPropertyCategoryManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyCategoryManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierPropertyCategoryManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int categoryId = ParamKit.getIntParameter(request, "categoryId", 0);
		int parentCategoryId = ParamKit.getIntParameter(request, "parentCategoryId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		SupplierPropertyCategoryBean bean = new SupplierPropertyCategoryBean();
		SupplierPropertyCategoryBusiness business = new SupplierPropertyCategoryBusiness();
		if (parentCategoryId < 0) {
			HttpResponseKit.alertMessage(response, "参数错误", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (categoryId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getSupplierPropertyCategoryByKey(categoryId);
			if (bean == null) {
				HttpResponseKit.alertMessage(response, "供应商属性类型不存在", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} else {
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}

		List<SupplierPropertyCategoryBean> parentTreeList = business.getParentTreeListByKey(parentCategoryId);
		request.setAttribute("ParentTreeList", parentTreeList);
		request.setAttribute("SupplierPropertyCategoryBean", bean);
		request.setAttribute("ParentCategoryId", parentCategoryId);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SrmConfig.PAGE_PATH + "SupplierPropertyCategoryManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);

		SupplierPropertyCategoryBean bean = new SupplierPropertyCategoryBean();
		try {
			bean = BeanKit.request2Bean(request, SupplierPropertyCategoryBean.class);
			SupplierPropertyCategoryBusiness business = new SupplierPropertyCategoryBusiness();

			ReturnMessageBean messageBean = business.verifySupplierPropertyCategoryProperty(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			int result = 0;
			if (bean.getCategoryId() > 0) {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateSupplierPropertyCategory(bean);
			} else {
				if (bean.getCategoryId() > 0
						&& !adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addSupplierPropertyCategory(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/srm/SupplierPropertyCategoryList.do?parentCategoryId=" + bean.getParentCategoryId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
