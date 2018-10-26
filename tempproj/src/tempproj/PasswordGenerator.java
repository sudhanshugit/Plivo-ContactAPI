package tempproj;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import java.sql.*;  

import com.plivo.contacs_passwordManagement.*;
public class PasswordGenerator {

	public static void main(String[] args) {
		PasswordSet p1 = PasswordManagement.encryptPassword("plivo_user1");
		//String s = new String(p.salt, Charset.forName("UTF-8"));
		String username1 = "user1@abc";
		PasswordSet p2 = PasswordManagement.encryptPassword("plivo_user1");
		//String s = new String(p.salt, Charset.forName("UTF-8"));
		String username2 = "user2@abc";
		PasswordSet p3 = PasswordManagement.encryptPassword("plivo_user1");
		//String s = new String(p.salt, Charset.forName("UTF-8"));
		String username3 = "user3@abc";
		PasswordSet p4 = PasswordManagement.encryptPassword("plivo_user1");
		//String s = new String(p.salt, Charset.forName("UTF-8"));
		String username4 = "user4@abc";
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/Plivo","user1_plivo","user_plivo");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			String query = "insert into users (email, name, password,password_salt) values (?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1,username1);
			ps.setString(2,username1);
			ps.setString(3,p1.encryptedPassword);
			ps.setBytes(4, p1.salt);
			ps.execute();
			
			
			ps.setString(1,username2);
			ps.setString(2,username2);
			ps.setString(3,p2.encryptedPassword);
			ps.setBytes(4, p2.salt);
			ps.execute();
			
			
			ps.setString(1,username3);
			ps.setString(2,username3);
			ps.setString(3,p3.encryptedPassword);
			ps.setBytes(4, p3.salt);
			ps.execute();
			
			
			ps.setString(1,username4);
			ps.setString(2,username4);
			ps.setString(3,p4.encryptedPassword);
			ps.setBytes(4, p4.salt);
			ps.execute();
			
			String sql = "Select password,password_salt from users where email = 'user1@abc'"; 
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				byte[] bs= rs.getBytes("password_salt");
				String pq = rs.getString("password");
				String pd = PasswordManagement.checkPassword("plivo_user1", bs);
				System.out.println(pq);
				System.out.println(pd);
			}
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
			
		

	}

}
