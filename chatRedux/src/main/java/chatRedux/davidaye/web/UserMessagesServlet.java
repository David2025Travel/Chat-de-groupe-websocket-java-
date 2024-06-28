package chatRedux.davidaye.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import chatRedux.davidaye.beans.TranscriptBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/userMessages")
public class UserMessagesServlet extends HttpServlet{
	
	@EJB
	private TranscriptBean transcriptBean;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8;");
		// je recupere la map userMessage
		Map<String, List<String>> userMessages = transcriptBean.getUserMessages();
		PrintWriter out = resp.getWriter();
		
		for(String cle : userMessages.keySet()) {
			out.println("<div class=\"row mb-1\">");
			out.println("<div class=\"col-1 d-flex align-items-center justify-content-center bg-success\">");
			out.println(cle);
			out.println("</div>");
			out.println("<div class=\"col-5 bg-secondary d-flex align-items-center justify-content-center \">");
			userMessages.get(cle).forEach(t ->{
				out.println(t+"<br>");
			});
			out.println("</div>");
			out.println("</div>");
		}
	}
}
