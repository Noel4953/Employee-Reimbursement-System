package com.revature.ersmaven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import	com.revature.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.repositories.UserDAO;
import com.revature.services.AuthService;
import com.revature.util.ConnectionFactory;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		AuthService authService = new AuthService();
		System.out.println("Begin Try Block");
		try {
			User verify = authService.login(username, password);
			String destpage = "HomeServlet";
			
			if(verify!=null){
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				System.out.println("Try works");
				
			}else {
				String message = "Invalid Username/Password";
				response.sendError(404,message);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destpage);
	
			dispatcher.forward(request, response);
			
			response.sendRedirect("HomeServlet?username="+username);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		UserDAO userDao = new UserDAO();
//		Optional<User> user = userDao.getByUsername(username);
//		if(user.isPresent() && user.get().getPassword().equals(password)) {
//			int id = user.get().getId();
//			response.sendRedirect("HomeServlet?username="+username);
//		}else {
//			response.sendError(404,"Invalid Credentials");
//		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
