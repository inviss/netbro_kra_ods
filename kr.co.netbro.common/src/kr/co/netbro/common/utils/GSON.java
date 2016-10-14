package kr.co.netbro.common.utils;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.netbro.common.json.converter.DateJsonAdapter;

public class GSON {
	
	private static Gson gson = null;
	static {
		if(gson == null)
			gson = new GsonBuilder()
			.registerTypeAdapter(Date.class, new DateJsonAdapter())
			.excludeFieldsWithoutExposeAnnotation().create();
	}
	
	public static <T> T toObject(String json, Class<T> cls) {
		return gson.fromJson(json, cls);
	}
	
	public static <T> String toString(T obj) {
		return gson.toJson(obj);
	}
}
