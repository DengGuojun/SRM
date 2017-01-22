package com.lpmas.srm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierInfoList
 */
@WebServlet("/srm/SupplierInfoList.do")
public class SupplierInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierInfoList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.SEARCH)) {
			return;
		}
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = new HashMap<String, String>();
		String supplierName = ParamKit.getParameter(request, "supplierName", "").trim();
		if (StringKit.isValid(supplierName)) {
			condMap.put("supplierName", supplierName);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		Map<Integer, List<SupplierTypeBean>> supplierTypeTreeMap = new HashMap<Integer, List<SupplierTypeBean>>();
		SupplierInfoBusiness business = new SupplierInfoBusiness();
		PageResultBean<SupplierInfoBean> result = business.getSupplierInfoPageListByMap(condMap, pageBean);
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		for (SupplierInfoBean supplierInfoBean : result.getRecordList()) {
			List<SupplierTypeBean> supplierTreeList = supplierTypeBusiness
					.getSupplierTypeParentTreeListByKey(supplierInfoBean.getSupplierType());
			supplierTypeTreeMap.put(supplierInfoBean.getSupplierType(), supplierTreeList);
		}
		request.setAttribute("supplierTypeTreeMap", supplierTypeTreeMap);
		request.setAttribute("SupplierInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierInfoList.jsp");
		rd.forward(request, response);
	}

}
