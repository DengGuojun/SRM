package com.lpmas.srm.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class SupplierPropertyTypeBean {
	@FieldTag(name = "属性ID")
	private int propertyId = 0;
	@FieldTag(name = "属性名称")
	private String propertyName = "";
	@FieldTag(name = "属性代码")
	private String propertyCode = "";
	@FieldTag(name = "供应商分类ID")
	private int supplierTypeId = 0;
	@FieldTag(name = "父级属性ID")
	private int parentPropertyId = 0;
	@FieldTag(name = "属性类型")
	private int categoryId = 0;
	@FieldTag(name = "输入方式，文本框、选择框")
	private int inputMethod = 0;
	@FieldTag(name = "json")
	private String inputStyle = "";
	@FieldTag(name = "输入说明")
	private String inputDesc = "";
	@FieldTag(name = "数据字段类型文本、数字")
	private int fieldType = 0;
	@FieldTag(name = "数据格式")
	private String fieldFormat = "";
	@FieldTag(name = "数据字段来源")
	private String fieldSource = "";
	@FieldTag(name = "默认值")
	private String defaultValue = "";
	@FieldTag(name = "是否必填")
	private int isRequired = 0;
	@FieldTag(name = "是否可修改")
	private int isModifiable = 0;
	@FieldTag(name = "优先级")
	private int priority = 0;
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

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public int getSupplierTypeId() {
		return supplierTypeId;
	}

	public void setSupplierTypeId(int supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
	}

	public int getParentPropertyId() {
		return parentPropertyId;
	}

	public void setParentPropertyId(int parentPropertyId) {
		this.parentPropertyId = parentPropertyId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(int inputMethod) {
		this.inputMethod = inputMethod;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getInputDesc() {
		return inputDesc;
	}

	public void setInputDesc(String inputDesc) {
		this.inputDesc = inputDesc;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldFormat() {
		return fieldFormat;
	}

	public void setFieldFormat(String fieldFormat) {
		this.fieldFormat = fieldFormat;
	}

	public String getFieldSource() {
		return fieldSource;
	}

	public void setFieldSource(String fieldSource) {
		this.fieldSource = fieldSource;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}

	public int getIsModifiable() {
		return isModifiable;
	}

	public void setIsModifiable(int isModifiable) {
		this.isModifiable = isModifiable;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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
}