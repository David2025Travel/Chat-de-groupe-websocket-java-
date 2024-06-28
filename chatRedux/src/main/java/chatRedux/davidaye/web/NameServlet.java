package chatRedux.davidaye.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/nameServlet")
public class NameServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		out.println("<div class=\"col-3 mx-1  bg-secondary d-flex align-items-center justify-content-center\">");
		out.println(username);
		out.println("</div>");
	}
}
