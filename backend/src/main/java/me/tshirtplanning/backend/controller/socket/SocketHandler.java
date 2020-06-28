package me.tshirtplanning.backend.controller.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.tshirtplanning.backend.controller.RoomSessionController;
import me.tshirtplanning.backend.dto.RoomParticipation;
import me.tshirtplanning.backend.dto.RoomMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
  private final Map<String, WebSocketSession> unhandledSessions = new ConcurrentHashMap<>();
  private final ObjectMapper objectMapper;
  private final RoomSessionController roomController;

  public SocketHandler(ObjectMapper objectMapper, RoomSessionController roomController) {
    this.objectMapper = objectMapper;
    this.roomController = roomController;
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    WebSocketSession knownSession = unhandledSessions.get(session.getId());
    if (knownSession != null) {
      unhandledSessions.remove(session.getId());
      roomController.connectToRoom(session, objectMapper.readValue(message.getPayload(), RoomParticipation.class));
      session.sendMessage(new TextMessage("OK"));
      return;
    }
    roomController.handleMessage(session, objectMapper.readValue(message.getPayload(), RoomMessage.class));
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws IOException {
    unhandledSessions.put(session.getId(), session);
    session.sendMessage(new TextMessage("init"));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    roomController.closeSession(session.getId());
  }
}
