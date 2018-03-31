package net.unir.emoodsic.common.entities;

import java.io.Serializable;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;

import net.unir.emoodsic.common.definitions.CommonDefs;

public class AuthToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 826772208084826431L;

	private int idUser;
	
	private String authTokenValue;
	
	public AuthToken() {
		super();
		this.setIdUser(-1);
		this.setAuthTokenValue("");
	}

	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the authTokenValue
	 */
	public String getAuthTokenValue() {
		return authTokenValue;
	}

	/**
	 * @param authTokenValue the authTokenValue to set
	 */
	public void setAuthTokenValue(String authTokenValue) {
		this.authTokenValue = authTokenValue;
	}
	
    /**
     * @return A random hexadecimal string of 32 upper case characters.
     */
    public static String createNewToken() {
        byte[] token;
        final SecureRandom random = new SecureRandom();
        token = new byte[CommonDefs.AUTHENTICATION_TOKEN_LENGTH];
        random.nextBytes(token);
        return Hex.encodeHexString(token).toUpperCase();
    }

}
