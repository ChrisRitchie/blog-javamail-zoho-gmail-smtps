package com.ritchie.email;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SendEmailServlet", urlPatterns = "/sendmail")
public class SendEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SendEmailService sendEmailService;

	public SendEmailServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String message = sendEmailService.sendEmail();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title></title>" + "</head><body>"
				+ message + "</body></html>");
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// not implemented

	}

}
