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
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;

/**
 * Servlet implementation class SupplierTypeManage
 */
@WebServlet("/srm/SupplierTypeManage.do")
public class SupplierTypeManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierTypeManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int typeId = ParamKit.getIntParameter(request, "typeId", 0);
		int parentTypeId = ParamKit.getIntParameter(request, "parentTypeId", 0);
		SupplierTypeBean typeBean = null;

		SupplierTypeBusiness business = new SupplierTypeBusiness();
		// 判断新建还是修改
		if (typeId > 0) {
			// 是修改
			if (!readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.SEARCH)) {
				return;
			}

			// 查出对应的BEAN
			typeBean = business.getSupplierTypeByKey(typeId);
			if (typeBean == null) {
				HttpResponseKit.alertMessage(response, "供应商类型不存在", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} else {
			// 是新增
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.CREATE)) {
				return;
			}
			typeBean = new SupplierTypeBean();
			typeBean.setStatus(Constants.STATUS_VALID);
		}
		// 获取爸爸的列表
		List<SupplierTypeBean> parentTreeList = business.getSupplierTypeParentTreeListByKey(parentTypeId);
		request.setAttribute("ParentTreeList", parentTreeList);
		request.setAttribute("SupplierTypeBean", typeBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierTypeManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		SupplierTypeBean bean = new SupplierTypeBean();
		SupplierTypeBusiness business = new SupplierTypeBusiness();
		try {
			int result = 0;
			bean = BeanKit.request2Bean(request, SupplierTypeBean.class);
			// 参数验证
			// 权限验证
			if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.SEARCH)) {
				return;
			}
			ReturnMessageBean messageBean = business.validateSupplierTypeBean(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (bean.getTypeId() > 0) {
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.UPDATE)) {
					return;
				}
				// 修改
				if (bean.getStatus() == Constants.STATUS_NOT_VALID) {
					// 有子类型不能删
					if (business.getSupplierTypeListByParentId(bean.getTypeId()).size() > 0) {
						HttpResponseKit.alertMessage(response, "已经存在子类型,不能删除父类型", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					// 有对应的供应商不能删
					SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
					HashMap<String, String> infoCondMap = new HashMap<String, String>();
					infoCondMap.put("supplierType", String.valueOf(bean.getTypeId()));
					infoCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
					List<SupplierInfoBean> supplierInfoList = supplierInfoBusiness
							.getSupplierInfoListByMap(infoCondMap);
					if (!supplierInfoList.isEmpty()) {
						HttpResponseKit.alertMessage(response, "已经存在对应的供应商，不能删除该供应商类型",
								HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					// 该类型有属性也不能删
					SupplierPropertyTypeBusiness propertyTypeBusiness = new SupplierPropertyTypeBusiness();
					HashMap<String, String> condMap = new HashMap<String, String>();
					condMap.put("supplierTypeId", String.valueOf(bean.getTypeId()));
					condMap.put("status", String.valueOf(Constants.STATUS_VALID));
					List<SupplierPropertyTypeBean> propertyTypeList = propertyTypeBusiness
							.getSupplierPropertyTypeListByMap(condMap);
					if (!propertyTypeList.isEmpty()) {
						HttpResponseKit.alertMessage(response, "已经存在对应的属性类型，不能删除该供应商类型",
								HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateSupplierType(bean);
			} else {
				// 新增
				// 权限验证
				if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addSupplierType(bean);
			}

			if (result == 0) {
				HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "操作成功",
						"/srm/SupplierTypeList.do?parentTypeId=" + bean.getParentTypeId());
				return;
			}
		} catch (Exception e) {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}

}
