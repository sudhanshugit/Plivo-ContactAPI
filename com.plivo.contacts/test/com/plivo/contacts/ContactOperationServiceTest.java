package com.plivo.contacts;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.plivo.contacts_DataAccess.ContactAccess;
import com.plivo.contacts_Model.*;

import com.plivo.contacts_API.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.*;



public class ContactOperationServiceTest extends EasyMockSupport{
	
	
	
	@Test
	public void addContact_SuccessTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
		EasyMock.expect(ca.addContact(isA(Contact.class))).andReturn(1);
		EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		
		Response r = cos.addContact(c);
		assertTrue(r.isStatus());
	}
	
	@Test
	public void addContact_FailTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
		EasyMock.expect(ca.addContact(c)).andReturn(0);
		EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Response r = cos.addContact(c);
		assertTrue(!r.isStatus());
	}
	@Test
	public void deleteContact_SuccessTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
		EasyMock.expect(ca.deleteContact(c.getEmail())).andReturn(1);
		EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Response r = cos.deleteContact(c.getEmail());
		assertTrue(r.isStatus());
	}
	@Test
	public void deleteContact_FailTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
		EasyMock.expect(ca.deleteContact(c.getEmail())).andReturn(0);
		EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Response r = cos.deleteContact(c.getEmail());
		assertTrue(!r.isStatus());
	}
	@Test
	public void updateContact_SuccessTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
		EasyMock.expect(ca.updateContact(isA(Contact.class))).andReturn(1);
		EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Response r = cos.updateContact(c);
		assertTrue(r.isStatus());
	}
	@Test
	public void updateContact_FailTest(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    EasyMock.expect(ca.updateContact(c)).andReturn(0);
	    EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Response r = cos.updateContact(c);
		assertTrue(!r.isStatus());
	}
	@Test
	public void searchUsersByEmail_Test(){
		ContactAccess ca = EasyMock.createMock(ContactAccess.class);
		Contact c = new Contact();
	    c.setEmail("abc@def.com");
	    c.setName(c.getEmail());
	    c.setDate_added(new Date());
	    c.setAddress("address");
	    c.setPhone_no("1234");
	    EasyMock.expect(ca.getContactByEmail(c.getEmail())).andReturn(c);
	    EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		Contact cr = cos.searchUsersByEmail(c.getEmail());
		assertTrue(cr.getEmail().equals(c.getEmail()));
	}
	@Test
	public void searchUsersByName_Test(){
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
	    
	    ContactAccess ca = EasyMock.createMock(ContactAccess.class);
	    EasyMock.expect(ca.getContactByName(c.getName(), 2, 1)).andReturn(cl);
	    EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		
		List<Contact> ret = cos.searchUsersByName(c.getName(), 2, 1);
		assertTrue(ret.get(0).getName().equals(c.getName())
	    		&& ret.get(1).getName().equals(c.getName())
	    		&& ret.get(2).getName().equals(c.getName()));
	    
	}
	@Test
	public void getAllUsers_Test(){
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
	    ContactAccess ca = EasyMock.createMock(ContactAccess.class);
	    EasyMock.expect(ca.getContacts(2, 1)).andReturn(cl);
	    EasyMock.replay(ca);
		ContactsOperationService cos = new ContactsOperationService(ca);
		
		List<Contact> ret = cos.getAllUsers(2, 1);
		assertTrue(ret.get(0).getName().equals(c.getName())
	    		&& ret.get(1).getName().equals(c.getName())
	    		&& ret.get(2).getName().equals(c.getName()));
	}
}
