package chatRedux.davidaye.beans;

public class ChatMessage {

	private String username;
	private String content;
	private TypeMessage typeMessage;
	
	public ChatMessage() {
		super();
	}
	
	public ChatMessage(String username, String content) {
		this.username=username;
		this.content =content;
		this.typeMessage = TypeMessage.CHAT;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}
	
	@Override
	public String toString() {
		
		return "{\"username\":\""+username+"\", \"content\": \""+content+"\",\"typeMessage\":\""+typeMessage+"\"}";
	}
}
