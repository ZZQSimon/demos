package com.simon.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 
 * @ClassName: EncodeUtils <br>
 * @Description: 各种格式的编码加码工具类.集成Commons-Codec,Commons-Lang及JDK提供的编解码方法 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class EncodeUtils {
	/** 默认编码集. */
	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(final String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(final byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(final byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(final String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (final DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Hex编码.
	 */
	public static String hexEncode(final byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(final String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(final String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(final String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码.
	 */
	public static String urlDecode(final String input, final String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(final String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码.
	 */
	public static String urlEncode(final String input, final String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(final String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xtmlUnescape(final String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

}
