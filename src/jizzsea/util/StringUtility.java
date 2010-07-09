package jizzsea.util;

public class StringUtility {

	public static String join(String between,String ... values) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if(i != 0) {
				sb.append(between);
			}
			sb.append(values[i]);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(join(" ", "a", "b", "c"));
	}
}
