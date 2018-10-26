package com.plivo.contacts_Model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	private String email;
	private String name;
	private String phone_no;
	private Date date_added;
	private String address;
	
	public Contact() {}
	
	public Contact(String email,String name, String phone_no, Date date_added
			, String address) {
		this.email = email;
		this.name = name;
		this.phone_no = phone_no;
		this.date_added = date_added;
		this.address = address;
	}
	
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
	
	public String getPhone_no() {
		return phone_no;
	}
	
	public void setPhone_no(String p) {
		phone_no = p;
	} 
	
	public Date getDate_added() {
		return date_added;
	}
	
	public void setDate_added(Date da) {
		date_added = da;
	} 
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String a) {
		address = a;
	} 
	
	@Override
	public String toString(){
		return email+"::"+name+"::"+phone_no+"::"+date_added.toString()+"::"+address;
	}
	
}