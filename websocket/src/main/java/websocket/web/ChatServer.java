package websocket.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.websocket.*;
import jakarta.websocket.EncodeException;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import websocket.beans.Message;
import websocket.config.MessageConfigurator;
import websocket.config.MessageDecode;
import websocket.config.MessageEncode;
import websocket.metier.Transcription;

@ServerEndpoint( value = "/chat", configurator = MessageConfigurator.class,decoders = {MessageDecode.class},encoders = {MessageEncode.class})
public class ChatServer {

	private Session session;
	private Transcription transcription;
	private ServerEndpointConfig serverEndpointConfig;
	private String user;
	
	@OnOpen
	public void ouverture(Session session, EndpointConfig endpointConfig) {
		this.session = session;
		this.serverEndpointConfig = (ServerEndpointConfig)endpointConfig;
		this.transcription = ((MessageConfigurator)serverEndpointConfig.getConfigurator()).getTranscription();
	}
	
	@OnError
	public void chatError(Throwable th) {
		System.out.println("Message erreur : "+th.getCause());
	}
	
	@OnClose
	public void closeChat(CloseReason cr) {
		if(cr.getCloseCode().getCode() == 1000) {
			envoyerDernierMessage();
			sendNameUserDisconnect();
		}
		else {
			String username = (String) session.getUserProperties().get("username");
			this.transcription.addEntry(username, "a quitté la reunion de façon brutale ...");
			envoyerDernierMessage();
			enleverDeLaListeDesConnecte();
			sendNameUserDisconnect();
		}
	}
	
	@OnMessage
	public void chatMessage(Message message) {
		
		switch (message.getTypeMessage()) {
			case CONNECT -> connectUser(message);
			case CHAT -> chatUser(message);
			case DISCONNECT -> disconnectUser(message);
		}
	}
	
	public void connectUser(Message message) {
		user = validationUsername(message.getUsername());
		session.getUserProperties().put("username", user);
		message.setUsername(user);
		envoyerMessageAmoi(message);
		envoyerTranscription();
		this.transcription.addEntry(user, message.getBody());
		envoyerDernierMessage(); // dans l'affichage, vous etes connecté 
		updateList();
		envoyerListDesConnecteAmoi();
		envoyerDernierNomConnecteAuxautre();
	}
	
	public String validationUsername(String username) {
		if(this.getUsersList().contains(username)) {
			username +=1;
			validationUsername(username);
		}
		return username;
	}
	
	public List<String> getUsersList(){
		@SuppressWarnings("unchecked")
		List<String> usersList = (List<String>)serverEndpointConfig
								.getUserProperties().get("usernames");
		
		return usersList == null ? new ArrayList<>(): usersList;
	}
	
	public void envoyerTranscription() {
		List<String> usernames = transcription.getListUsernames();
		List<String> messages = transcription.getListMessages();
		
		for(int i=0 ; i< usernames.size() ; i++) {
			envoyerMessageAmoi(new Message(usernames.get(i), messages.get(i)));
		}
	}
	
	public void envoyerMessageAmoi(Message message) {
		try {
			session.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	public void envoyerDernierMessage() {
		String username = transcription.getLastUsernames();
		String message = transcription.getLastMessage();
		
		for(Session ss : session.getOpenSessions()) {
			if( ss != session) {
				try {
					ss.getBasicRemote().sendObject(new Message(username, message));
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void updateList() {
		List<String> usernames = new ArrayList<>();
		
		for(Session ss : session.getOpenSessions()) {
			String name = (String) ss.getUserProperties().get("username");
			usernames.add(name);
		}
		serverEndpointConfig.getUserProperties().put("usernames", usernames);
	}
	
	public void envoyerDernierNomConnecteAuxautre() {
		
		for(Session ss : session.getOpenSessions()) {
			if(ss != session) {
				try {
					ss.getBasicRemote().sendText(user);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void envoyerListDesConnecteAmoi() {
		@SuppressWarnings("unchecked")
		List<String> listeUsersConnected = (List<String>)serverEndpointConfig
												.getUserProperties().get("usernames");
			for(String username : listeUsersConnected) {
				try {
					session.getBasicRemote().sendText(username);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	public void chatUser(Message message) {
		this.transcription.addEntry(message.getUsername(), message.getBody());
		envoyerDernierMessage();
	}
	
	public void disconnectUser(Message message) {
		try {
			this.transcription.addEntry(message.getUsername(), message.getBody());
			enleverDeLaListeDesConnecte();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enleverDeLaListeDesConnecte() {
		@SuppressWarnings( "unchecked" )
		List<String> usernames = (List<String>) serverEndpointConfig.getUserProperties().get("usernames");
		usernames.remove(user);
	}
	
	public void sendNameUserDisconnect() {
		for(Session ss : session.getOpenSessions()) {
			if(ss != session) {
				try {
					ss.getBasicRemote().sendText("{\"username\" : \""+user+"\" , \"typeMessage\" : \"userDisconnect\"}");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
