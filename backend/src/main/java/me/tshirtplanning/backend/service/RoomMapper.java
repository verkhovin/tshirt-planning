package me.tshirtplanning.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static java.util.Collections.reverseOrder;

@Service
public class RoomMapper {
  public RoomDto toRoomDto(Room room) {
    final List<RoomDto.EstimateDto> estimates = room.getEstimates().stream()
            .map(estimate -> toEstimateDto(estimate, room.isShowEstimates()))
            .collect(Collectors.toList());
    final Map<String, Long> countPerEstimate = estimates.stream()
            .collect(Collectors.toMap(RoomDto.EstimateDto::getSize, (e) -> 1L, Long::sum));
    return new RoomDto(
            room.getId(),
            room.isShowEstimates() ? getDominatingEstimate(countPerEstimate): null,
        room.isShowEstimates()&& countPerEstimate.size() < 2,
            room.isShowEstimates(),
            estimates
    );
  }

  public RoomDto.EstimateDto toEstimateDto(Estimate estimate, boolean showEstimates) {
    String displayedEstimate = showEstimates
        ? estimate.getSize()
        : StringUtils.hasText(estimate.getSize()) ? "?" : "-";
    return new RoomDto.EstimateDto(estimate.getUsername(),  displayedEstimate);
  }

  private String getDominatingEstimate(Map<String, Long> countPerEstimateMap) {
    List<Map.Entry<String, Long>> estimates = countPerEstimateMap.entrySet().stream()
        .filter(entry -> entry.getKey() != null)
        .sorted(reverseOrder(Map.Entry.comparingByValue()))
        .collect(Collectors.toList());
    if (estimates.isEmpty()) {
      return null;
    }
    if (hasSingleDominatingEstimate(estimates)) {
      return estimates.get(0).getKey();
    }
    return null;
  }

  private boolean hasSingleDominatingEstimate(List<Map.Entry<String, Long>> estimates) {
    return estimates.size() == 1 || !estimates.get(0).getValue().equals(estimates.get(1).getValue());
  }
}
