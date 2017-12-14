package com.mob.bbssdk.gui.jimu.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.mob.bbssdk.gui.jimu.biz.ProtocolBase;
import com.mob.tools.utils.Hashon;

/**
 * 所有的查询操作都通过Query来拼接，使用方法如下：
 * <pre>
 * String json = new Query("http://ip:port/q/news")
 *         .condition(Condition.and(
 *                 Condition.lte("createat", Date.valueOf(System.currentTimeMillis())),
 *                 Condition.gte("createat", Date.valueOf(-86400000L))))
 *         .fields("title", "thumbs", "createat")
 *         .sort(Sort.desc("createat"), Sort.asc("title"))
 *         .includes(Include.type("user")
 *                 .fields("id", "nickname", "avatar")
 *                 .sort(Sort.asc("nick"), Sort.asc("id")))
 *         .offset(20)
 *         .size(20)
 *         .compile();
 * </pre>
 * 返回的json就是用来发送请求的query脚本
 */
public class Query {
	private String url;
	private ProtocolBase protocol;
	private HashMap<String, Object> query;
	private ArrayList<String> fields;
	private ArrayList<HashMap<String, Object>> sort;
	private int offset;
	private int size;
	private ArrayList<HashMap<String, Object>> includes;
	private HashMap<String, Object> extra;

	public Query(String url, ProtocolBase protocol) {
		this.url = url;
		this.protocol = protocol;
		query = new HashMap<String, Object>();
		fields = new ArrayList<String>();
		sort = new ArrayList<HashMap<String, Object>>();
		offset = 0;
		size = 50;
		includes = new ArrayList<HashMap<String, Object>>();
		extra = new HashMap<String, Object>();
	}

	/** 设置查询的条件 */
	public Query condition(Condition condition) {
		query.clear();
		query.putAll(condition.toMap());
		return this;
	}

	/** 设置返回数据每一个元素包含的字段，缺省为全部返回 */
	public Query fields(String... fields) {
		this.fields.addAll(Arrays.asList(fields));
		return this;
	}


	/** 根据设置的字段，对返回数据进行排序 */
	public Query sort(Sort... sorts) {
		for (Sort sort : sorts) {
			this.sort.add(sort.toMap());
		}
		return this;
	}

	/** 设置从搜索结果的何处开始返回数据，缺省为从第0条开始 */
	public Query offset(int offset) {
		this.offset = offset;
		return this;
	}

	/** 设置返回数据的总量，缺省为最多50条 */
	public Query size(int size) {
		this.size = size;
		return this;
	}

	private HashMap<String, Object> compileToMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (query.size() > 0) {
			map.put("query", query);
		}
		if (fields.size() > 0) {
			map.put("fields", fields);
		}
		if (sort.size() > 0) {
			map.put("sort", sort);
		}
		map.put("offset", offset);
		map.put("size", size);
		if (includes.size() > 0) {
			map.put("includes", includes);
		}
		return map;
	}

	/** 将查询条件转换为json格式 */
	public String compile() {
		return new Hashon().fromHashMap(compileToMap());
	}

	/** 清空所有查询条件 */
	public Query reset() {
		query.clear();
		fields.clear();
		sort.clear();
		offset = 0;
		size = 50;
		includes.clear();
		return this;
	}

	public void putExtra(String key, Object value) {
		extra.put(key, value);
	}

	/** 执行查询，为联网阻塞操作，返回一个json结果 */
	public String query() throws Throwable {
		HashMap<String, Object> map = compileToMap();
		map.put("target", extra);
		return protocol.query(new Hashon().fromHashMap(map), url);
	}

}
