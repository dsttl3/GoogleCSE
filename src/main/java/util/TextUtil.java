package util;

/**
 * @author dsttl3
 */
public class TextUtil {

	/**
	 * 判断字符串是否是空
	 * @param str 判断的字符串
	 * @return 是空时返回true
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
    }
	
	/**
	 * 字符串是否为空判断（null && length == 0 && 空白符）
	 * @param str 判断的字符串
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
		}
		return true;
	}
}
