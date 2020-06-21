package me.tshirtplanning.backend.dto;

public class RoomMessage {
  private Type type;
  private String payload;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public enum Type {
    ESTIMATE,
    SHOW,
    HIDE,
    CLEAR
  }
}
