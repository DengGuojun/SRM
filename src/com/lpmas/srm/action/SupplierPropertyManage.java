package com.lpmas.srm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierPropertyBean;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.bean.SupplierPropertyTypeBean;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierPropertyBusiness;
import com.lpmas.srm.business.SupplierPropertyTypeBusiness;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;
import com.lpmas.srm.config.SupplierPropertyTypeConfig;

@WebServlet("/srm/SupplierPropertyManage.do")
public class SupplierPropertyManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyManage.class);
	private static final long serialVersionUID = 1L;

	public SupplierPropertyManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		if (readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.SEARCH)) {
			return;
		}
		if (!readOnly && !adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.UPDATE)) {
			return;
		}
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		int categoryId = ParamKit.getIntParameter(request, "categoryId", 0);
		SupplierInfoBean bean = new SupplierInfoBean();
		SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
		bean = supplierInfoBusiness.getSupplierInfoByKey(supplierId);
		if (bean == null) {
			HttpResponseKit.alertMessage(response, "供应商信息缺失", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();

		// 获取动态属性值
		SupplierPropertyBusiness supplierPropertyBusiness = new SupplierPropertyBusiness();
		List<SupplierPropertyBean> propertyList = supplierPropertyBusiness
				.getSupplierPropertyListBySupplierId(supplierId);
		// 获得Map
		Map<String, SupplierPropertyBean> propertyTypeMap = ListKit.list2Map(propertyList, "propertyCode");

		// 获取最顶层categoryId
		List<Map.Entry<Integer, SupplierPropertyCategoryBean>> propertyCategoryMapList = supplierPropertyBusiness
				.getSupplierPropertyCategoryMapByTypeId(bean.getSupplierType());
		request.setAttribute("propertyCategoryList", propertyCategoryMapList);

		// 获取所有categoryId对应属性
		List<SupplierPropertyTypeBean> supplierPropertyTypeList = supplierPropertyTypeBusiness
				.getSupplierPropertyTypeTreeListByCantegoryIdAndTypeId(categoryId, bean.getSupplierType());
		// 获取动态属性的子属性
		Map<String, SupplierPropertyTypeBean> subPropertyTypeMap = new HashMap<String, SupplierPropertyTypeBean>();
		for (SupplierPropertyTypeBean propertyTypeBean : supplierPropertyTypeList) {
			if (propertyTypeBean.getParentPropertyId() == 0) {
				SupplierPropertyTypeBean subSupplierPropertyTypeBean = supplierPropertyTypeBusiness
						.getSupplierPropertyTypeByParentId(propertyTypeBean.getPropertyId());
				if (subSupplierPropertyTypeBean != null) {
					subPropertyTypeMap.put(propertyTypeBean.getPropertyCode(), subSupplierPropertyTypeBean);
				}
			}
		}

		request.setAttribute("SupplierInfo", bean);
		request.setAttribute("adminUserHelper", adminUserHelper);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("supplierPropertyTypeList", supplierPropertyTypeList);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("subPropertyTypeMap", subPropertyTypeMap);
		request.setAttribute("propertyList", propertyList);
		request.setAttribute("propertyTypeMap", propertyTypeMap);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierPropertyManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_INFO, OperationConfig.UPDATE)) {
			return;
		}
		int userId = adminUserHelper.getAdminUserId();
		int supplierId = ParamKit.getIntParameter(request, "supplierId", 0);
		int categoryId = ParamKit.getIntParameter(request, "categoryId", 0);

		// 根据供应商ID查到对应的供应商
		SupplierInfoBean supplierInfoBean = new SupplierInfoBean();
		SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
		supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(supplierId);
		// 获取属性类型列表
		int supplierTypeId = supplierInfoBean.getSupplierType();
		SupplierPropertyTypeBusiness supplierPropertyTypeBusiness = new SupplierPropertyTypeBusiness();
		List<SupplierPropertyTypeBean> supplierPropertyTypeList = supplierPropertyTypeBusiness
				.getSupplierPropertyTypeTreeListByCantegoryIdAndTypeId(categoryId, supplierTypeId);
		// 转化成Map
		Map<Integer, SupplierPropertyTypeBean> supplierPropertyTypeMap = new HashMap<Integer, SupplierPropertyTypeBean>();
		try {
			// 从REQUEST中获取参数值Map
			// KEY是propery_code
			Map<Integer, String> parameterMap = new HashMap<Integer, String>();
			Map<String, String> subParameterMap = new HashMap<String, String>();
			Map<Integer, String> subParameterMap4Validate = new HashMap<Integer, String>();
			for (SupplierPropertyTypeBean propertyTypeBean : supplierPropertyTypeList) {
				if (propertyTypeBean.getParentPropertyId() == SrmConfig.ROOT_PARENT_ID) {
					if (propertyTypeBean.getInputMethod() == SupplierPropertyTypeConfig.INPUT_METHOD_CHECKBOX) {
						parameterMap.put(propertyTypeBean.getPropertyId(),
								ParamKit.getParameter(request, "PROPERTY_" + propertyTypeBean.getPropertyCode(), "0"));
					} else {
						parameterMap.put(propertyTypeBean.getPropertyId(),
								ParamKit.getParameter(request, "PROPERTY_" + propertyTypeBean.getPropertyCode(), ""));
					}
				} else {
					String subValue = ParamKit.getParameter(request,
							"SUB_PROPERTY_" + propertyTypeBean.getPropertyCode(), null);
					if (subValue != null) {
						subParameterMap.put(propertyTypeBean.getPropertyCode(), subValue);
						subParameterMap4Validate.put(propertyTypeBean.getPropertyId(), subValue);
					}
				}

				supplierPropertyTypeMap.put((Integer) propertyTypeBean.getPropertyId(), propertyTypeBean);

			}

			// 服务端数据验证
			// 校验供应商动态属性值格式
			SupplierPropertyBusiness supplierPropertyBusiness = new SupplierPropertyBusiness();
			ReturnMessageBean messageBean = new ReturnMessageBean();
			messageBean = supplierPropertyBusiness.verifySupplierInfoProperty(parameterMap);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 子属性验证
			messageBean = new ReturnMessageBean();
			messageBean = supplierPropertyBusiness.verifySupplierInfoProperty(subParameterMap4Validate);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}

			// 更新供应商动态基本属性类型
			// 查询判断是更新还是新建插入
			int result = 0;
			Set<Integer> keySet = parameterMap.keySet();
			for (Integer propertyId : keySet) {
				String propertyValue = parameterMap.get(propertyId);
				SupplierPropertyBean bean = null;
				bean = supplierPropertyBusiness.getSupplierPropertyByKey(supplierId,
						supplierPropertyTypeMap.get(propertyId).getPropertyCode());
				if (bean != null) {
					// 更新
					bean.setModifyUser(userId);
					bean.setPropertyValue1(propertyValue);
					bean.setPropertyValue2(subParameterMap.get(bean.getPropertyCode()));
					result = supplierPropertyBusiness.updateSupplierProperty(bean);
				} else if (StringKit.isValid(propertyValue.trim())) {
					// 插入
					// 只有当PROPERTYVALUE1不是空串时插入数据库
					bean = new SupplierPropertyBean();
					bean.setSupplierId(supplierId);
					bean.setCreateUser(userId);
					bean.setPropertyCode(supplierPropertyTypeMap.get(propertyId).getPropertyCode());
					bean.setPropertyValue1(propertyValue);
					bean.setPropertyValue2(subParameterMap.get(bean.getPropertyCode()));
					result = supplierPropertyBusiness.addSupplierProperty(bean);
				}
				if (!(result >= 0)) {
					// 操作中出现异常
					HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
					break;
				}
			}
			if (result >= 0)
				HttpResponseKit.alertMessage(response, "处理成功", "/srm/SupplierInfoList.do");

		} catch (Exception e) {
			log.error("", e);
		}

	}

}
