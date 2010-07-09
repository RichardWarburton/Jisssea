package jizzsea.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IrcUtility {

	private static final Set<String> prefixes = 
		new HashSet<String>(Arrays.asList("#","+","!","&"));
	
	public static void main(String[] args) {
		String t1 = "uwcsnet:#jizz";
		System.out.println(isValidName(t1));
		System.out.println(getNetwork(t1));
		System.out.println(getChannel(t1));
	}
	
	public static boolean isValidChannel(final String name) {
		for (String prefix : prefixes) {
			if(name.startsWith(prefix))
				return true;
		}
		return false;
	}
	
	public static boolean isValidName(final String name) {
		final int indexOf = name.indexOf(':');
		return indexOf != -1 &&
			prefixes.contains(name.substring(indexOf+1, indexOf+2));
	}
	
	public static String getNetwork(final String name) {
		return name.substring(0,name.indexOf(':'));
	}
	
	public static String getChannel(final String name) {
		return name.substring(name.indexOf(':')+1);
	}
}
