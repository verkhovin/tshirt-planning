package me.tshirtplanning.backend.dto;

import java.util.List;

public class RoomDto {
  private final Long roomId;
  private final String dominatingEstimate;
  private final Boolean hasConsensus;
  private final Boolean estimatesOpened;
  private final List<EstimateDto> estimates;

  public RoomDto(Long roomId, String dominatingEstimate, Boolean hasConsensus, Boolean estimatesOpened, List<EstimateDto> estimates) {
    this.roomId = roomId;
    this.dominatingEstimate = dominatingEstimate;
    this.hasConsensus = hasConsensus;
    this.estimatesOpened = estimatesOpened;
    this.estimates = estimates;
  }

  public Long getRoomId() {
    return roomId;
  }

  public String getDominatingEstimate() {
    return dominatingEstimate;
  }

  public Boolean getHasConsensus() {
    return hasConsensus;
  }

  public List<EstimateDto> getEstimates() {
    return estimates;
  }

  public Boolean getEstimatesOpened() {
    return estimatesOpened;
  }

  public static class EstimateDto {
    private final String username;
    private final String size;

    public EstimateDto(String username, String size) {
      this.username = username;
      this.size = size;
    }

    public String getUsername() {
      return username;
    }

    public String getSize() {
      return size;
    }
  }
}
