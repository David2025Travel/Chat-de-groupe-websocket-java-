package chatRedux.davidaye.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import chatRedux.davidaye.beans.TranscriptBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/usernameServlet")
public class UsernamesServlet extends HttpServlet {

	@EJB
	private TranscriptBean transcriptBean;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = resp.getWriter();
		List<String> usernames = transcriptBean.getUsersConnect();
		out.println("<div class=\"col-3 mx-1 d-flex align-items-center justify-content-center bg-secondary\">");
		for(String user : usernames) {
			out.println(user+"<br>");
		}
		out.println("</div>");
	}
}
