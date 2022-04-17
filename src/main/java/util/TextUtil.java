package util;

public class TextUtil {

	/**
	 * 判断字符串是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
	
	/**
	 * 字符串是否为空判断（null && length == 0 && 空白符）
	 * @param str
	 * @return 是空时返回true
	 */
	public static boolean isBlank(CharSequence str) {
	    int strLen;
	    if (str != null && (strLen = str.length()) != 0) {
	        for(int i = 0; i < strLen; ++i) {
	            if (!Character.isWhitespace(str.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    } else {
	        return true;
	    }
	}
}
