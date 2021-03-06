package com.plivo.contacts_API;

import java.util.List;

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

@Path("/Operate")
public class ContactsOperationService {
	
	private ContactAccess ca;
	
	public ContactsOperationService() {
		ca = new ContactAccess();
	}
	
	public ContactsOperationService(ContactAccess ca) {
		this.ca = ca;
	}
	
	@POST
    @Path("/addContact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response addContact(Contact c) {
		Response response = new Response();
		
		int retVal = ca.addContact(c);
		if(retVal == 1) {
			response.setMessage("Contact added");
			response.setStatus(true);
		}
		else {
			response.setMessage("Contact not added");
			response.setStatus(false);
		}
		
		return response;
	}
	
	@POST
	@Path("/deleteContact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response deleteContact(String email){
		Response response = new Response();
		try {
			
			
			int retVal = ca.deleteContact(email);
			if(retVal == 1) {
				response.setMessage("Delete Successful");
				response.setStatus(true);
			}
			else{
				response.setMessage("Delete failed");
				response.setStatus(false);
			}
		}
		catch(Exception e){
			//log e.message;
			response.setMessage("Delete failed");
			response.setStatus(false);
		}
		return response;
	}
	
	@POST
	@Path("/updateContact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response updateContact(Contact c){
		Response response = new Response();
		
		int retVal = ca.updateContact(c);
		if(retVal == 1) {
			response.setMessage("Update Successful");
			response.setStatus(true);
		}
		else{
			response.setMessage("Update failed");
			response.setStatus(false);
		}
		return response;
	}
	
	
	@GET
	@Path("/{id}/searchbyemail")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Contact searchUsersByEmail(@PathParam("id") String email){
		
		Contact c = ca.getContactByEmail(email);
		return c;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{id}/searchbyname")
	public List<Contact> searchUsersByName(@PathParam("id") String name,
											@QueryParam("pageSize") int pageSize,
											@QueryParam("pageStart") int pageStart){
		if(pageSize == 0) pageSize = 10;
		
		List<Contact> cl = ca.getContactByName(name, pageSize, pageStart);
		return cl;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/allcontact")
	public List<Contact> getAllUsers(@QueryParam("pageSize") int pageSize,
											@QueryParam("pageStart") int pageStart){
		if(pageSize == 0) pageSize = 10;
		
		List<Contact> cl = ca.getContacts(pageSize, pageStart);
		return cl;
	}
}
