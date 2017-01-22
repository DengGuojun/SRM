package com.lpmas.srm.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierInfoEntityBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierInfoSelect
 */
@WebServlet("/srm/SupplierInfoSelect.do")
public class SupplierInfoSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierInfoSelect() {
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.SEARCH)) {
			return;
		}
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = new HashMap<String, String>();
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		String supplierName = ParamKit.getParameter(request, "queryParam", "").trim();
		if (StringKit.isValid(supplierName)) {
			condMap.put("supplierName", supplierName);
		}
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		SupplierInfoBusiness business = new SupplierInfoBusiness();
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		List<SupplierInfoEntityBean> list = new ArrayList<SupplierInfoEntityBean>();
		PageResultBean<SupplierInfoBean> result = business.getSupplierInfoPageListByMap(condMap, pageBean);
		for(SupplierInfoBean bean: result.getRecordList()){
			SupplierTypeBean typeBean = supplierTypeBusiness.getSupplierTypeByKey(bean.getSupplierType());
			SupplierInfoEntityBean entityBean = new SupplierInfoEntityBean(bean, typeBean);
			list.add(entityBean);
		}
		request.setAttribute("SupplierInfoEntityList", list);
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("SupplierId", supplierId);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierInfoSelect.jsp");
		rd.forward(request, response);
	}

}
