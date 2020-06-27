/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  JsonUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-7-10 13:16:10   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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

public class JsonUtils extends io.github.junxworks.junx.core.util.JsonUtils {

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
	 * 解析JSON数组.
	 *
	 * @param <T> the generic type
	 * @param array the array
	 * @param clazz the clazz
	 * @return the list
	 */
	public static <T> List<T> parseJsonArray(Class<T> clazz, JSONArray array) {
		return array.stream().flatMap(p -> {
			return Stream.of(JSON.toJavaObject((JSONObject) p, clazz));
		}).collect(Collectors.toList());
	}

	public static <T> List<T> parseJsonArray(Class<T> clazz, List<Map<String, Object>> array) {
		return array.stream().flatMap(p -> {
			return Stream.of(parseJsonObject(clazz, p));
		}).collect(Collectors.toList());
	}

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
	 * 解析Json Object
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @param clazz the clazz
	 * @return the t
	 */
	public static <T> T parseJsonObject(Class<T> clazz, JSONObject obj) {
		return JSON.toJavaObject(obj, clazz);
	}

	public static <T> T parseJsonObject(Class<T> clazz, final Map<String, Object> map) {
		try {
			return JSON.parseObject(JSON.toJSONString(map), clazz);
		} catch (Exception e) {
			throw new BaseRuntimeException(e);
		}
	}
}
