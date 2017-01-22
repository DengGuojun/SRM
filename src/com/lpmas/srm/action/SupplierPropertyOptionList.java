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
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.srm.bean.SupplierPropertyOptionBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.business.SupplierPropertyOptionBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

@WebServlet("/srm/SupplierPropertyOptionList.do")
public class SupplierPropertyOptionList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SupplierPropertyOptionList() {
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
		String propertyId = ParamKit.getParameter(request, "propertyId", "").trim();
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		if (StringKit.isValid(propertyId)) {
			SupplierPropertyTypeBean supplierPropertyTypeBean = supplierPropertyTypeBusiness
					.getSupplierPropertyTypeByKey(Integer.parseInt(propertyId));
			request.setAttribute("supplierPropertyTypeBean", supplierPropertyTypeBean);
		} else {
			HttpResponseKit.alertMessage(response, "供应商属性ID不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 处理查询条件
		HashMap<String, String> condMap = new HashMap<String, String>();
		if (StringKit.isValid(propertyId)) {
			condMap.put("propertyId", propertyId);
		}
		String optionValue = ParamKit.getParameter(request, "optionValue", "").trim();
		if (StringKit.isValid(optionValue)) {
			condMap.put("optionValue", optionValue);
		}
		String optionContent = ParamKit.getParameter(request, "optionContent", "").trim();
		if (StringKit.isValid(optionContent)) {
			condMap.put("optionContent", optionContent);
		}
		SupplierPropertyOptionBusiness business = new SupplierPropertyOptionBusiness();
		PageResultBean<SupplierPropertyOptionBean> result = business.getSupplierPropertyOptionPageListByMap(condMap,
				pageBean);
		request.setAttribute("PropertyOptionList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "/SupplierPropertyOptionList.jsp");
		rd.forward(request, response);
	}

}
