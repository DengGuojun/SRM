package com.lpmas.srm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.bean.SupplierAddressEntityBean;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.business.SupplierAddressBusiness;
import com.lpmas.srm.business.SupplierAddressEntityBusiness;
import com.lpmas.srm.business.SupplierInfoBusiness;
import com.lpmas.srm.business.SupplierTypeBusiness;

/**
 * Servlet implementation class SupplierAddressSync
 */
@WebServlet("/srm/SupplierAddressSync.do")
public class SupplierAddressSync extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierAddressSync() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SupplierInfoBusiness supplierInfoBusiness = new SupplierInfoBusiness();
		SupplierAddressEntityBusiness entityBusiness = new SupplierAddressEntityBusiness();
		SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
		SupplierAddressBusiness supplierAddressBusiness = new SupplierAddressBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		
		List<SupplierAddressBean> list = supplierAddressBusiness.getSupplierAddressListByMap(condMap);
		for(SupplierAddressBean bean : list){
			SupplierInfoBean supplierInfoBean = supplierInfoBusiness.getSupplierInfoByKey(bean.getSupplierId());
			//把聚合的Bean写入Mongo
			SupplierAddressEntityBean entityBean = new SupplierAddressEntityBean();
			entityBean.set_id(bean.getAddressId());
			entityBean.setAddressId(bean.getAddressId());
			entityBean.setSupplierAddress(bean.getCompleteAddress());
			entityBean.setSupplierName(supplierInfoBean.getSupplierName());
			entityBean.setSupplierId(supplierInfoBean.getSupplierId());
			SupplierTypeBean typeBean = supplierTypeBusiness.getSupplierTypeByKey(supplierInfoBean.getSupplierType());
			entityBean.setSupplierTypeName(typeBean.getTypeName());
			entityBean.setSupplierTypeId(typeBean.getTypeId());
			
			try {
				SupplierAddressEntityBean existBean = entityBusiness.getSupplierAdressEntity(bean.getAddressId());
				if(existBean==null){
					entityBusiness.insertSupplierAddressEntity(entityBean);
				}else{
					entityBusiness.updateSupplierAddressEntity(entityBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
