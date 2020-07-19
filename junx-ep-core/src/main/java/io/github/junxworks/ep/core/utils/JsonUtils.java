/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  JsonUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  JsonUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class JsonUtils extends io.github.junxworks.junx.core.util.JsonUtils {

	/**
	 * Parses the json array.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param array the array
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> parseJsonArray(Class<T> clazz, Object array) {
		if (array instanceof JSONArray) {
			return parseJsonArray(clazz, (JSONArray) array);
		} else if (array instanceof List) {
			return parseJsonArray(clazz, (List) array);
		}
		throw new BaseRuntimeException("Input parameter must be JSONArray");
	}

	/**
	 * Parses the json array.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param array the array
	 * @return the list
	 */
	public static <T> List<T> parseJsonArray(Class<T> clazz, JSONArray array) {
		return array.stream().flatMap(p -> {
			return Stream.of(JSON.toJavaObject((JSONObject) p, clazz));
		}).collect(Collectors.toList());
	}

	/**
	 * Parses the json array.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param array the array
	 * @return the list
	 */
	public static <T> List<T> parseJsonArray(Class<T> clazz, List<Map<String, Object>> array) {
		return array.stream().flatMap(p -> {
			return Stream.of(parseJsonObject(clazz, p));
		}).collect(Collectors.toList());
	}

	/**
	 * Parses the json object.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param obj the obj
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseJsonObject(Class<T> clazz, Object obj) {
		if (obj instanceof JSONObject) {
			return parseJsonObject(clazz, (JSONObject) obj);
		} else if (obj instanceof Map) {
			return parseJsonObject(clazz, (Map<String, Object>) obj);
		}
		throw new BaseRuntimeException("Input parameter must be JSONObject");
	}

	/**
	 * Parses the json object.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param obj the obj
	 * @return the t
	 */
	public static <T> T parseJsonObject(Class<T> clazz, JSONObject obj) {
		return JSON.toJavaObject(obj, clazz);
	}

	/**
	 * Parses the json object.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param map the map
	 * @return the t
	 */
	public static <T> T parseJsonObject(Class<T> clazz, final Map<String, Object> map) {
		try {
			return JSON.parseObject(JSON.toJSONString(map), clazz);
		} catch (Exception e) {
			throw new BaseRuntimeException(e);
		}
	}
}
