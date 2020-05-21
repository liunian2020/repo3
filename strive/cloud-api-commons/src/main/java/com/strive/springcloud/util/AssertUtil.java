package com.strive.springcloud.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;


/**
 * 
 * @author qing.wang
 *
 */
public class AssertUtil extends Assert {

	/**
	 * 字符串非空判断
	 * 
	 * @param object
	 * @param message
	 */
	public static void notNullString(String object, String message) {
		if (object == null || object.trim().length() <= 0) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是否是空字符串
	 * 
	 * @param object
	 * @return
	 */
	public static Boolean isNullString(String object) {
		if (object == null || object.trim().length() <= 0) {
			return true;
		}
		return false;
	}

	public static Boolean isNotNullString(String object) {
		if (object == null || object.trim().length() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是空字符串
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNullObject(Object object) {
		if (object == null || object.toString().trim().length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是空List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Boolean isEmptyList(List<T> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是非空集合
	 * 
	 * @param collection
	 * @return
	 */
	public static Boolean isNotEmptyList(Collection<?> collection) {
		if (CollectionUtils.isEmpty(collection)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 批量校验数据是否为空
	 * 
	 * @param values
	 * @param descs
	 */
	public static void notNull(Object[] values, String[] descs) throws Exception {
		AssertUtil.notEmpty(values, "values must not be null");
		AssertUtil.notEmpty(descs, "descs must not be null");
		if (values.length != descs.length) {
			throw new Exception("values和descs的长度不一致");
		}
		for (int i = 0; i < values.length; i++) {
			AssertUtil.notNull(values[i], descs[i] + "不能为空");
			if (values[i] instanceof String) {
				AssertUtil.notNullString(values[i].toString(), descs[i] + "不能为空");
			}
		}
	}



	public static void notZero(BigDecimal value, String desc) throws Exception {
		if (value.compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception(desc + "不能小于或等于0");
		}
	}

	public static void notZero(BigDecimal[] values, String[] descs) throws Exception {
		if (values.length != descs.length) {
			throw new Exception("values和descs的长度不一致");
		}
		for (int i = 0; i < values.length; i++) {
			AssertUtil.notZero(values[i], descs[i]);
		}
	}

}
