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
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierTypeList
 */
@WebServlet("/srm/SupplierTypeList.do")
public class SupplierTypeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierTypeList() {
		super();
		// TODO Auto-generated constructor stub
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
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.SEARCH)) {
			return;
		}
		// 初始化页面分页参数
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 获取页面请求参数
		// 插入查询参数MAP
		HashMap<String, String> condMap = new HashMap<String, String>();
		int parentTypeId = ParamKit.getIntParameter(request, "parentTypeId", 0);
		condMap.put("parentTypeId", String.valueOf(parentTypeId));
		String typeCode = ParamKit.getParameter(request, "typeCode", "").trim();
		if (StringKit.isValid(typeCode)) {
			condMap.put("typeCode", typeCode);
		}
		String typeName = ParamKit.getParameter(request, "typeName", "").trim();
		if (StringKit.isValid(typeName)) {
			condMap.put("typeName", typeName);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		// 从数据库中获取相应的数据
		SupplierTypeBusiness business = new SupplierTypeBusiness();
		PageResultBean<SupplierTypeBean> result = business.getSupplierTypePageListByMap(condMap, pageBean);
		// 获取爸爸的列表
		List<SupplierTypeBean> parentTreeList = business.getSupplierTypeParentTreeListByKey(parentTypeId);
		// 把数据放到页面
		request.setAttribute("SupplierTypeList", result.getRecordList());
		request.setAttribute("ParentTreeList", parentTreeList);
		// 初始化分页数据
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierTypeList.jsp");
		rd.forward(request, response);
	}

}
