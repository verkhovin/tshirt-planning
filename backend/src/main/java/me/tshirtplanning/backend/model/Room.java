package me.tshirtplanning.backend.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Room {
  @Id
  private String id;
  private Long roomId;
  private List<Estimate> estimates = new ArrayList<>();
  private boolean showEstimates;

  public Room() {
  }

  public Room(Long roomId) {
    this.roomId = roomId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Estimate> getEstimates() {
    return estimates;
  }

  public boolean isShowEstimates() {
    return showEstimates;
  }

  public void setShowEstimates(boolean showEstimates) {
    this.showEstimates = showEstimates;
  }

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public void setEstimates(List<Estimate> estimates) {
    this.estimates = estimates;
  }

}
