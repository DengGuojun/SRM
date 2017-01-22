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
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

@WebServlet("/srm/SupplierPropertyTypeList.do")
public class SupplierPropertyTypeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SupplierPropertyTypeList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.SEARCH)) {
			return;
		}
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SrmConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SrmConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		// 处理查询条件
		HashMap<String, String> condMap = new HashMap<String, String>();
		int parentPropertyId = ParamKit.getIntParameter(request, "parentPropertyId", 0);
		String propertyCode = ParamKit.getParameter(request, "propertyCode", "").trim();
		condMap.put("parentPropertyId", String.valueOf(parentPropertyId));
		if (StringKit.isValid(propertyCode)) {
			condMap.put("propertyCode", propertyCode);
		}
		String propertyName = ParamKit.getParameter(request, "propertyName", "").trim();
		if (StringKit.isValid(propertyName)) {
			condMap.put("propertyName", propertyName);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		int supplierType = ParamKit.getIntParameter(request, "supplierType", 0);
		if (supplierType != 0) {
			condMap.put("supplierType", String.valueOf(supplierType));
		}
		SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
		int supplierPropertyCategory = ParamKit.getIntParameter(request, "supplierPropertyCategory", 0);
		if (supplierPropertyCategory != 0) {
			condMap.put("supplierPropertyCategory", String.valueOf(supplierPropertyCategory));
		}
		SupplierPropertyTypeBusiness business = new SupplierPropertyTypeBusiness();
		PageResultBean<SupplierPropertyTypeBean> result = business.getSupplierPropertyTypePageListByMap(condMap,
				pageBean);
		if (parentPropertyId > 0) {
			SupplierPropertyTypeBean parentPropertyBean = business.getSupplierPropertyTypeByKey(parentPropertyId);
			request.setAttribute("ParentPropertyBean", parentPropertyBean);
		}
		Map<Integer, List<SupplierTypeBean>> supplierTypeTreeMap = new HashMap<Integer, List<SupplierTypeBean>>();
		Map<Integer, List<SupplierPropertyCategoryBean>> supplierPropertyCategoryTreeMap = new HashMap<Integer, List<SupplierPropertyCategoryBean>>();
		for (SupplierPropertyTypeBean supplierPropertyTypeBean : result.getRecordList()) {
			List<SupplierTypeBean> supplierTreeList = supplierTypeBusiness
					.getSupplierTypeParentTreeListByKey(supplierPropertyTypeBean.getSupplierTypeId());
			List<SupplierPropertyCategoryBean> supplierPropertyCategoryTreeList = supplierPropertyCategoryBusiness
					.getParentTreeListByKey(supplierPropertyTypeBean.getCategoryId());
			supplierTypeTreeMap.put(supplierPropertyTypeBean.getSupplierTypeId(), supplierTreeList);
			supplierPropertyCategoryTreeMap.put(supplierPropertyTypeBean.getCategoryId(),
					supplierPropertyCategoryTreeList);
		}

		request.setAttribute("supplierTypeTreeMap", supplierTypeTreeMap);
		request.setAttribute("supplierPropertyCategoryTreeMap", supplierPropertyCategoryTreeMap);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("PropertyTypeList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("ParentPropertyId", parentPropertyId);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "/SupplierPropertyTypeList.jsp");
		rd.forward(request, response);
	}

}
