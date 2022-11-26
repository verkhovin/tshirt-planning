package me.tshirtplanning.backend.dto;

import java.util.Objects;

public class RoomParticipation {
  private final Long roomId;
  private final String username;

  public RoomParticipation(Long roomId, String username) {
    this.roomId = roomId;
    this.username = username;
  }

  public Long getRoomId() {
    return roomId;
  }

  public String getUsername() {
    return username;
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
