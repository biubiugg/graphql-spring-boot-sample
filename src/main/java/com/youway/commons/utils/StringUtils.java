package com.youway.commons.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;


/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author youway
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	public static String lowerFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toLowerCase() + str.substring(1);
		}
	}
	
	public static String upperFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 缩略字符串（替换html）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 转换为字符串，如null则转换为""
	 *
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj) {
		if (obj == null) {
			obj = "";
		}
		return obj.toString();
	}

	/**
	 * 将字符串转换成长整型，如果转换失败，则默认为0
	 *
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		return NumberUtils.toLong(str, 0);
	}

	/**
	 * 将字符串转换成Double型，如果转换失败，则默认为0.00
	 *
	 * @param str
	 * @return
	 */
	public static double toDouble(String str) {
		return NumberUtils.toDouble(str, 0.00);
	}

	/**
	 * 将字符串转换成整型，如果转换失败，则默认为0
	 *
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		return NumberUtils.toInt(str, 0);
	}

	/**
	 * 开头字母转大写
	 *
	 * @param name
	 * @return
	 */
	public static String startCapital(String name) {
		// name = name.substring(0, 1).toUpperCase() + name.substring(1);
		// return name;
		char[] cs = name.toCharArray();
		if (cs[0] > 96) {
			cs[0] -= 32;
		}
		return String.valueOf(cs);
	}
	
}