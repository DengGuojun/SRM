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
import com.lpmas.srm.bean.SupplierTypeBean;
import com.lpmas.srm.factory.SrmDBFactory;

public class SupplierTypeDao {
	private static Logger log = LoggerFactory.getLogger(SupplierTypeDao.class);

	public int insertSupplierType(SupplierTypeBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into supplier_type ( type_name, type_code, parent_type_id, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getTypeName());
			ps.setString(2, bean.getTypeCode());
			ps.setInt(3, bean.getParentTypeId());
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

	public int updateSupplierType(SupplierTypeBean bean) {
		int result = -1;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update supplier_type set type_name = ?, type_code = ?, parent_type_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where type_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getTypeName());
			ps.setString(2, bean.getTypeCode());
			ps.setInt(3, bean.getParentTypeId());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getModifyUser());
			ps.setString(6, bean.getMemo());

			ps.setInt(7, bean.getTypeId());

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

	public SupplierTypeBean getSupplierTypeByKey(int typeId) {
		SupplierTypeBean bean = null;
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_type where type_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, typeId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new SupplierTypeBean();
				bean = BeanKit.resultSet2Bean(rs, SupplierTypeBean.class);
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

	public PageResultBean<SupplierTypeBean> getSupplierTypePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<SupplierTypeBean> result = new PageResultBean<SupplierTypeBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_type";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String parentTypeId = condMap.get("parentTypeId");
			if (StringKit.isValid(parentTypeId)) {
				condList.add("parent_type_id = ?");
				paramList.add(parentTypeId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String typeName = condMap.get("typeName");
			if (StringKit.isValid(typeName)) {
				condList.add("type_name like ?");
				paramList.add("%" + typeName + "%");
			}
			String typeCode = condMap.get("typeCode");
			if (StringKit.isValid(typeCode)) {
				condList.add("type_code like ?");
				paramList.add("%" + typeCode + "%");
			}

			String orderQuery = "order by type_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, SupplierTypeBean.class, pageBean,
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

	public List<SupplierTypeBean> getSupplierTypeListByMap(HashMap<String, String> condMap) {
		List<SupplierTypeBean> result = new ArrayList<SupplierTypeBean>();
		DBFactory dbFactory = new SrmDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from supplier_type";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String parentTypeId = condMap.get("parentTypeId");
			if (StringKit.isValid(parentTypeId)) {
				condList.add("parent_type_id = ?");
				paramList.add(parentTypeId);
			}
			String typeCode = condMap.get("typeCode");
			if (StringKit.isValid(typeCode)) {
				condList.add("type_code = ?");
				paramList.add(typeCode);
			}
			String orderQuery = "order by type_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, SupplierTypeBean.class, db);
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
