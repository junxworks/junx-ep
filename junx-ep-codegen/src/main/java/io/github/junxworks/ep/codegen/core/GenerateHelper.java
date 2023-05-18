package io.github.junxworks.ep.codegen.core;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import io.github.junxworks.junx.core.util.StringUtils;

public class GenerateHelper {
	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || str.equals("null");
	}

	public static String packagePathToFilePath(String packagepath) {
		String result = null;
		if (packagepath != null) {
			result = packagepath.replace(".", File.separator);
		}
		return result;
	}

	public static String toUnicodeString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				sb.append("\\u" + Integer.toHexString(c));
			}
		}
		return sb.toString();
	}

	public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
		if (inputString.contains("-")) {
			inputString = underlineToCamel(inputString, '-');
		}
		if (inputString.contains("_")) {
			inputString = underlineToCamel(inputString, '_');
		}
		StringBuilder sb = new StringBuilder(inputString);
		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		} else {
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		}
		return sb.toString();
	}

	public static String underlineToCamel(String str, char ch) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c == ch) {
				if (++i < len) {
					sb.append(Character.toUpperCase(str.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getValidPropertyName(String inputString) {
		String answer;

		if (inputString == null) {
			answer = null;
		} else if (inputString.length() < 2) {
			answer = inputString.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
			} else {
				answer = inputString;
			}
		}

		return answer;
	}

	public static String escape(final String val) {

		int len = val.length();
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			if (val.charAt(i) == '\'')
				buf.append('\'');
			buf.append(val.charAt(i));
		}
		return buf.toString();
	}

	public static Connection getConnection(String type, String url, String user, String password) throws Exception {
		Connection con = null;
		String className = "";
		if (type.equals("mysql")) {
			className = "com.mysql.cj.jdbc.Driver";
		} else {
			throw new Exception("暂不支持数据库类型:" + type);
		}
		try {
			Class.forName(className);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException cException) {
			throw new Exception("缺少" + type + "数据库驱动类");
		} catch (SQLException sException) {
			throw new Exception("创建数据库连接失败");
		}
		return con;
	}
}
