package com.lpmas.srm.action;

import java.io.IOException;
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
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.log.bean.DataLogBean;
import com.lpmas.log.client.LogServiceClient;
import com.lpmas.srm.config.SrmConfig;
import com.lpmas.srm.config.SrmResource;
import com.lpmas.system.bean.SysApplicationInfoBean;
import com.lpmas.system.client.cache.SysApplicationInfoClientCache;

@WebServlet("/srm/SupplierOperationLogManage.do")
public class SupplierOperationLogManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SupplierOperationLogManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String logId = ParamKit.getParameter(request, "logId", "");
		if (!StringKit.isValid(logId)) {
			HttpResponseKit.alertMessage(response, "日志ID缺失!", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SrmResource.SUPPLIER_LOG_INFO, OperationConfig.SEARCH)) {
			return;
		}
		// 查出对应的LOG BEAN
		LogServiceClient client = new LogServiceClient();
		DataLogBean dataLogBean = client.getDataLogBeanByKey(logId);

		// 从SYSTEM查数据
		SysApplicationInfoClientCache sysCache = new SysApplicationInfoClientCache();
		List<SysApplicationInfoBean> appList = sysCache.getSysApplicationInfoAllList();
		// 转化成Map
		Map<Integer, String> appMap = ListKit.list2Map(appList, "appId", "appName");

		// 放到页面
		request.setAttribute("DataLogBean", dataLogBean);
		request.setAttribute("ApplicationMap", appMap);
		// 请求转发
		RequestDispatcher rd = request.getRequestDispatcher(SrmConfig.PAGE_PATH + "SupplierOperationLogManage.jsp");
		rd.forward(request, response);
	}
}
