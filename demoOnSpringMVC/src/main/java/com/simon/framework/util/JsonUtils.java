package com.simon.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 * @version 1.0
 * @parameter
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class JsonUtils {

	private static ObjectMapper mapper;

	public static <T> String transToJson(T object) {
		if (object == null)
			return null;
		if (mapper == null)
			initJson();
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {

		}
		return null;
	}

	public static <T> T transFromJson(String json, Class<T> classType) {
		if (json == null)
			return null;
		if (mapper == null)
			initJson();
		try {
			return mapper.readValue(json, classType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void initJson() {
		mapper = new ObjectMapper();

	}

	@SuppressWarnings("deprecation")
	public static <T> List<T> convertJsonToList(String json, Class<T> classType) {
		if (json == null)
			return null;
		if (mapper == null)
			initJson();

		try {
			List<T> result = mapper.readValue(json, TypeFactory.collectionType(ArrayList.class, classType));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	public static <T> T convertJsonToBean(String json, Class<T> classType) {
		if (json == null)
			return null;
		if (mapper == null)
			initJson();

		try {

			T result = mapper.readValue(json, TypeFactory.collectionType(ArrayList.class, classType));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		// String string =
		// "[{\"days\":[\"0\",\"0\",\"0\"],\"metric\":\"连通性\",\"xx\":123 ,
		// \"day\":{\"time\":\"17:22:00\",\"list\":[{\"name\":\"连通性\",\"status\":\"不明\"}]},
		// \"list\":[{\"time\":\"17:22:00\",\"list\":[{\"name\":\"连通性\",\"status\":\"不明\"}]}]}]";
		// List<ArrayList> list = JsonUtils.convertJsonToList(string,
		// ArrayList.class);
	}

}