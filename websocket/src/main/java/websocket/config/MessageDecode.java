package websocket.config;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import websocket.beans.Message;
import websocket.beans.TypeMessage;

public class MessageDecode implements Decoder.Text<Message> {

	@Override
	public Message decode(String tojsonformat) throws DecodeException {
		
		Message message =  new Message();
		String [] tab = tojsonformat.split("\"");
		message.setUsername(tab[3]);
		message.setBody(tab[7]);
		message.setTypeMessage(TypeMessage.valueOf(tab[11]));
		return message;
	}
	
	@Override
	public boolean willDecode(String arg0) {
		return true;
	}
}
