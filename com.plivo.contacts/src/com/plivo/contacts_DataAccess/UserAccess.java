package com.plivo.contacts_DataAccess;
import com.plivo.contacs_passwordManagement.*;
import com.plivo.contacts_Model.PasswordSet;
import com.plivo.contacts_Model.User;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;  
import java.util.Random;


public class UserAccess {
	public static void addUser(String userName, String password) {
		
		PasswordSet p = PasswordManagement.encryptPassword(password);
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("./com/plivo/contacts_DataAccess/hibernate.cfg.xml").build();  
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
      
	    SessionFactory factory = meta.getSessionFactoryBuilder().build();  
	    Session session = factory.openSession();  
	    Transaction t = session.beginTransaction();  
	    
	    Query q = session.createQuery("from User where email = :username");
	    q.setString("username", userName);
	    List<User> ul = q.list();
	    if(ul.isEmpty()) {
	    	User u = new User();
	    	u.setEmail(userName);
	    	u.setName(userName);
	    	u.setPassword(p.encryptedPassword);
	    	u.setPassword_salt(p.salt);
	    	t.commit();
	    }	
	}
	
	public static PasswordSet fetchUser(String userName) {
		
		PasswordSet p = new PasswordSet();
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("./com/plivo/contacts_DataAccess/hibernate.cfg.xml").build();  
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
      
	    SessionFactory factory = meta.getSessionFactoryBuilder().build();  
	    Session session = factory.openSession();  
	    Transaction t = session.beginTransaction();  
	    Query q = session.createQuery("from User where email = :username");
	    q.setString("username", userName);
	    List<User> ul = q.list();
	    if(ul.isEmpty()) {
	    	Random rn = new Random();
	    	byte[] bt = new byte[10];
	    	rn.nextBytes(bt);
	    	p.salt = bt;
	    	p.encryptedPassword = "";
	    	return p;
	    }
		p.salt = ul.get(0).getPassword_salt();
		p.encryptedPassword = ul.get(0).getPassword();
		return p;
	}

}