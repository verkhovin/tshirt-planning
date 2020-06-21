package me.tshirtplanning.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomMapper {
  public RoomDto toRoomDto(Room room) {
    RoomDto roomDto = new RoomDto();
    roomDto.setRoomId(room.getRoomId());
    roomDto.setEstimatesOpened(room.isShowEstimates());
    Map<String, Long> sizeMaps = new HashMap<>();
    roomDto.setEstimates(
        room.getEstimates().stream()
            .map(estimate -> toEstimateDto(estimate, room.isShowEstimates()))
            .peek(estimateDto -> sizeMaps.merge(estimateDto.getSize(), 1L, Long::sum))
            .collect(Collectors.toList())
    );
    if(room.isShowEstimates()) {
      roomDto.setHasConsensus(sizeMaps.size() < 2);
      roomDto.setDominatingEstimate(getDominatingEstimate(sizeMaps));
    }
    return roomDto;
  }

  public RoomDto.EstimateDto toEstimateDto(Estimate estimate, boolean showEstimates) {
    RoomDto.EstimateDto estimateDto = new RoomDto.EstimateDto();
    estimateDto.setUsername(estimate.getUsername());
    String displayedEstimate = estimate.getSize();
    if(!showEstimates) {
      displayedEstimate = StringUtils.isEmpty(displayedEstimate) ? "-" : "?";
    }
    estimateDto.setSize(displayedEstimate);
    return estimateDto;
  }

  private String getDominatingEstimate(Map<String, Long> estimatesMap) {
    List<Map.Entry<String, Long>> estimates = estimatesMap.entrySet().stream()
        .filter(entry -> entry.getKey() != null)
        .sorted((v1, v2) -> Long.compare(v2.getValue(), v1.getValue()))
        .collect(Collectors.toList());
    if (estimates.isEmpty()) {
      return null;
    }
    if (estimates.size() == 1 || !estimates.get(0).getValue().equals(estimates.get(1).getValue())) {
      return estimates.get(0).getKey();
    }
    return null;
  }
}
