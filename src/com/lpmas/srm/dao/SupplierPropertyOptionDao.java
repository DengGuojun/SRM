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
import com.lpmas.srm.bean.SupplierPropertyOptionBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierPropertyOptionDao {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyOptionDao.class);

	public int insertSupplierPropertyOption(SupplierPropertyOptionBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_property_option ( property_id, option_value, option_content, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getPropertyId());
			ps.setString(2, bean.getOptionValue());
			ps.setString(3, bean.getOptionContent());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());
			ps.setString(6, bean.getMemo());

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

	public int updateSupplierPropertyOption(SupplierPropertyOptionBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_property_option set property_id = ?, option_value = ?, option_content = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where option_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getPropertyId());
			ps.setString(2, bean.getOptionValue());
			ps.setString(3, bean.getOptionContent());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getModifyUser());
			ps.setString(6, bean.getMemo());

			ps.setInt(7, bean.getOptionId());

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

	public SupplierPropertyOptionBean getSupplierPropertyOptionByKey(int optionId) {
		SupplierPropertyOptionBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_option where option_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, optionId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierPropertyOptionBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierPropertyOptionBean.class);
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

	public PageResultBean<SupplierPropertyOptionBean> getSupplierPropertyOptionPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<SupplierPropertyOptionBean> result = new PageResultBean<SupplierPropertyOptionBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_option";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String propertyId = condMap.get("propertyId");
			if (StringKit.isValid(propertyId)) {
				condList.add("property_id like ?");
				paramList.add("%" + propertyId + "%");
			}
			String optionValue = condMap.get("optionValue");
			if (StringKit.isValid(optionValue)) {
				condList.add("option_value like ?");
				paramList.add("%" + optionValue + "%");
			}
			String optionContent = condMap.get("optionContent");
			if (StringKit.isValid(optionContent)) {
				condList.add("option_content like ?");
				paramList.add("%" + optionContent + "%");
			}
			String orderQuery = "order by option_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierPropertyOptionBean.class,
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

	public List<SupplierPropertyOptionBean> getSupplierPropertyOptionListByMap(HashMap<String, String> condMap) {
		List<SupplierPropertyOptionBean> result = new ArrayList<SupplierPropertyOptionBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_option";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String propertyId = condMap.get("propertyId");
			if (StringKit.isValid(propertyId)) {
				condList.add("property_id = ?");
				paramList.add(propertyId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by option_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList,
					SupplierPropertyOptionBean.class, db);
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
