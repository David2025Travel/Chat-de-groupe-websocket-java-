package chatRedux.davidaye.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Singleton;

@Singleton
public class TranscriptBean {

	private List<String> usernames = new ArrayList<>();
	private List<String> messages = new ArrayList<>();
	private List<String> listUserConnect = new ArrayList<>();
	private Map<String, List<String>> userMessages = new HashMap<>();
	private int maxLine;
	
	public String getLastUserConnect() {
		return listUserConnect.getLast();
	}
	public List<String> getUsernames(){
		return usernames;
	}
	
	public List<String> getMessages(){
		return messages;
	}
	
	public TranscriptBean() {
		this.maxLine = 30;
	}
	
	public Map<String, List<String>> getUserMessages(){
		return userMessages;
	}
	public List<String> getUsersConnect(){
		return listUserConnect;
	}
	public String getLastMessage() {
		return messages.get(messages.size()-1);
	}
	
	public String getLastUsername() {
		return usernames.get(usernames.size()-1);
	}
	
	public void addUserListConnect(String username) {
		userMessages.put(username, new ArrayList<>());
		listUserConnect.add(username);
	}
	
	public void deleteUserListConnect(String username) {
		listUserConnect.remove(username);
	}
	
	public void addEntry(String username, String message) {

		userMessages.get(username).add(message);
		if(usernames.size() > maxLine) {
			usernames.remove(0);
			messages.remove(0);
		}
		usernames.add(username);
		messages.add(message);
	}
	
}
