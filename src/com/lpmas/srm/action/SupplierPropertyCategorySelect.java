package com.lpmas.srm.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

@WebServlet("/srm/SupplierPropertyCategorySelect.do")
public class SupplierPropertyCategorySelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SupplierPropertyCategorySelect() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.SEARCH)) {
			return;
		}
		SupplierPropertyCategoryBusiness business = new SupplierPropertyCategoryBusiness();
		List<SupplierPropertyCategoryBean> topSupplierPropertyCategoryList = business
				.getSupplierPropertyCategoryListByParentId(SrmConfig.ROOT_PARENT_ID);
		request.setAttribute("topSupplierPropertyCategoryList", topSupplierPropertyCategoryList);
		RequestDispatcher rd = request
				.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierPropertyCategorySelect.jsp");
		rd.forward(request, response);
	}

}
