package com.lpmas.srm.factory;

import com.lpmas.srm.business.PropertyInputDisplay;
import com.lpmas.srm.business.PropertyInputVerify;
import com.lpmas.srm.business.impl.PropertyInputDisplayCheckboxImpl;
import com.lpmas.srm.business.impl.PropertyInputDisplayDateImpl;
import com.lpmas.srm.business.impl.PropertyInputDisplayOutsourceImpl;
import com.lpmas.srm.business.impl.PropertyInputDisplaySelectImpl;
import com.lpmas.srm.business.impl.PropertyInputDisplayTextImpl;
import com.lpmas.srm.business.impl.PropertyInputDisplayTextareaImpl;
import com.lpmas.srm.business.impl.PropertyInputVerifyBooleanImpl;
import com.lpmas.srm.business.impl.PropertyInputVerifyDateImpl;
import com.lpmas.srm.business.impl.PropertyInputVerifyNumberImpl;
import com.lpmas.srm.config.SupplierPropertyTypeConfig;

public class SupplierPropertyFactory {

	public PropertyInputDisplay getPropertyInputDisplay(int inputMethod) {
		if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_TEXT) {
			return new PropertyInputDisplayTextImpl();
		} else if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_SELECT) {
			return new PropertyInputDisplaySelectImpl();
		} else if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_DATE) {
			return new PropertyInputDisplayDateImpl();
		} else if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_CHECKBOX) {
			return new PropertyInputDisplayCheckboxImpl();
		} else if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_TEXTAREA) {
			return new PropertyInputDisplayTextareaImpl();
		} else if (inputMethod == SupplierPropertyTypeConfig.INPUT_METHOD_BOX) {
			return new PropertyInputDisplayOutsourceImpl();
		}
		return null;
	}

	public PropertyInputVerify getPropertyInputVerify(int fieldType) {
		if (fieldType == SupplierPropertyTypeConfig.FIELD_TYPE_BOOLEAN) {
			return new PropertyInputVerifyBooleanImpl();
		} else if (fieldType == SupplierPropertyTypeConfig.FIELD_TYPE_NUMBER) {
			return new PropertyInputVerifyNumberImpl();
		} else if (fieldType == SupplierPropertyTypeConfig.FIELD_TYPE_DATE) {
			return new PropertyInputVerifyDateImpl();
		}
		return null;
	}

}
