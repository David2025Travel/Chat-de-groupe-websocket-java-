package websocket.config;

import jakarta.websocket.server.ServerEndpointConfig;
import websocket.metier.Transcription;

public class MessageConfigurator extends ServerEndpointConfig.Configurator {

	private Transcription transcription;
	
	public MessageConfigurator () {
		this.transcription = new Transcription(10);
	}
	
	public Transcription getTranscription() {
		return transcription;
	}
}
