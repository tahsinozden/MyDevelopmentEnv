package ozden.apps.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptionHelper {
	// secret key is used to encrypt all passwords
	final static private String secretKeyword = "tahsin-api";
	/**
	 * Generates encrypted password from plain text
	 * @param plainTextPassword
	 * @return
	 */
	public static String generateEncryptedPassword(String plainTextPassword){
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(secretKeyword);
        String encryptedPassword = bte.encrypt(plainTextPassword);
        return encryptedPassword;
	}
	
	/**
	 * 	Generates decryption of the encrypted password
	 * @param encryptedPassword
	 * @return
	 */
	public static String generateDecryptedPassword(String encryptedPassword){
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(secretKeyword);
        String plainText = bte.decrypt(encryptedPassword);
        return plainText;
	}
	
	/**
	 * It checks encrypted password and plain text password,
	 * returns true if they are equal, false if not equal
	 * @param encryptedPasswrod
	 * @param plainTextPassword
	 * @return
	 */
	public static boolean checkPasswordPairEqual(String encryptedPasswrod, String plainTextPassword){
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(secretKeyword);
        if (plainTextPassword.equals(bte.decrypt(encryptedPasswrod))){
        	return true;
        }
        else{
        	return false;
        }
	}
}
