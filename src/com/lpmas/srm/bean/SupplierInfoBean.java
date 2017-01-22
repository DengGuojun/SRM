package com.lpmas.srm.bean;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lpmas.framework.annotation.FieldTag;

public class SupplierInfoBean {
	@FieldTag(name = "供应商ID")
	private int supplierId = 0;
	@FieldTag(name = "供应商名称")
	private String supplierName = "";
	@FieldTag(name = "供应商类型")
	private int supplierType = 0;
	@FieldTag(name = "邮件")
	private String email = "";
	@FieldTag(name = "国家")
	private String country = "";
	@FieldTag(name = "省份")
	private String province = "";
	@FieldTag(name = "城市")
	private String city = "";
	@FieldTag(name = "区")
	private String region = "";
	@FieldTag(name = "地址")
	private String address = "";
	@FieldTag(name = "联系人姓名")
	private String contactName = "";
	@FieldTag(name = "邮编")
	private String zipCode = "";
	@FieldTag(name = "联系电话")
	private String telephone = "";
	@FieldTag(name = "手机")
	private String mobile = "";
	@FieldTag(name = "传真")
	private String fax = "";
	@FieldTag(name = " 供应商状态")
	private String supplierStatus = "";
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;
	@FieldTag(name = "备注")
	private String memo = "";
	
	
	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(int supplierType) {
		this.supplierType = supplierType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonIgnore
	public String getCompleteAddress() {
		return getCountry() + getProvince() + getCity() + getRegion() + getAddress();
	}
}