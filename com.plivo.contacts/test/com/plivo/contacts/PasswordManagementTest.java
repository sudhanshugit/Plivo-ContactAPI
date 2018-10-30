package com.plivo.contacts;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

import org.easymock.*;
import org.junit.*;
import com.plivo.contacts_Model.*;

import com.plivo.contacts_passwordManagement.PasswordManagement;

public class PasswordManagementTest extends EasyMockSupport{
	
	private PasswordManagement pm;
	
	
	/*@Before 
	public void setUp() {
		pm = new PasswordManagement();
		
	}*/
	
	/// here this one test will test both the methods of password management as we cannot hardcode
	/// bytearray so here we are using both methods to test each other.
	/// any of them will go bad the test will fail.
	@Test
	public void encryptPasswordTest() {
		PasswordSet ps = pm.encryptPassword("something");
		String password = pm.checkPassword("something",ps.salt);
		assertTrue(ps.encryptedPassword.equals(password));
		//assertTrue(true);
	}
	
}
