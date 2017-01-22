package com.lpmas.srm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.srm.bean.SupplierAddressBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierAddressDao {
	private static Logger log = LoggerFactory.getLogger(SupplierAddressDao.class);

	public int insertSupplierAddress(SupplierAddressBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_address ( supplier_id, country, province, city, region, address, contact_name, zip_code, telephone, mobile, fax, is_default, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getSupplierId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getContactName());
			ps.setString(8, bean.getZipCode());
			ps.setString(9, bean.getTelephone());
			ps.setString(10, bean.getMobile());
			ps.setString(11, bean.getFax());
			ps.setInt(12, bean.getIsDefault());
			ps.setInt(13, bean.getStatus());
			ps.setInt(14, bean.getCreateUser());
			ps.setString(15, bean.getMemo());

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

	public int updateSupplierAddress(SupplierAddressBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_address set supplier_id = ?, country = ?, province = ?, city = ?, region = ?, address = ?, contact_name = ?, zip_code = ?, telephone = ?, mobile = ?, fax = ?, is_default = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where address_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getSupplierId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getContactName());
			ps.setString(8, bean.getZipCode());
			ps.setString(9, bean.getTelephone());
			ps.setString(10, bean.getMobile());
			ps.setString(11, bean.getFax());
			ps.setInt(12, bean.getIsDefault());
			ps.setInt(13, bean.getStatus());
			ps.setInt(14, bean.getModifyUser());
			ps.setString(15, bean.getMemo());

			ps.setInt(16, bean.getAddressId());

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

	public int resetDefaultSuppliserAddress(SupplierAddressBean bean, int adminUserId) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_address set  is_default = ?, modify_time = now(), modify_user = ? where is_default = ? and supplier_id=? and address_id <> ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, Constants.STATUS_NOT_VALID);
			ps.setInt(2, adminUserId);
			ps.setInt(3, Constants.STATUS_VALID);
			ps.setInt(4, bean.getSupplierId());
			ps.setInt(5, bean.getAddressId());

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

	public SupplierAddressBean getSupplierAddressByKey(int addressId) {
		SupplierAddressBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_address where address_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, addressId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierAddressBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierAddressBean.class);
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

	public PageResultBean<SupplierAddressBean> getSupplierAddressPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<SupplierAddressBean> result = new PageResultBean<SupplierAddressBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_address";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String supplierId = condMap.get("supplierId");
			if (StringKit.isValid(supplierId)) {
				condList.add("supplier_id = ?");
				paramList.add(supplierId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by supplier_id asc,address_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierAddressBean.class, pageBean,
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

	public List<SupplierAddressBean> getSupplierAddressListByMap(HashMap<String, String> condMap) {
		List<SupplierAddressBean> result = new ArrayList<SupplierAddressBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_address";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String supplierId = condMap.get("supplierId");
			if (StringKit.isValid(supplierId)) {
				condList.add("supplier_id = ?");
				paramList.add(supplierId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by address_id asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, SupplierAddressBean.class,
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

}
