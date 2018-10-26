package com.plivo.contacs_passwordManagement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManagement {
	
	
	public static PasswordSet encryptPassword(String password) {
		PasswordSet p = null;
		try {
			byte[] salt = getSalt();
			String encryptPassword = 
					get_SHA_256_SecurePassword(password, salt);
			p = new PasswordSet();
			p.salt = salt;
			p.encryptedPassword = encryptPassword;
		}
		catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
		return p;
	}
	
	public static String checkPassword(String password , byte[] salt) {
		return get_SHA_256_SecurePassword(password, salt);
	}
	
	private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
		String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	
	
	private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
	
}
