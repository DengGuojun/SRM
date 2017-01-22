package com.lpmas.srm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.business.SupplierPropertyCategoryBusiness;

@WebServlet("/srm/SupplierPropertyCategoryJsonList.do")
public class SupplierPropertyCategoryJsonList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SupplierPropertyCategoryJsonList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int parentCategoryId = ParamKit.getIntParameter(request, "parentCategoryId", 0);
		SupplierPropertyCategoryBusiness business = new SupplierPropertyCategoryBusiness();
		List<SupplierPropertyCategoryBean> list = business.getSupplierPropertyCategoryListByParentId(parentCategoryId);
		if (list.size() > 0) {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			Map<String, List<SupplierPropertyCategoryBean>> map = new HashMap<String, List<SupplierPropertyCategoryBean>>();
			map.put("result", list);
			String result = JsonKit.toJson(map);
			writer.write(result);
			writer.flush();
			writer.close();
		}
	}
}
