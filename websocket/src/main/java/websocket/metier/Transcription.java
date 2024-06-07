package websocket.metier;

import java.util.ArrayList;
import java.util.List;

public class Transcription {

	private List<String> usernames = new ArrayList<>() ;
	private List<String> messages = new ArrayList<>();
	private int maxLines ;
	
	public List<String> getListUsernames(){
		return usernames;
	}
	
	public List<String> getListMessages(){
		return messages;
	}
	
	public Transcription (int maxLines) {
		this.maxLines = maxLines;
	}
	
	public String getLastMessage() {
		return messages.get(messages.size()-1);
	}
	
	public String getLastUsernames() {
		return usernames.get(usernames.size()-1);
	}
	
	public void addEntry(String username, String message) {
		
		if(maxLines < usernames.size()) {
			usernames.remove(0);
			messages.remove(0);
		}
		usernames.add(username);
		messages.add(message);
	}
}
