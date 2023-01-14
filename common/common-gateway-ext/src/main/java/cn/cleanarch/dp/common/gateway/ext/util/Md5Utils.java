package cn.cleanarch.dp.common.gateway.ext.util;

import java.security.MessageDigest;

/**
 * @Description MD5加密方法
 * @author JL
 * @version V1.0
 */
public class Md5Utils {

	private final static String CHARSET_NAME = "UTF-8";

	private Md5Utils(){
	}

	/**
	 * @Description md5加密
	 * @param source
	 * @return
	 */
	public static String md5Str(String source) {
		return md5Str(source, CHARSET_NAME);
	}

	/**
	 * @Description md5加密
	 * @param source
	 * @param charset
	 * @return
	 */
	public static String md5Str(String source, String charset) {
		int number ;
		MessageDigest md ;
		StringBuffer md5Str = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			byte [] bs = md.digest(source.getBytes(charset));
			for (byte b : bs) {
                number = b & 0xff;
                if (number < 16) {
                	md5Str.append(0);
				}
                md5Str.append(Integer.toHexString(number));
            }
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		return md5Str.toString();
	}
}
