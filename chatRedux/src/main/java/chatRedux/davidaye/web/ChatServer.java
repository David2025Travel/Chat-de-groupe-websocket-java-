package chatRedux.davidaye.web;

import java.io.IOException;
import java.util.List;

import chatRedux.davidaye.beans.ChatMessage;
import chatRedux.davidaye.beans.TranscriptBean;
import chatRedux.davidaye.config.ChatMessageConfigurator;
import chatRedux.davidaye.config.ChatMessageDecode;
import chatRedux.davidaye.config.ChatMessageEncode;
import chatRedux.davidaye.config.ChatServerBindingSessionHttp;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint( value = "/chat", 
				configurator = ChatMessageConfigurator.class ,
				encoders = {ChatMessageEncode.class},
				decoders = {ChatMessageDecode.class})
public class ChatServer {

	@EJB
	private TranscriptBean transcriptBean;
	private Session session;
	private HttpSession httpSession;
	private ChatServerBindingSessionHttp chatServerBindingSessionHttp;
	
	@OnOpen
	public void ouverture(Session session, EndpointConfig endpointConfig) {
		this.session = session;
		this.httpSession = (HttpSession) endpointConfig.getUserProperties().get("HTTP_SESSION");
		chatServerBindingSessionHttp = new ChatServerBindingSessionHttp(this);
		httpSession.setAttribute("bidingSession", chatServerBindingSessionHttp);
	}
	
	@OnMessage
	public void messageHandle(ChatMessage chatMessage) throws IOException, EncodeException {
		switch(chatMessage.getTypeMessage()) {
			case CHAT -> processChat(chatMessage);
			case CONNECT -> processNewUser(chatMessage);
			case DISCONNECT -> processDisconnect(chatMessage);
		}
	}
	
	@OnError
	public void erreur(Throwable th) {
		System.out.println("Message erreur : "+th.getMessage());
	}
	
	@OnClose
	public void fermeture(CloseReason clr) throws IOException, EncodeException {
		String username = (String) httpSession.getAttribute("username");
		if(username != null) {
			transcriptBean.addEntry(username, "a fait une pause de quelques minutes ...");
		}
		else {
			transcriptBean.addEntry(username, "est parti(e)");
		}
		sendLastMessage();
	}
	
	private void processNewUser(ChatMessage chatMessage) throws IOException, EncodeException {
		String name = validationUsername(chatMessage.getUsername());
		httpSession.setAttribute("username", name);
		chatMessage.setUsername(name);
		confirmUsername(chatMessage);
		updateList(name);
		transcriptBean.addEntry(name, "viens de rejoindre la discussion");
		sendTranscription();
		sendUserList();
		sendLastMessage();
	}
	
	private void confirmUsername(ChatMessage chatMessage) {
		sendMe(chatMessage);
	}
	
	private void sendUserList() throws IOException {
		sendTextMessage("newList");
		for(String username : transcriptBean.getUsersConnect()) {
			sendTextMessage(username);;
		}
	}
	
	private void sendTextMessage(String username) throws IOException {
		for(Session ss : session.getOpenSessions()) {
			ss.getBasicRemote().sendText(username);
		}
	}
	
	private void sendTranscription() {
		List<String> usernames = transcriptBean.getUsernames();
		List<String> messages = transcriptBean.getMessages();
		
		for(int i=0; i< usernames.size(); i++) {
			ChatMessage chatMessage = map(usernames.get(i), messages.get(i));
			sendMe(chatMessage);;
		}
	}
	
	private void sendMe(ChatMessage chatMessage) {
		try {
			session.getBasicRemote().sendObject(chatMessage);
		} catch (IOException | EncodeException e) {
			new RuntimeException();
		}
	}
	
	private ChatMessage map(String user, String mess) {
		return new ChatMessage(user, mess);
	}
	
	private void updateList(String name) {
		transcriptBean.addUserListConnect(name);
	}
	
	private String validationUsername(String username) {
		List<String> listeUserConnect = transcriptBean.getUsersConnect();
		if(listeUserConnect.contains(username)) {
			username+=1;
			validationUsername(username);
		}
		return username;
	}
	
	private void processChat(ChatMessage chatMessage) throws IOException, EncodeException {
		String username = chatMessage.getUsername();
		String content = chatMessage.getContent();
		transcriptBean.addEntry(username, content);
		sendLastMessage();
	}
	
	private void processDisconnect(ChatMessage chatMessage) throws IOException, EncodeException {
		String username = chatMessage.getUsername();
		transcriptBean.addEntry(username, "est parti(e)");
		transcriptBean.deleteUserListConnect(username);
		this.httpSession.removeAttribute("username");
		session.close( new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Fermeture volontaire"));
		sendLastMessage();
		sendTextMessage("newList");
		sendUserList();
	}
	
	private void sendLastMessage() throws IOException, EncodeException {
		ChatMessage chatMessage = map(transcriptBean.getLastUsername(), transcriptBean.getLastMessage());
		
		for(Session ss : session.getOpenSessions()) {
			if(ss != session) {
				ss.getBasicRemote().sendObject(chatMessage);
			}
		}
	}

	public void notifyEndSessionHttp(HttpSession httpSession) throws IOException, EncodeException {
		String username = (String) httpSession.getAttribute("username");
		transcriptBean.addEntry(username, "a quitté la reunion ...");
		sendLastMessage();
		session.close();
		
	}
}
