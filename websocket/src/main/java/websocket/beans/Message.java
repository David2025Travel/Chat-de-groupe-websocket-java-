package websocket.beans;

public class Message {

	private String username;
	private String body;
	private TypeMessage typeMessage;
	
	public Message () {
		
	}
	
	public Message(String username, String boby) {
		this.username=username;
		this.body = boby;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}
	
	@Override
	public String toString() {
		
		return "{\"username\":\""+username+"\", \"boby\":\""+body+"\", \"typeMessage\":\""+typeMessage+"\"}";
	}
}
