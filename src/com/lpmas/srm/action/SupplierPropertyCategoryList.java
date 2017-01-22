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

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class ProductTypeList
 */
@WebServlet("/srm/SupplierPropertyCategoryList.do")
public class SupplierPropertyCategoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierPropertyCategoryList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = new HashMap<String, String>();
		int parentCategoryId = ParamKit.getIntParameter(request, "parentCategoryId", 0);
		condMap.put("parentCategoryId", String.valueOf(parentCategoryId));
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.SEARCH)) {
			return;
		}
		String categoryCode = ParamKit.getParameter(request, "categoryCode", "").trim();
		if (StringKit.isValid(categoryCode)) {
			condMap.put("categoryCode", categoryCode);
		}
		String categoryName = ParamKit.getParameter(request, "categoryName", "").trim();
		if (StringKit.isValid(categoryName)) {
			condMap.put("categoryName", categoryName);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		SupplierPropertyCategoryBusiness business = new SupplierPropertyCategoryBusiness();
		PageResultBean<SupplierPropertyCategoryBean> result = business.getSupplierPropertyCategoryPageListByMap(
				condMap, pageBean);
		List<SupplierPropertyCategoryBean> parentTreeList = business.getParentTreeListByKey(parentCategoryId);
		SupplierPropertyCategoryBean parentCategoryBean = new SupplierPropertyCategoryBean();
		if (parentCategoryId > 0) {
			parentCategoryBean = business.getSupplierPropertyCategoryByKey(parentCategoryId);
		}
		request.setAttribute("ParentTreeList", parentTreeList);
		request.setAttribute("ParentCategoryBean", parentCategoryBean);
		request.setAttribute("SupplierPropertyCategoryList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("ParentCategoryId", parentCategoryId);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierPropertyCategoryList.jsp");
		rd.forward(request, response);
	}

}
