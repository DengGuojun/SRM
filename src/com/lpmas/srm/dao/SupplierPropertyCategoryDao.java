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
import com.lpmas.srm.bean.SupplierPropertyCategoryBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierPropertyCategoryDao {
	private static Logger log = LoggerFactory.getLogger(SupplierPropertyCategoryDao.class);

	public int insertSupplierPropertyCategory(SupplierPropertyCategoryBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_property_category ( category_name, category_code, parent_category_id, priority, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCategoryName());
			ps.setString(2, bean.getCategoryCode());
			ps.setInt(3, bean.getParentCategoryId());
			ps.setInt(4, bean.getPriority());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getCreateUser());
			ps.setString(7, bean.getMemo());

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

	public int updateSupplierPropertyCategory(SupplierPropertyCategoryBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_property_category set category_name = ?, category_code = ?, parent_category_id = ?, priority = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where category_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCategoryName());
			ps.setString(2, bean.getCategoryCode());
			ps.setInt(3, bean.getParentCategoryId());
			ps.setInt(4, bean.getPriority());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());

			ps.setInt(8, bean.getCategoryId());

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

	public SupplierPropertyCategoryBean getSupplierPropertyCategoryByKey(int categoryId) {
		SupplierPropertyCategoryBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_category where category_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, categoryId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierPropertyCategoryBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierPropertyCategoryBean.class);
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
	
	public SupplierPropertyCategoryBean getSupplierPropertyCategoryByCode(String categoryCode) {
		SupplierPropertyCategoryBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_category where category_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, categoryCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierPropertyCategoryBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierPropertyCategoryBean.class);
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

	public PageResultBean<SupplierPropertyCategoryBean> getSupplierPropertyCategoryPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<SupplierPropertyCategoryBean> result = new PageResultBean<SupplierPropertyCategoryBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_category";

			// 条件处理
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String categoryName = condMap.get("categoryName");
			if (StringKit.isValid(categoryName)) {
				condList.add("category_name like ?");
				paramList.add("%" + categoryName + "%");
			}
			String categoryCode = condMap.get("categoryCode");
			if (StringKit.isValid(categoryCode)) {
				condList.add("category_code like ?");
				paramList.add("%" + categoryCode + "%");
			}
			String parentCategoryId = condMap.get("parentCategoryId");
			if (StringKit.isValid(parentCategoryId)) {
				condList.add("parent_category_id = ?");
				paramList.add(parentCategoryId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by category_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierPropertyCategoryBean.class,
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
	
	public List<SupplierPropertyCategoryBean> getSupplierPropertyCategoryListByMap(HashMap<String, String> condMap) {
		List<SupplierPropertyCategoryBean> result = new ArrayList<SupplierPropertyCategoryBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_property_category";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String parentCategoryId = condMap.get("parentCategoryId");
			if (StringKit.isValid(parentCategoryId)) {
				condList.add("parent_category_id = ?");
				paramList.add(parentCategoryId);
			}
			String orderQuery = "order by priority asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, SupplierPropertyCategoryBean.class, db);
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
