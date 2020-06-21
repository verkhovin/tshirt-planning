package me.tshirtplanning.backend.dto;

import java.util.Objects;

public class RoomParticipation {
  private Long roomId;
  private String username;

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoomParticipation that = (RoomParticipation) o;
    return Objects.equals(roomId, that.roomId) &&
        Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, username);
  }
}
