package com.lpmas.srm.bean;

import java.sql.Timestamp;

public class SupplierInfoEntityBean {

	private SupplierInfoBean supplierInfoBean;
	private SupplierTypeBean supplierTypeBean;

	public SupplierInfoEntityBean(SupplierInfoBean supplierInfoBean, SupplierTypeBean supplierTypeBean) {
		super();
		this.supplierInfoBean = supplierInfoBean;
		this.supplierTypeBean = supplierTypeBean;
	}

	public SupplierInfoBean getSupplierInfoBean() {
		return supplierInfoBean;
	}

	public void setSupplierInfoBean(SupplierInfoBean supplierInfoBean) {
		this.supplierInfoBean = supplierInfoBean;
	}

	public SupplierTypeBean getSupplierTypeBean() {
		return supplierTypeBean;
	}

	public void setSupplierTypeBean(SupplierTypeBean supplierTypeBean) {
		this.supplierTypeBean = supplierTypeBean;
	}

	public int getSupplierId() {
		return supplierInfoBean.getSupplierId();
	}

	public String getSupplierName() {
		return supplierInfoBean.getSupplierName();
	}

	public int getSupplierType() {
		return supplierInfoBean.getSupplierType();
	}

	public String getEmail() {
		return supplierInfoBean.getEmail();
	}

	public String getCountry() {
		return supplierInfoBean.getCountry();
	}

	public String getProvince() {
		return supplierInfoBean.getProvince();
	}

	public String getCity() {
		return supplierInfoBean.getCity();
	}

	public String getRegion() {
		return supplierInfoBean.getRegion();
	}

	public String getAddress() {
		return supplierInfoBean.getAddress();
	}

	public String getContactName() {
		return supplierInfoBean.getContactName();
	}

	public String getZipCode() {
		return supplierInfoBean.getZipCode();
	}

	public String getTelephone() {
		return supplierInfoBean.getTelephone();
	}

	public String getMobile() {
		return supplierInfoBean.getMobile();
	}

	public String getFax() {
		return supplierInfoBean.getFax();
	}

	public String getSupplierStatus() {
		return supplierInfoBean.getSupplierStatus();
	}

	public int getStatus() {
		return supplierInfoBean.getStatus();
	}

	public Timestamp getCreateTime() {
		return supplierInfoBean.getCreateTime();
	}

	public int getCreateUser() {
		return supplierInfoBean.getCreateUser();
	}

	public Timestamp getModifyTime() {
		return supplierInfoBean.getModifyTime();
	}

	public int getModifyUser() {
		return supplierInfoBean.getModifyUser();
	}

	public String getMemo() {
		return supplierInfoBean.getMemo();
	}

	public String getSupplierTypeName() {
		return supplierTypeBean.getTypeName();
	}
	
	public String getCompleteAddress() {
		return getCountry() + getProvince() + getCity() + getRegion() + getAddress();
	}

}
