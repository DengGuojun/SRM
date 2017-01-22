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
import com.lpmas.srm.bean.SupplierPropertyBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierPropertyDao {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyDao.class);

	public int insertSupplierProperty(SupplierPropertyBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_property ( supplier_id, property_code, property_value_1, property_value_2, property_value_3, create_time, create_user) value( ?, ?, ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getSupplierId());
			ps.setString(2, bean.getPropertyCode());
			ps.setString(3, bean.getPropertyValue1());
			ps.setString(4, bean.getPropertyValue2());
			ps.setString(5, bean.getPropertyValue3());
			ps.setInt(6, bean.getCreateUser());

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

	public int updateSupplierProperty(SupplierPropertyBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_property set property_value_1 = ?, property_value_2 = ?, property_value_3 = ?, modify_time = now(), modify_user = ? where supplier_id = ? and property_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getPropertyValue1());
			ps.setString(2, bean.getPropertyValue2());
			ps.setString(3, bean.getPropertyValue3());
			ps.setInt(4, bean.getModifyUser());

			ps.setInt(5, bean.getSupplierId());
			ps.setString(6, bean.getPropertyCode());

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

	public SupplierPropertyBean getSupplierPropertyByKey(int supplierId, String propertyCode) {
		SupplierPropertyBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property where supplier_id = ? and property_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, supplierId);
			ps.setString(2, propertyCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierPropertyBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierPropertyBean.class);
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

	public PageResultBean<SupplierPropertyBean> getSupplierPropertyPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<SupplierPropertyBean> result = new PageResultBean<SupplierPropertyBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by property_code,supplier_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierPropertyBean.class,
					pageBean, db);
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

	public List<SupplierPropertyBean> getSupplierPropertyListByMap(HashMap<String, String> condMap) {
		List<SupplierPropertyBean> result = new ArrayList<SupplierPropertyBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String supplierId = condMap.get("supplierId");
			if (StringKit.isValid(supplierId)) {
				condList.add("supplier_id = ?");
				paramList.add(supplierId);
			}

			String orderQuery = "order by supplier_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, SupplierPropertyBean.class,
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
