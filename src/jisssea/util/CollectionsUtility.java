package jisssea.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionsUtility {

	/**
	 * 
	 * @param <K>
	 *            Type of Keys
	 * @param <V>
	 *            Type of Values
	 * @param map
	 *            a collections map
	 * @param value
	 * @return The Corresponding key for value in map
	 */
	public static <K, V> K getMapKey(Map<K, V> map, V value) {
		for (Entry<K, V> e : map.entrySet()) {
			if (value.equals(e.getValue()))
				return e.getKey();
		}
		throw new IllegalArgumentException();
	}

	public static <T> T findFirst(Collection<T> collection, Predicate<T> pred) {
		for (T t : collection) {
			if (pred.check(t))
				return t;
		}
		throw new IllegalArgumentException("Cannot find element");
	}

	public static interface Predicate<T> {
		boolean check(T t);
	}
}
