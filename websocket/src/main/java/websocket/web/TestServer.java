package websocket.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/test")
public class TestServer {
	
	@OnOpen
	public void ouverture(Session session) throws IOException, EncodeException {
		
		System.out.println("Dans la methode d'ouverture");
		
	}
	
	@OnMessage
	public void message(String sms) {
		System.out.println("Message recue : "+sms);
	}
	@OnError
	public void error(Throwable th) {
		System.out.println("cause : "+th.getMessage());
	}
	
	@OnClose
	public void fermeture(CloseReason clr) {
		System.out.println("methode annotée avec on close ");
	}
}
