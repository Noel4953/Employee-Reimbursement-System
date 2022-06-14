package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.CredentialNotFoundException;

public class UserDAO {
	private User currentUser;
    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public Optional<User> getByUsername(String username) {
    		Optional<User> optionalUSer = Optional.empty();
    	try {
    		Connection conn = ConnectionFactory.getInstance().getConnection();
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select * from user_table where username ='"+username+"'");
        	if (rs.next()) {
        		User user = new User();
        	user.setId(rs.getInt("id"));
        	user.setUsername(rs.getString("username"));
        	user.setPassword(rs.getString("password"));
        	if(rs.getString("role").equalsIgnoreCase("finance manager")) {
        		user.setRole(Role.FINANCE_MANAGER);
        	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
        		user.setRole(Role.EMPLOYEE);
        	}
        	user.setAddress(rs.getString("address"));
        	user.setFirstName(rs.getString("first_name"));
        	user.setLastName(rs.getString("last_name"));
        	user.setEmail(rs.getString("email"));
        	user.setPhone(rs.getLong("phone"));
        	optionalUSer = Optional.of(user);
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return optionalUSer;
    }

    public Optional<User> getByEmail(String email) {
		Optional<User> optionalUSer = Optional.empty();
	try {
		Connection conn = ConnectionFactory.getInstance().getConnection();
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from user_table where email ='"+email+"'");
    	if (rs.next()) {
    		User user = new User();
    	user.setId(rs.getInt("id"));
    	user.setUsername(rs.getString("username"));
    	user.setPassword(rs.getString("password"));
    	if(rs.getString("role").equalsIgnoreCase("finance manager")) {
    		user.setRole(Role.FINANCE_MANAGER);
    	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
    		user.setRole(Role.EMPLOYEE);
    	}
    	user.setAddress(rs.getString("address"));
    	user.setFirstName(rs.getString("first_name"));
    	user.setLastName(rs.getString("last_name"));
    	user.setEmail(rs.getString("email"));
    	user.setPhone(rs.getLong("phone"));
    	optionalUSer = Optional.of(user);
    	}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    return optionalUSer;
}

    
    public List<User> getAllUsers(){
    	List<User> userList = new ArrayList<User>();
    	try {
    		Connection conn = ConnectionFactory.getInstance().getConnection();
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select * from user_table");
        	while (rs.next()) {
        		User user = new User();
        	user.setId(rs.getInt("id"));
        	user.setUsername(rs.getString("username"));
        	user.setPassword(rs.getString("password"));
        	if(rs.getString("role").equalsIgnoreCase("finance manager")) {
        		user.setRole(Role.FINANCE_MANAGER);
        	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
        		user.setRole(Role.EMPLOYEE);
        	}
        	user.setAddress(rs.getString("address"));
        	user.setFirstName(rs.getString("first_name"));
        	user.setLastName(rs.getString("last_name"));
        	user.setEmail(rs.getString("email"));
        	user.setPhone(rs.getLong("phone"));
        	userList.add(user);
        	}
    	} catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
    	}
    	return userList;
    }
    
    public Optional<User> getByUserId(int id) {
		Optional<User> optionalUSer = Optional.empty();
	try {
		Connection conn = ConnectionFactory.getInstance().getConnection();
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from user_table where id ="+id);
    	if (rs.next()) {
    		User user = new User();
    	user.setId(rs.getInt("id"));
    	user.setUsername(rs.getString("username"));
    	user.setPassword(rs.getString("password"));
    	if(rs.getString("role").equalsIgnoreCase("finance manager")) {
    		user.setRole(Role.FINANCE_MANAGER);
    	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
    		user.setRole(Role.EMPLOYEE);
    	}
    	user.setAddress(rs.getString("address"));
    	user.setFirstName(rs.getString("first_name"));
    	user.setLastName(rs.getString("last_name"));
    	user.setEmail(rs.getString("email"));
    	user.setPhone(rs.getLong("phone"));
    	optionalUSer = Optional.of(user);
    	}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    return optionalUSer;
}
    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     */
    public User create(User userToBeRegistered) {
    		User user = new User();
    	try {
    		Connection conn = ConnectionFactory.getInstance().getConnection();
    		String insertQuery = "insert into user_table (username,password,role) values(?,?,?)";
        	PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        	pstmt.setString(1, userToBeRegistered.getUsername());
        	pstmt.setString(2, userToBeRegistered.getPassword());
        	pstmt.setObject(3, userToBeRegistered.getRole().toString());
        	pstmt.execute();
        	
        	ResultSet rs = pstmt.getGeneratedKeys();
        	int id =0;
        	if(rs.next()) {
        		id = rs.getInt(1);
        	}
        	
        	user.setId(id);
        	user.setUsername(userToBeRegistered.getUsername());
        	user.setPassword(userToBeRegistered.getPassword());
        	user.setRole(userToBeRegistered.getRole());
        	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return user;
    }

public User login(String username, String password) {
			User user = new User();
		try {
    		Connection conn = ConnectionFactory.getInstance().getConnection();
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select * from user_table where username ='"+username+"'");
        	if (rs.next()) {
        	if(rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
        		user.setUsername(rs.getString("username"));
            	user.setPassword(rs.getString("password"));
        		if(rs.getString("role").equalsIgnoreCase("finance manager")) {
            		user.setRole(Role.FINANCE_MANAGER);
            	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
            		user.setRole(Role.EMPLOYEE);
            	}
            	user.setAddress(rs.getString("address"));
            	user.setFirstName(rs.getString("first_name"));
            	user.setLastName(rs.getString("last_name"));
            	user.setEmail(rs.getString("email"));
            	user.setPhone(rs.getLong("phone"));
            	
            	this.currentUser = user;
            	System.out.println("adding value to currentUser");
        	}else {
        		user = null;
        		}
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    return user;
//	Optional<User> user = userDao.getByUsername(username);
//	if(user.isPresent() && user.get().getPassword().equals(password)) {
//	int id = user.get().getId();
	
    }
public Optional<User> exampleRetrieveCurrentUser() {
	int id = currentUser.getId();
	Optional<User> optionalUser = Optional.empty();
	try {
		Connection conn = ConnectionFactory.getInstance().getConnection();
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from user_table where id ="+id);
    	if (rs.next()) {
    		
    	currentUser.setId(rs.getInt("id"));
    	currentUser.setUsername(rs.getString("username"));
    	currentUser.setPassword(rs.getString("password"));
    	if(rs.getString("role").equalsIgnoreCase("finance manager")) {
    		currentUser.setRole(Role.FINANCE_MANAGER);
    	}else if(rs.getString("Role").equalsIgnoreCase("employee")) {
    		currentUser.setRole(Role.EMPLOYEE);
    	}
    	currentUser.setAddress(rs.getString("address"));
    	currentUser.setFirstName(rs.getString("first_name"));
    	currentUser.setLastName(rs.getString("last_name"));
    	currentUser.setEmail(rs.getString("email"));
    	currentUser.setPhone(rs.getLong("phone"));
    	optionalUser = Optional.of(currentUser);
    	}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    return optionalUser;
}
}