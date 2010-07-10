package jisssea.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StringUtility {

	public static String join(String between, String... values) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i != 0) {
				sb.append(between);
			}
			sb.append(values[i]);
		}
		return sb.toString();
	}

	public static Set<String> replaceAll(Collection<String> l, String from, String to) {
		final Set<String> afterAcc = new HashSet<String>(l.size());
		for (String before : l) {
			afterAcc.add(before.replace(from, to));
		}
		return afterAcc;
	}

	public static void main(String[] args) {
		System.out.println(join(" ", "a", "b", "c"));
	}
}
