package com.revature.ersmaven;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.AuthService;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

/**
 * Servlet implementation class CreateReimbursementServlet
 */
@WebServlet("/CreateReimbursementServlet")
public class CreateReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReimbursementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				//response.getWriter().append("Served at: ").append(request.getContextPath());
				PrintWriter out = response.getWriter();
				response.setContentType("text/application");
				
				ReimbursementService reimbursementService = new ReimbursementService();
				Reimbursement reimbursementToBeCreated = new Reimbursement();
				Double amount = Double.parseDouble(request.getParameter("amount"));
				String description = request.getParameter("description");
				Object receipt = request.getParameter("receipt");
				
				//String username = request.getParameter("username");
				HttpSession session = request.getSession(false);
				String username = (String)session.getAttribute("username");
				
				//int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(username);
				UserDAO userDao = new UserDAO();
				Optional<User> optionalUser = userDao.getByUsername(username);
				User user = optionalUser.get();
				int id = user.getId();
				
				reimbursementToBeCreated.setAmount(amount);
				reimbursementToBeCreated.setDescription(description);
				reimbursementToBeCreated.setReceipt(receipt);
				reimbursementToBeCreated.setAuthor(id);
				
			
				Reimbursement registeredReimbursement = reimbursementService.create(reimbursementToBeCreated);
				if(registeredReimbursement!=null)
					response.sendRedirect("HomeServlet?username="+username);
				else
					throw new RegistrationUnsuccessfulException("Registration is UnSuccessful");
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
