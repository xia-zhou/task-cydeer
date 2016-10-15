/*
 * Copyright (C), 2014-2015, 
 * FileName: DynamicFieldFilterProvider.java
 * Author:   xia zhou
 * Date:     2015年6月11日 下午9:32:48
 * Description: 
 */
package com.cydeer.task.jackson;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 〈一句话是什么〉
 * 〈详细描述做什么〉
 *
 * @author xia zhou
 */
@SuppressWarnings("deprecation")
@JsonFilter(DynamicFieldFilterProvider.DYNAMIC_FIELD_FILTER)
public class DynamicFieldFilterProvider extends FilterProvider {
	public static final String DYNAMIC_FIELD_FILTER = "dynamicField";
	private FilterProvider filterProvider;
	private PropertyFilter runtimeFilter;
	
	DynamicFieldFilterProvider(FilterProvider filterProvider, PropertyFilter runtimeFilter){
		this.filterProvider = filterProvider;
		this.runtimeFilter = runtimeFilter;
	}

	@Deprecated
	@Override
	public BeanPropertyFilter findFilter(Object filterId) {
		throw new UnsupportedOperationException("Access to deprecated filters not supported");
	}

	@Override
	public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
		if(StringUtils.equals(DYNAMIC_FIELD_FILTER, filterId.toString()))
			return this.runtimeFilter;
		if(this.filterProvider != null)
			return super.findPropertyFilter(filterId, valueToFilter);
		return null;
	}
	
}

