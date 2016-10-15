/*
 * Copyright (C), 2014-2015, 
 * FileName: Jackson.java
 * Author:   xia zhou
 * Date:     2015年6月11日 下午9:31:27
 * Description: 
 */
package com.cydeer.task.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * <pre>
 * 〈一句话是什么〉
 * 〈详细描述做什么〉
 *
 * @author xia zhou
 */
public class Jackson {
	private static volatile Base base;
	private static volatile Simple simple;
	private static volatile Mobile mobile;

	// 获取默认实例
	public static Base base() {
		if (base == null) {
			synchronized (Jackson.class) {
				if (base == null) {
					base = new Base();
				}
			}
		}
		return base;
	}

	// 获取精简版实例，目前主要用于缓存KEY值的生成
	public static Simple simple() {
		if (simple == null) {
			synchronized (Jackson.class) {
				if (simple == null) {
					simple = new Simple();
				}
			}
		}
		return simple;
	}
	
	/**
	 * 
	 * 为手机端提供json数据时，为了json能兼容Android与IOS， 默认不生成为空的属性
	 *
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Mobile mobile() {
		if (mobile == null) {
			synchronized (Jackson.class) {
				if (mobile == null) {
					mobile = new Mobile();
				}
			}
		}
		return mobile;
	}

	public static class Base {
		protected ObjectMapper mapper;

		public Base() {
			this.mapper = new ObjectMapper();
		}

		public TypeFactory getTypeFactory() {
			return this.mapper.getTypeFactory();
		}

		public ObjectMapper getObjectMapper() {
			return this.mapper;
		}
		public <T> T readValue(String content, Class<T> valueType) {
			try {
				return this.mapper.readValue(content, valueType);
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}

		public <T> T readValue(String content, TypeReference<?> valueTypeRef) {
			try {
				return this.mapper.readValue(content, valueTypeRef);
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}

		public <T> T readValue(String content, JavaType valueType) {
			try {
				return this.mapper.readValue(content, valueType);
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}

		public void writeValue(Writer w, Object value) {
			try {
				this.mapper.writeValue(w, value);
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}

		public String writeValueAsString(Object value) {
			try {
				return this.mapper.writeValueAsString(value);
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}
	}

	public static class Simple extends Base {
		public Simple() {
			super();
			// 属性排序，保证顺序生成JSON
			mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			// 对于值为空的属性不生成
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
			// 去除属性名称的双引号
			mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
			// 设置全局动态字段包含过滤器
			mapper.addMixInAnnotations(Object.class, DynamicFieldFilterProvider.class);
			mapper.setFilters(new DynamicFieldFilterProvider(null, null));

		}
		public String writeValueAsString(Object value, String[] includeFields) {
			try {
				if (includeFields != null && includeFields.length > 0) {
					FilterProvider filters = new DynamicFieldFilterProvider(this.mapper.getSerializationConfig()
							.getFilterProvider(), SimpleBeanPropertyFilter.filterOutAllExcept(includeFields));
					return this.mapper.writer(filters).writeValueAsString(value);
				} else {
					return this.mapper.writeValueAsString(value);
				}
			} catch (IOException e) {
				throw new RuntimeException("Jackson 出异常了：", e);
			}
		}
	}
	
	public static class Mobile extends Base {
		public Mobile() {
			super();
			// 对于值为空的属性不生成
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		}
	}

}
