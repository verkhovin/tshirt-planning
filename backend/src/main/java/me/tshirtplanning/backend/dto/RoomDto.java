package me.tshirtplanning.backend.dto;

import java.util.List;

public class RoomDto {
  private Long roomId;
  private String dominatingEstimate;
  private Boolean hasConsensus;
  private Boolean estimatesOpened;
  private List<EstimateDto> estimates;

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public String getDominatingEstimate() {
    return dominatingEstimate;
  }

  public void setDominatingEstimate(String dominatingEstimate) {
    this.dominatingEstimate = dominatingEstimate;
  }

  public Boolean getHasConsensus() {
    return hasConsensus;
  }

  public void setHasConsensus(Boolean hasConsensus) {
    this.hasConsensus = hasConsensus;
  }

  public List<EstimateDto> getEstimates() {
    return estimates;
  }

  public void setEstimates(List<EstimateDto> estimates) {
    this.estimates = estimates;
  }

  public Boolean getEstimatesOpened() {
    return estimatesOpened;
  }

  public void setEstimatesOpened(Boolean estimatesOpened) {
    this.estimatesOpened = estimatesOpened;
  }

  public static class EstimateDto {
    private String username;
    private String size;

    public String getUsername() {
      return username;
    }

    public String getSize() {
      return size;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public void setSize(String size) {
      this.size = size;
    }
  }
}
