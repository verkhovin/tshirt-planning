package me.tshirtplanning.backend.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomMapper {
  public RoomDto toRoomDto(Room room) {
    RoomDto roomDto = new RoomDto();
    roomDto.setRoomId(room.getRoomId());
    roomDto.setEstimatesOpened(room.isShowEstimates());
    HashMap<String, Long> sizeMaps = new HashMap<>();
    roomDto.setEstimates(
        room.getEstimates().stream()
            .map(estimate -> toEstimateDto(estimate, room.isShowEstimates()))
            .peek(estimateDto -> sizeMaps.merge(estimateDto.getSize(), 1L, Long::sum))
            .collect(Collectors.toList())
    );
    roomDto.setHasConsensus(sizeMaps.size() < 2);
    roomDto.setFinalEstimate(
        sizeMaps.entrySet().stream()
            .max(Comparator.comparingLong(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElse(null)
    );
    return roomDto;
  }

  public RoomDto.EstimateDto toEstimateDto(Estimate estimate, boolean showEstimates) {
    RoomDto.EstimateDto estimateDto = new RoomDto.EstimateDto();
    estimateDto.setUsername(estimate.getUsername());
    estimateDto.setSize(showEstimates ? estimate.getSize() : "?");
    return estimateDto;
  }
}
