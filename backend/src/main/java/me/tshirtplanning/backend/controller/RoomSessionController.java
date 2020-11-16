package me.tshirtplanning.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.dto.RoomParticipation;
import me.tshirtplanning.backend.dto.RoomMessage;
import me.tshirtplanning.backend.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class RoomSessionController {
  private static Logger LOG = LoggerFactory.getLogger(RoomSessionController.class.getName());

  private final Map<String, RoomParticipation> sessionRoomParticipations = new ConcurrentHashMap<>(); //redis here
  private final Map<Long, List<WebSocketSession>> rooms = new ConcurrentHashMap<>();

  private final RoomService roomService;
  private final ObjectMapper objectMapper;

  public RoomSessionController(RoomService roomService, ObjectMapper objectMapper) {
    this.roomService = roomService;
    this.objectMapper = objectMapper;
  }

  public void connectToRoom(WebSocketSession session, RoomParticipation participation) {
    roomService.addUser(participation);
    sessionRoomParticipations.put(session.getId(), participation);
    List<WebSocketSession> webSocketSessions = rooms.computeIfAbsent(participation.getRoomId(), id -> new ArrayList<>());
    webSocketSessions.add(session);
    broadcastRoom(participation.getRoomId());
  }

  public void handleMessage(WebSocketSession session, RoomMessage message) {
    RoomParticipation participation = sessionRoomParticipations.get(session.getId());
    Long roomId = participation.getRoomId();
    switch (message.getType()) {
      case ESTIMATE:
        roomService.commitEstimate(roomId, participation.getUsername(), message.getPayload());
        break;
      case SHOW:
        roomService.showEstimates(roomId);
        break;
      case HIDE:
        roomService.hideEstimates(roomId);
        break;
      case CLEAR:
        roomService.clearEstimates(roomId);
        break;
    }
    broadcastRoom(roomId);
  }

  public void closeSession(String sessionId) {
    RoomParticipation remove = sessionRoomParticipations.remove(sessionId);
    if(remove != null) {
      Long roomId = remove.getRoomId();
      List<WebSocketSession> sessions = rooms.get(roomId);
      for (int i = 0; i < sessions.size(); i++) {
        if (sessions.get(i).getId().equals(sessionId)) {
          sessions.remove(i);
          break;
        }
      }
    }
  }

  private void broadcastRoom(Long roomId) {
    RoomDto roomDto = roomService.getRoom(roomId);
    List<WebSocketSession> roomSessions = rooms.get(roomId);
    roomSessions.forEach(session -> {
      try {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(roomDto)));
      } catch (Exception e) {
        LOG.error("Failed to send updated room", e);
      }
    });
  }
}
