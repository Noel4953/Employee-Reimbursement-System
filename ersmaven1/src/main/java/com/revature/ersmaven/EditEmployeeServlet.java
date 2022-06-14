package com.revature.ersmaven;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class EditEmployeeServlet
 */
@WebServlet("/EditEmployeeServlet")
public class EditEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEmployeeServlet() {
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
		
		try {
			
			Connection conn = ConnectionFactory.getInstance().getConnection();
			UserService userService = new UserService();
			User user = new User();
			HttpSession session = request.getSession(false);
			String username = (String)session.getAttribute("username");
			Optional<User> optionalUser = userService.getByUsername(username);
			user = optionalUser.get();
			
			int id = Integer.parseInt(request.getParameter("id"));
			String newUsername = request.getParameter("username");
			String password = request.getParameter("password");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			long phone = Long.parseLong(request.getParameter("phone"));

			if(newUsername==null)
				newUsername = username;
			if(password==null)
				password = user.getPassword();
			
			
			String query = "update user_table set username=?, password=?, first_name=?,last_name=?,email=?,phone=? where id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newUsername);
			pstmt.setString(2, password);
			pstmt.setString(3, fname);
			pstmt.setString(4, lname);
			pstmt.setString(5, email);
			pstmt.setLong(6, phone);
			pstmt.setInt(7, id);
			pstmt.executeUpdate();
			response.sendRedirect("HomeServlet?username="+username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
