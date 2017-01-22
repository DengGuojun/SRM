package com.lpmas.srm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.srm.bean.SupplierInfoBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierInfoDao {
	private static Logger log = LoggerFactory.getLogger(SupplierInfoDao.class);

	public int insertSupplierInfo(SupplierInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_info ( supplier_name, supplier_type, email, country, province, city, region, address, contact_name, zip_code, telephone, mobile, fax, supplier_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getSupplierName());
			ps.setInt(2, bean.getSupplierType());
			ps.setString(3, bean.getEmail());
			ps.setString(4, bean.getCountry());
			ps.setString(5, bean.getProvince());
			ps.setString(6, bean.getCity());
			ps.setString(7, bean.getRegion());
			ps.setString(8, bean.getAddress());
			ps.setString(9, bean.getContactName());
			ps.setString(10, bean.getZipCode());
			ps.setString(11, bean.getTelephone());
			ps.setString(12, bean.getMobile());
			ps.setString(13, bean.getFax());
			ps.setString(14, bean.getSupplierStatus());
			ps.setInt(15, bean.getStatus());
			ps.setInt(16, bean.getCreateUser());
			ps.setString(17, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateSupplierInfo(SupplierInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_info set supplier_name = ?, supplier_type = ?, email = ?, country = ?, province = ?, city = ?, region = ?, address = ?, contact_name = ?, zip_code = ?, telephone = ?, mobile = ?, fax = ?, supplier_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where supplier_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getSupplierName());
			ps.setInt(2, bean.getSupplierType());
			ps.setString(3, bean.getEmail());
			ps.setString(4, bean.getCountry());
			ps.setString(5, bean.getProvince());
			ps.setString(6, bean.getCity());
			ps.setString(7, bean.getRegion());
			ps.setString(8, bean.getAddress());
			ps.setString(9, bean.getContactName());
			ps.setString(10, bean.getZipCode());
			ps.setString(11, bean.getTelephone());
			ps.setString(12, bean.getMobile());
			ps.setString(13, bean.getFax());
			ps.setString(14, bean.getSupplierStatus());
			ps.setInt(15, bean.getStatus());
			ps.setInt(16, bean.getModifyUser());
			ps.setString(17, bean.getMemo());

			ps.setInt(18, bean.getSupplierId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public SupplierInfoBean getSupplierInfoByKey(int supplierId) {
		SupplierInfoBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_info where supplier_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, supplierId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierInfoBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public PageResultBean<SupplierInfoBean> getSupplierInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<SupplierInfoBean> result = new PageResultBean<SupplierInfoBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String supplierName = condMap.get("supplierName");
			if (StringKit.isValid(supplierName)) {
				condList.add("supplier_name like ?");
				paramList.add("%" + supplierName + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by supplier_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierInfoBean.class, pageBean,
					db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public List<SupplierInfoBean> getSupplierInfoListByMap(HashMap<String, String> condMap) {
		List<SupplierInfoBean> list = new ArrayList<SupplierInfoBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String supplierId = condMap.get("supplierId");
			if (StringKit.isValid(supplierId)) {
				condList.add("supplier_id = ?");
				paramList.add(supplierId);
			}
			String supplierType = condMap.get("supplierType");
			if (StringKit.isValid(supplierType)) {
				condList.add("supplier_type = ?");
				paramList.add(supplierType);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by supplier_id asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, SupplierInfoBean.class, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return list;
	}
	
}
