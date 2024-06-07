package websocket.config;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import websocket.beans.Message;

public class MessageEncode implements Encoder.Text<Message> {

	@Override
	public String encode(Message arg0) throws EncodeException {
		return arg0.toString();
	}
	
	
	
}
