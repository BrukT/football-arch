/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruk
 */
public class Hash {
    	public static String getSHA256(String s){
		if(s == null)
			return null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if(md != null){
			String hash = Base64.getEncoder().encodeToString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
			return hash;
		}
		else 
			return null;
	}
}
