package com.plivo.contacts_Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String email;
	private String name;
	private String password;
	private byte[] password_salt;
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String em) {
		email = em;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String p) {
		password = p;
	}
	
	public byte[] getPassword_salt() {
		return password_salt;
	}
	
	public void setPassword_salt(byte[] s) {
		password_salt = s;
	}
	
	@Override
	public String toString(){
		// for security reasons no password and salt.
		return email+"::"+name;
	}
}
