package com.revature.ersmaven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Connection con = ConnectionFactory.getInstance().getConnection();
		UserService userService = new UserService();
		User user = new User();
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("username");
		Optional<User> optionalUser = userService.getByUsername(username);
		user = optionalUser.get();
		
		out.println("<head><title>Edit Employee Form</head></title>");
		out.println("<body><form method='GET' action='EditEmployeeServlet'><table><tr><th>Column Name</th><th> Column Value</th></tr>");
		
		out.println("<tr><td>Enter Employee ID : </td><td><input type='number' name='id' value='"+user.getId()+"'readonly/> </td></tr>");
		out.println("<tr><td>Enter Employee Username :</td><td><input type='text' onblur=sendInfo() name='username' value='"+user.getUsername()+"'/> </td></tr>");
		if(username!=null){  
			try{  
			PreparedStatement ps=con.prepareStatement("select * from user_table where username=?");  
			ps.setString(1,username);  
			ResultSet rs=ps.executeQuery();  
			if(rs.next()){  
			out.print("Unavailable! <img src='unchecked.gif'/>");  
			}else{  
			out.print("Available! <img src='checked.gif'/>");  
			}  
			}catch(Exception e){out.print(e);}  
			}else{  
			out.print("Invalid Username!");  
			}  
		out.println("<tr><td>Enter Employee Password :</td><td><input type='text' name='password' value='"+user.getPassword()+"'/> </td></tr>");
//		out.println("<tr><td>Retype Password :</td><td><input type='text' name='repeatpassword' value='"+"Retype Password"+"'/> </td></tr>");
//		if(user.getPassword().no)
		out.println("<tr><td>Enter Employee First Name :</td><td><input type='text' name='fname' value='"+user.getFirstName()+"'/> </td></tr>");
		out.println("<tr><td>Enter Employee Last Name :</td><td><input type='text' name='lname' value='"+user.getLastName()+"'/> </td></tr>");
		out.println("<tr><td>Enter Employee Email :</td><td><input type='email' name='email' value='"+user.getEmail()+"'/> </td></tr>");
		out.println("<tr><td>Enter Employee Phone No. :</td><td><input type='number' name='phone' value='"+user.getPhone()+"'/> </td></tr>");
		out.println("<tr><td>Enter Employee Address :</td><td><input type='text' name='address' value='"+user.getAddress()+"'/> </td></tr>");
		
		
		out.println("<tr><td><input type='reset' value='Clear Data'/> </td><td><input type='submit' value='Update Employee'/> </td></tr>");
		out.println("</table></form></body>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
