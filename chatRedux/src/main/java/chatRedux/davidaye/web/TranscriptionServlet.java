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
@WebServlet("/transcription")
public class TranscriptionServlet extends HttpServlet {

	@EJB
	private TranscriptBean transcriptBean;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = resp.getWriter();
		List<String> usernames = transcriptBean.getUsernames();
		List<String> messages = transcriptBean.getMessages();
		
		out.println("<div class=\"col-5 mx-1 d-flex align-items-center justify-content-center bg-secondary\">");
		for(int i=0; i<usernames.size(); i++) {
			String user = usernames.get(i);
			String mess = messages.get(i);
			out.println(user+": "+mess+"<br>");
		}
		out.println("</div>");
		
	}
}
