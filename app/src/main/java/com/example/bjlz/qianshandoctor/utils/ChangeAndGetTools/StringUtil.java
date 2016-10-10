package com.example.bjlz.qianshandoctor.utils.ChangeAndGetTools;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 是否是电子邮箱
	 * @param paramString
	 * @return
	 */
	public static boolean isEmail(String paramString) {
		return Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
				.matcher(paramString).matches();
	}

	/**
	 * 判断是否是email地址
	 * @param value
	 * @return
     */
	public static final boolean isemail(String value) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
	}
	/**
	 * 是否是身份证号
	 * @param paramString
	 * @return
	 */
	public static boolean isIDCard(String paramString) {
		return Pattern.compile("(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")
				.matcher(paramString).matches();
	}
	/**
	 * 是否符合手机格式
	 * @param paramString
	 * @return
	 */
	public static boolean isMobileNO(String paramString) {
		return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(paramString).matches();
	}
	/**
	 * 是否为数字
	 * @param paramString
	 * @return
	 */
	public static boolean isNumber(String paramString) {
		try {
			Float.parseFloat(paramString);
			return true;
		} catch (Exception localException) {
		}
		return false;
	}
	/**
	 * 是否是符合要求的密码格式
	 * @param paramString
	 * @return
	 */
	public static boolean isValidPassword(String paramString) {
		return Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$")
				.matcher(paramString).matches();
	}

	/**
	 * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
	 */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 判断该字符串是否为中文
	 * @param string
	 * @return
	 */
	public static boolean isChinese(String string){
		int n = 0;
		for(int i = 0; i < string.length(); i++) {
			n = (int)string.charAt(i);
			if(!(19968 <= n && n <40869)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 显示隐藏位数的手机号
	 * @param mobile
	 * @return
     */
	public static String hideMobileNumber(String mobile){
		 String number=null;
		if (mobile.contains("+86")||mobile.contains("086"))
			number = mobile.substring(0,5)+"****"+mobile.substring(9,mobile.length());
		else
			number = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
		return number;
	}
	/**
	 *   验证邮政编码
	 */
	public static boolean checkPost(String post) {
		if (post.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
	 */
	public static boolean isEquals(String... agrs) {
		String last = null;
		for (int i = 0; i < agrs.length; i++) {
			String str = agrs[i];
			if (isEmpty(str)) {
				return false;
			}
			if (last != null && !str.equalsIgnoreCase(last)) {
				return false;
			}
			last = str;
		}
		return true;
	}

	/**
	 * 返回一个高亮spannable
	 *
	 * @param content 文本内容
	 * @param color   高亮颜色
	 * @param start   起始位置
	 * @param end     结束位置
	 * @return 高亮spannable
	 */
	public static CharSequence getHighLightText(String content, int color, int start, int end) {
		if (TextUtils.isEmpty(content)) {
			return "";
		}
		start = start >= 0 ? start : 0;
		end = end <= content.length() ? end : content.length();
		SpannableString spannable = new SpannableString(content);
		CharacterStyle span = new ForegroundColorSpan(color);
		spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	/**
	 * 获取链接样式的字符串，即字符串下面有下划线
	 *
	 * @param resId 文字资源
	 * @return 返回链接样式的字符串
	 */
	public static Spanned getHtmlStyleString(Context context, int resId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"\"><u><b>").append(CommonUtil.getString(resId)).append(" </b></u></a>");
		return Html.fromHtml(sb.toString());
	}

	/**
	 * 格式化文件大小，不保留末尾的0
	 */
	public static String formatFileSize(long len) {
		return formatFileSize(len, false);
	}

	/**
	 * 格式化文件大小，保留末尾的0，达到长度一致
	 */
	public static String formatFileSize(long len, boolean keepZero) {
		String size;
		DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
		DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
		if (len < 1024) {
			size = String.valueOf(len + "B");
		} else if (len < 10 * 1024) {
			// [0, 10KB)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
		} else if (len < 100 * 1024) {
			// [10KB, 100KB)，保留一位小数
			size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
		} else if (len < 1024 * 1024) {
			// [100KB, 1MB)，个位四舍五入
			size = String.valueOf(len / 1024) + "KB";
		} else if (len < 10 * 1024 * 1024) {
			// [1MB, 10MB)，保留两位小数
			if (keepZero) {
				size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100)) + "MB";
			} else {
				size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100) + "MB";
			}
		} else if (len < 100 * 1024 * 1024) {
			// [10MB, 100MB)，保留一位小数
			if (keepZero) {
				size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10)) + "MB";
			} else {
				size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10) + "MB";
			}
		} else if (len < 1024 * 1024 * 1024) {
			// [100MB, 1GB)，个位四舍五入
			size = String.valueOf(len / 1024 / 1024) + "MB";
		} else {
			// [1GB, ...)，保留两位小数
			size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100) + "GB";
		}
		return size;
	}
}