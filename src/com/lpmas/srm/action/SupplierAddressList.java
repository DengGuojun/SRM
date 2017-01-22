package com.lpmas.srm.action;

import java.io.IOException;
import java.util.HashMap;

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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.business.SupplierAddressBusiness;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierAddressList
 */
@WebServlet("/srm/SupplierAddressList.do")
public class SupplierAddressList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierAddressList() {
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
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.SEARCH)) {
			return;
		}
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		if (supplierId == 0) {
			HttpResponseKit.alertMessage(response, "供应商ID缺失", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
		SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(supplierId);
		if (supplierInfoBean == null) {
			HttpResponseKit.alertMessage(response, "供应商ID不合法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("supplierId", String.valueOf(supplierId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		SupplierAddressBusiness business = new SupplierAddressBusiness();
		PageResultBean<SupplierAddressBean> result = business.getSupplierAddressPageListByMap(condMap, pageBean);
		request.setAttribute("SupplierAddressList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("SupplierInfoBean", supplierInfoBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierAddressList.jsp");
		rd.forward(request, response);
	}

}
