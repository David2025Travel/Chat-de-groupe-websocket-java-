package chatRedux.davidaye.config;

import java.io.StringReader;

import chatRedux.davidaye.beans.ChatMessage;
import chatRedux.davidaye.beans.TypeMessage;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class ChatMessageDecode implements Decoder.Text<ChatMessage> {

	@Override
	public ChatMessage decode(String formatJson) throws DecodeException {
		JsonObject json = Json.createReader(new StringReader(formatJson)).readObject();
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(json.getString("username"));
		chatMessage.setContent(json.getString("content"));
		chatMessage.setTypeMessage(TypeMessage.valueOf(json.getString("typeMessage")));
		return chatMessage;
	}
	
	@Override
	public boolean willDecode(String arg0) {
		try {
			Json.createReader(new StringReader(arg0)).read();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
