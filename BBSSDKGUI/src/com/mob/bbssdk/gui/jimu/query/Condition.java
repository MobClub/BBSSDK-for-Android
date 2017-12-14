package com.mob.bbssdk.gui.jimu.query;

import com.mob.bbssdk.gui.jimu.query.data.DataType;
import com.mob.bbssdk.gui.jimu.query.data.Rangable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 区间查询条件，包括：大于、小于、等于、不等于、大于或等于、小于或等于、指定集合、与、非和模糊匹配，如：
 * <pre>Range.lte("createat", Date.valueOf(System.currentTimeMillis()))</pre>
 * 或者:
 * <pre>Rage.in("name", Text.valueOf("alex", "bob", "steph"))</pre>
 * 并通过：{@link Query#condition(Condition)}设置为查询条件
 */
public final class Condition {
	private HashMap<String, Object> condition;

	private Condition() {
		condition = new HashMap<String, Object>();
	}

	/** 大于或等于 */
	public static final Condition gte(String field, Rangable<?> value) {
		return range("gte", field, value);
	}

	/** 大于 */
	public static final Condition gt(String field, Rangable<?> value) {
		return range("gt", field, value);
	}

	/** 小于或等于 */
	public static final Condition lte(String field, Rangable<?> value) {
		return range("lte", field, value);
	}

	/** 小于 */
	public static final Condition lt(String field, Rangable<?> value) {
		return range("lt", field, value);
	}

	private static Condition range(String typeName, String field, DataType<?> value) {
		Condition range = new Condition();
		HashMap<String, Object> op = new HashMap<String, Object>();
		op.put(typeName, value.value());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(field, op);
		range.condition.put("range", map);
		return range;
	}

	/** 等于 */
	public static final Condition eq(String field, DataType<?> value) {
		return term(field, value);
	}

	/** 不等于 */
	public static final Condition ne(String field, DataType<?> value) {
		return term("-" + field, value);
	}

	private static Condition term(String field, DataType<?> value) {
		Condition term = new Condition();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(field, value.value());
		term.condition.put("term", map);
		return term;
	}

	/** 数组 */
	public static final <T extends DataType<?>> Condition in(String field, T... values) {
		if (values.length == 1) {
			return eq(field, values[0]);
		} else {
			Condition in = new Condition();
			HashMap<String, Object> map = new HashMap<String, Object>();
			ArrayList<Object> list = new ArrayList<Object>();
			for (T value : values) {
				list.add(value.value());
			}
			map.put(field, list);
			in.condition.put("in", map);
			return in;
		}
	}

	/** 与操作 */
	public static Condition and(Condition... conditions) {
		return bool("and", conditions);
	}

	/** 或操作 */
	public static Condition or(Condition... conditions) {
		return bool("or", conditions);
	}

	private static Condition bool(String op, Condition... conditions) {
		Condition bool = new Condition();
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (Condition c : conditions) {
			list.add(c.toMap());
		}
		map.put(op, list);
		bool.condition.put("bool", map);
		return bool;
	}

	/** 模糊匹配 */
	public static Condition like(String field, DataType<?> value) {
		return fuzzy(field, value);
	}

	private static Condition fuzzy(String field, DataType<?> value) {
		Condition fuzzy = new Condition();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(field, value.value());
		fuzzy.condition.put("fuzzy", map);
		return fuzzy;
	}

	public HashMap<String, Object> toMap() {
		return condition;
	}

}
