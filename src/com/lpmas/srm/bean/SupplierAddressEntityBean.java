package com.lpmas.srm.bean;

import com.lpmas.framework.nosql.mongodb.MongoDBDocumentBean;

public class SupplierAddressEntityBean extends MongoDBDocumentBean<Integer> {

	private int addressId;
	private String supplierAddress;
	private int supplierId;
	private String supplierName;
	private int supplierTypeId;
	private String supplierTypeName;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getSupplierTypeId() {
		return supplierTypeId;
	}

	public void setSupplierTypeId(int supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
	}
	
	

}
