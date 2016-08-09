package com.simon.framework.util.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.simon.framework.util.StringUtils;

/**
 * 
 * @ClassName: StringEncryptUtil <br>
 * @Description: String encrypt util. <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public final class StringEncryptUtil {
	private static final String ALGORITHM = "DES";

	/**
	 * The Default Key.
	 */
	public static final String DEFAULT_KEY = "asdfsadf@#FDDD$%^$%^%^&*&asdf24243423234";

	private static byte[] decrypt(final byte[] encryptedByte, final byte[] key) throws Exception {
		final SecureRandom sr = new SecureRandom();
		final DESKeySpec dks = new DESKeySpec(key);
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		final SecretKey keySpec = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
		return cipher.doFinal(encryptedByte);
	}

	public static String decrypt(final String encryptedString) throws Exception {
		final byte[] bEn = StringUtils.parseBytesByHexString(encryptedString);
		final byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
		return new String(orginal);
	}

	public static String decrypt(final String encryptedString, final String key) throws Exception {
		final byte[] bEn = StringUtils.parseBytesByHexString(encryptedString);
		final byte[] orginal = decrypt(bEn, key.getBytes());
		return new String(orginal);
	}

	private static byte[] encrypt(final byte[] originalByte, final byte[] key) throws Exception {
		final SecureRandom sr = new SecureRandom();
		final DESKeySpec dks = new DESKeySpec(key);
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		final SecretKey keySpec = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
		return cipher.doFinal(originalByte);

	}

	public static String encrypt(final String originalString) throws Exception {
		final byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
		return StringUtils.parseHexStringFromBytes(bEn);
	}

	public static String encrypt(final String originalString, final String key) throws Exception {
		final byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
		return StringUtils.parseHexStringFromBytes(bEn);
	}
}
