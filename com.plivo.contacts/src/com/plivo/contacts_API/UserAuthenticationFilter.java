package com.plivo.contacts_API;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.plivo.contacts_DataAccess.UserAccess;
import com.plivo.contacts_Model.PasswordSet;
import com.plivo.contacts_Model.User;
import com.plivo.contacts_passwordManagement.*;
//import com.sun.jersey.core.util.Base64;
import java.util.Base64;

@Provider
public class UserAuthenticationFilter implements javax.servlet.Filter
{
		@Context
	    private ResourceInfo resourceInfo;
		
		private static final String AUTHENTICATION_HEADER = "Authorization";
		private static final String AUTHENTICATION_SCHEME = "Basic";
		private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
                .entity("You cannot access this resource").build();
		private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
                .entity("Access blocked for all users !!").build();
		
		// once chuck norris tried to mock httprequest and legend says that he is still writing 
		// expectation rules for the mock. :) No unit tests.
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
				throws IOException, ServletException {
	        	              
	    	String authorization = null;
	    	if (request instanceof HttpServletRequest) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				authorization = httpServletRequest
						.getHeader(AUTHENTICATION_HEADER);

				// better injected
				boolean authenticationStatus = false;
				if(authorization == null || authorization.isEmpty())
	            {
					authenticationStatus = false;
	            }
				else
				{
					final String encodedUserPassword = authorization.replaceFirst("Basic"
							+ " ", "");
					byte[] decodedBytes = Base64.getDecoder().decode(
							encodedUserPassword);
					final String usernameAndPassword = new String(decodedBytes, "UTF-8");
					
					
					//final String usernameAndPassword = .replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		            final String username = tokenizer.nextToken();
		            final String password = tokenizer.nextToken();
		              
		            //Verifying Username and password
		            System.out.println(username);
		            System.out.println(password);
		            authenticationStatus = isUserAllowed(username, password);
				}
				
				if (authenticationStatus) 
				{
					filter.doFilter(request, response);
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					int x =0;
				} 
				else 
				{
					if (response instanceof HttpServletResponse) {
						HttpServletResponse httpServletResponse = (HttpServletResponse) response;
						httpServletResponse
								.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
			}
	    }
	    private boolean isUserAllowed(final String username, final String password)
	    {
	    	//String dummyUser = "krishna";
            //String dummyPswd = "radha";
	        UserAccess ua = new UserAccess();
	        PasswordSet p = ua.fetchUser(username);
	        String passwordcheck = PasswordManagement.checkPassword(password, p.salt);
	        //Step 1. Fetch password from database and match with password in argument
	        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
	        //Access the database and do this part yourself
	        //String userRole = userMgr.getUserRole(username);
	         
	        if(passwordcheck.equals(p.encryptedPassword))
	        {
	            return true;  
	        }
	        return false;
	    }
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}
	
}
