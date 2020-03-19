package com.youway.commons.utils;


import java.util.Map;
import java.util.Map.Entry;
 
import org.apache.commons.lang3.StringUtils;
 
import com.google.common.collect.Maps;

public class SearchFilter {

	public enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, IN,NOTIN,ISNULL, ISNOTNULL, BETWEENDATE, BETWEENLONG
	}
 
	public String fieldName;
	public Object value;
	public Operator operator;
 
	/**
	 * 使用：EQ, LIKE, GT, LT, GTE, LTE 时调用
	 * 
	 * @param fieldName
	 * @param operator
	 * @param value
	 */
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
 
	/**
	 * 使用：IN, BETWEEN_DATE, BETWEEN_LONG 时调用
	 * 
	 * @param fieldName
	 * @param operator
	 * @param values
	 */
	public SearchFilter(String fieldName, Operator operator, Object... values) {
		this.fieldName = fieldName;
		this.value = values;
		this.operator = operator;
	}
 
	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank(value.toString())) {
				continue;
			}
 
			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length < 2) {
				throw new IllegalArgumentException(key
						+ " is not a valid search filter name");
			}
 
			String filedName = key.substring(names[0].length() + 1).replaceAll(
					"_", ".");
			Operator operator = Operator.valueOf(names[0]);
 
			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}
 
		return filters;
	}
}
