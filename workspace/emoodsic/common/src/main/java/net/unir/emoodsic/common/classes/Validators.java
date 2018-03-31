/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Álvaro
 *
 * Contains property validators
 */
public final class Validators {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
	//private static final String PASSWORD_PATTERN = "^.{6,20}$";
	private static final String PASSWORD_PATTERN = "^.{6,24}$";
	
    /**
    *
    */
    private static final String TOKEN_PATTERN = "[A-F0-9]{32}";
   
    private Validators() {
        super();
    }
    
    /**
     * Validates email with regular expression.
     *
     * @param email
     *            email for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean validateEmail(final String email) {
        return Validators.matchRegex(EMAIL_PATTERN, email);
    }
    
    /**
     * Validate token with regular expression. Token must be a hexadecimal
     * string of 32 upper case characters.
     *
     * @param token
     *            token for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean validateToken(final String token) {
        return matchRegex(TOKEN_PATTERN, token);
    }    
    
    /**
     * Validates password with regular expression.<br>
     *
     * @note 
     * Password must be between 6 and 24 characters length and must include
     * lower cases, upper cases, numbers and special characters.
     *
     * @param pPassword	token for validation
     * @param pEmail	email to check it is not contained in the password

     * @return true if password is ok, false otherwise
     */
    public static boolean validatePassword(final String pPassword,
            final String pEmail) {
        return Validators.matchPassword(PASSWORD_PATTERN, pPassword, pEmail);
    }
    
    /**
     * Checks if the password matches the requirements to be valid.
     *
     * @param   regex     	the regex to use in the matcher.
     * @param   password	the text to validate
     * @param 	email		email to check it is not contained in the password
     * @return true if password is ok, false otherwise
     */
    private static boolean matchPassword(final String regex, final String password, final String email) {

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);
        // Check if the email is in the password
        if ((email != null) && (!email.isEmpty()) && (password.contains(email))) {
            return false;
        }

        return matcher.matches();
    }
   
    /**
     * Validates a string with a regex.
     *
     * @param regex             the regular expression to match.
     * @param stringToValidate  the {@code String} to check if matches the regex.
     * @return                  true if matches, false otherwise.
     */
    private static boolean matchRegex(final String regex, final String stringToValidate) {
        if (stringToValidate == null) {
            return false;
        }
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(stringToValidate);

        return matcher.matches();
    }

}
