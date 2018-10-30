package com.plivo.contacts;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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

import com.plivo.contacts_DataAccess.ContactAccess;
import com.plivo.contacts_DataAccess.UserAccess;
import com.plivo.contacts_Model.*;
import com.plivo.contacts_passwordManagement.PasswordManagement;



public class ContactAccessTest extends EasyMockSupport{
	
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
	public void addContact_Successful_Test() {
		
		//session.createquery
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery("from Contact where email = :email")).andReturn(qq);  
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    
	    EasyMock.expect(qq.setString("email", "abc@def.com")).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
	    
	    EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
	    t.commit();
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.addContact(c);
	    assertTrue(ret == 1);
	}
	
	@Test
	public void addContact_NotSuccessful_Test() {
		
		//session.createquery
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery("from Contact where email = :email")).andReturn(qq);  
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    cl.add(c);
	    EasyMock.expect(qq.setString("email", "abc@def.com")).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
	    t.commit();
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.addContact(c);
	    assertTrue(ret == 0);
	}
	
	@Test
	public void deleteContact_Successful_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    cl.add(c);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.executeUpdate()).andReturn(1);
		//EasyMock.expect(q.executeUpdate()).andReturn(2);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.deleteContact("abc@def.com");
	    assertTrue(ret == 1);
	}
	
	@Test
	public void deleteContact_NotSuccessful_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);  
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.deleteContact("abc@def.com");
	    assertTrue(ret == 0);
	}
	
	@Test
	public void updateContact_Successful_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    cl.add(c);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.executeUpdate()).andReturn(1);
		//EasyMock.expect(q.executeUpdate()).andReturn(2);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.updateContact(c);
	    assertTrue(ret == 1);
	}
	
	@Test
	public void updateContact_NotSuccessful_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);  
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    int ret = ca.updateContact(c);
	    assertTrue(ret == 0);
	}
	
	@Test
	public void getContactByEmail_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    cl.add(c);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.uniqueResult()).andReturn(c);
		EasyMock.expect(q.uniqueResult()).andReturn(c);
		//EasyMock.expect(q.executeUpdate()).andReturn(2);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    Contact ret = ca.getContactByEmail(c.getEmail());
	    assertTrue(ret.getEmail().equals(c.getEmail()));
	}
	
	@Test
	public void getContactByName_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    
	    Contact c1 = new Contact();
	    c1.setEmail("abc1@def.com");
	    c1.setName(c.getEmail());
	    c1.setDate_added(new Date());
	    c1.setAddress("address");
	    c1.setPhone_no("1234");
	    
	    Contact c2 = new Contact();
	    c2.setEmail("abc2@def.com");
	    c2.setName(c.getEmail());
	    c2.setDate_added(new Date());
	    c2.setAddress("address");
	    c2.setPhone_no("1234");
	    
	    
	    cl.add(c);
	    cl.add(c1);
	    cl.add(c2);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.uniqueResult()).andReturn(c);
		EasyMock.expect(q.uniqueResult()).andReturn(c);
		EasyMock.expect(  qq.setFirstResult(1)       ).andReturn(qq);
		EasyMock.expect(  qq.setMaxResults(2)       ).andReturn(qq);
		//EasyMock.expect(q.executeUpdate()).andReturn(2);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    List<Contact> ret = ca.getContactByName(c.getName(), 2, 1);
	    assertTrue(ret.get(0).getName().equals(c.getName())
	    		&& ret.get(1).getName().equals(c.getName())
	    		&& ret.get(2).getName().equals(c.getName()));
	}
	
	@Test
	public void getContacts_Test() {
		org.hibernate.query.Query qq = EasyMock.createMock(org.hibernate.query.Query.class);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    EasyMock.expect(session.createQuery(isA(String.class))).andReturn(qq);
	    
	    List<Contact> cl = new ArrayList<Contact>();
	    Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    
	    Contact c1 = new Contact();
	    c1.setEmail("abc1@def.com");
	    c1.setName(c.getEmail());
	    c1.setDate_added(new Date());
	    c1.setAddress("address");
	    c1.setPhone_no("1234");
	    
	    Contact c2 = new Contact();
	    c2.setEmail("abc2@def.com");
	    c2.setName(c.getEmail());
	    c2.setDate_added(new Date());
	    c2.setAddress("address");
	    c2.setPhone_no("1234");
	    
	    
	    cl.add(c);
	    cl.add(c1);
	    cl.add(c2);
	    EasyMock.expect(qq.setString(isA(String.class),isA(String.class))).andReturn(qq);
	    
	    EasyMock.expect(qq.list()).andReturn(cl);
	    
	    t= EasyMock.createMock(Transaction.class);
		q = EasyMock.createMock(Query.class);
		
		EasyMock.expect(session.beginTransaction()).andReturn(t);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		//EasyMock.expect(session.save(isA(Contact.class))).andReturn(1);
		EasyMock.expect(q.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.executeUpdate()).andReturn(1);
		EasyMock.expect(qq.uniqueResult()).andReturn(c);
		EasyMock.expect(q.uniqueResult()).andReturn(c);
		EasyMock.expect(  qq.setFirstResult(1)       ).andReturn(qq);
		EasyMock.expect(  qq.setMaxResults(2)       ).andReturn(qq);
		//EasyMock.expect(q.executeUpdate()).andReturn(2);
	    t.commit();
	    
	    
		EasyMock.replay(ssrb, ssr, meta, factory, session, t, qq);
	    //UserAccess ua = new UserAccess(ssrb, ssr, meta, factory,);
	    
	    ContactAccess ca = new ContactAccess(ssrb, ssr, meta, factory, session, t, qq);
	    
	    List<Contact> ret = ca.getContacts(2, 1);
	    assertTrue(ret.get(0).getName().equals(c.getName())
	    		&& ret.get(1).getName().equals(c.getName())
	    		&& ret.get(2).getName().equals(c.getName()));
	}

}
