package com.mob.bbssdk.gui.jimu.query;

import java.util.HashMap;

/**
 * 对返回数据进行排序，包括：正序和倒序。如：
 * <pre>Sort.asc("nick")</pre>
 * 并通过Query{@link Query#sort(Sort[])}设置为查询条件
 */
public class Sort {
	private HashMap<String, Object> sort;

	private Sort() {
		sort = new HashMap<String, Object>();
	}

	/** 正序 */
	public static Sort asc(String field) {
		return put(field, 1);
	}

	/** 倒序 */
	public static Sort desc(String field) {
		return put(field, -1);
	}

	private static Sort put(String field, int sort) {
		Sort s = new Sort();
		s.sort.put(field, sort);
		return s;
	}

	public HashMap<String, Object> toMap() {
		return sort;
	}

}
