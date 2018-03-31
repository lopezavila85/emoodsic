/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Álvaro
 *
 */
public final class CryptoUtils {

	private CryptoUtils() {
		super();
	}
	
	public static String getHashMd5(final String rawString) {
		
        String hash = null;
        try {

        	MessageDigest md = MessageDigest.getInstance("MD5");
        	byte[] thedigest = md.digest(rawString.getBytes("UTF-8"));
        	hash = Hex.encodeHexString(thedigest).toUpperCase();
        	
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        } catch (UnsupportedEncodingException e) {
            hash = null;
        }
        return hash;
	}
}
