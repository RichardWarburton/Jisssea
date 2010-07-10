package jisssea.util;

import java.util.Map;
import java.util.Map.Entry;

public class CollectionsUtility {

	/**
	 * 
	 * @param <K> Type of Keys
	 * @param <V> Type of Values
	 * @param map a collections map
	 * @param value  
	 * @return The Corresponding key for value in map
	 */
	public static<K,V> K getMapKey(Map<K,V> map,V value) {
		for (Entry<K, V> e : map.entrySet()) {
			if(value.equals(e.getValue()))
				return e.getKey();
		}
		throw new IllegalArgumentException();
	}
}
