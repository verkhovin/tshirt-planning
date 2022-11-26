package me.tshirtplanning.backend.dto;

public class RoomMessage {
  private final Type type;
  private final String payload;

  public RoomMessage(Type type, String payload) {
    this.type = type;
    this.payload = payload;
  }

  public Type getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }

  public enum Type {
    ESTIMATE,
    SHOW,
    HIDE,
    CLEAR
  }
}
