package chatRedux.davidaye.config;

import chatRedux.davidaye.beans.ChatMessage;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class ChatMessageEncode implements Encoder.Text<ChatMessage> {

	@Override
	public String encode(ChatMessage chatMessage) throws EncodeException {
		
		return chatMessage.toString();
	}
}
