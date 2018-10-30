package com.plivo.contacts;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.*;
import org.hibernate.Query;
//import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.*;

import com.plivo.contacts_DataAccess.UserAccess;
import com.plivo.contacts_Model.*;
import com.plivo.contacts_passwordManagement.PasswordManagement;


public class UserAccessTest  extends EasyMockSupport{
	
	private PasswordManagement pm;
	StandardServiceRegistry ssr;
	Metadata meta;
	SessionFactory factory;
	Session session;
	Transaction t;
	Query q;
	StandardServiceRegistryBuilder ssrb;
	
	@Before
	public void setUp() {
		meta = EasyMock.createMock(Metadata.class);
		factory = EasyMock.createMock(SessionFactory.class);
		session = EasyMock.createMock(Session.class);
		ssr = EasyMock.createMock(StandardServiceRegistry.class);
		
		ssrb = EasyMock.createMock(StandardServiceRegistryBuilder.class);
		EasyMock.expect(ssrb.configure("path")).andReturn(ssrb);
		MetadataSources mds = EasyMock.createMock(MetadataSources.class);
		MetadataBuilder mdb = EasyMock.createMock(MetadataBuilder.class);
		EasyMock.expect(mds.getMetadataBuilder()).andReturn(mdb);
		EasyMock.expect(mdb.build()).andReturn(meta);
		
		SessionFactoryBuilder sfb = EasyMock.createMock(SessionFactoryBuilder.class);
		EasyMock.expect(meta.getSessionFactoryBuilder()).andReturn(sfb);
		EasyMock.expect(sfb.build()).andReturn(factory);
		EasyMock.expect(factory.openSession()).andReturn(session);
		
		
		t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
	}
	
	
	@Test
	public void fetchUser_UserExists_Test() {
		
		//session.createquery
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery("from User where email = :username")).andReturn(qq);  
	    PasswordSet ps = pm.encryptPassword("something");
	    List<User> ul = new ArrayList<User>();
	    User u = new User();
	    u.setEmail("abc@def.com");
	    u.setName(u.getEmail());
	    u.setPassword(ps.encryptedPassword);
	    u.setPassword_salt(ps.salt);
	    ul.add(u);
	    EasyMock.expect(qq.setString("username", "abc@def.com")).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(ul);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    UserAccess ua = new UserAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    PasswordSet psv = ua.fetchUser("abc@def.com");
	    assertTrue(ps.encryptedPassword.equals(psv.encryptedPassword));
	}
	
	@Test
	public void fetchUser_UserDoesnotExists_Test() {
		
		//session.createquery
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery("from User where email = :username")).andReturn(qq);  
	    
	    List<User> ul = new ArrayList<User>();
	    EasyMock.expect(qq.setString("username", "abc@def.com")).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(ul);
	    EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    UserAccess ua = new UserAccess(ssrb, ssr, meta, factory, session, t, qq);
	    PasswordSet psv = ua.fetchUser("abc@def.com");
	    
	    assertTrue(psv.encryptedPassword.isEmpty());
	}
	
	
}
