package chatRedux.davidaye.config;

import java.io.IOException;

import chatRedux.davidaye.web.ChatServer;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.websocket.EncodeException;

public class ChatServerBindingSessionHttp implements HttpSessionBindingListener{

	private ChatServer chatServer;
	
	public ChatServerBindingSessionHttp(ChatServer chatServer) {
		this.chatServer = chatServer;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			chatServer.notifyEndSessionHttp(event.getSession());
		} catch (IOException | EncodeException e) {
			new RuntimeException();
		}
	}
}
