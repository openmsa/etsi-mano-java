package com.ubiqube.etsi.mano.json;

import org.apache.commons.beanutils.Converter;

import com.ubiqube.etsi.mano.model.nsd.sol005.NsDescriptorsNsdInfo.NsdOperationalStateEnum;
import com.ubiqube.etsi.mano.model.vnf.sol005.VnfPkgInfo.OperationalStateEnum;

public class OperationalStateConverter implements Converter {

	@Override
	public Object convert(final Class type, final Object value) {
		if (type.equals(OperationalStateEnum.class)) {
			return OperationalStateEnum.fromValue((String) value);
		} else if (type.equals(NsdOperationalStateEnum.class)) {
			return NsdOperationalStateEnum.fromValue((String) value);
		}
		return null;
	}

}
