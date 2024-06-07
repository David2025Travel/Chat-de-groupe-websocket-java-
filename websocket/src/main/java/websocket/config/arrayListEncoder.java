package websocket.config;

import java.util.ArrayList;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class arrayListEncoder implements Encoder.Text<ArrayList<String>> {

	@Override
	public String encode(ArrayList<String> usernames) throws EncodeException {
		
		StringBuffer tab = new StringBuffer("[ \"");
		
		for(String name : usernames) {
			tab.append(name+"\",\"");
		}
		tab.append(" ]");
		return tab.toString();
	}
}
