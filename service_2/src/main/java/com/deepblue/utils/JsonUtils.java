package com.deepblue.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	public static Gson getSkipIdGson(final String[] skipFieldNames, final String[] skipClassName) {
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

			public boolean shouldSkipField(FieldAttributes f) {
				// TODO Auto-generated method stub
				for (int i = 0; i < skipFieldNames.length; i++) {
					if (f.getName().equals(skipFieldNames[i]))
						return true;
				}
				return false;
			}

			public boolean shouldSkipClass(Class<?> clazz) {
				if (skipClassName == null)
					return false;
				// TODO Auto-generated method stub
				for (int i = 0; i < skipClassName.length; i++)
					if (clazz.getName().equals(skipClassName[i]))
						return true;
				return false;
			}

		}).create();
		return gson;
	}
}
