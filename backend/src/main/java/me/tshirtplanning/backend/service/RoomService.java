package me.tshirtplanning.backend.service;

import java.util.List;
import java.util.function.Consumer;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.dto.RoomParticipation;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import me.tshirtplanning.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {
  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
    this.roomRepository = roomRepository;
    this.roomMapper = roomMapper;
  }

  public Long createRoom() {
    Room newRoom = roomRepository.save(new Room());
    return newRoom.getId();
  }

  @Transactional(readOnly = true)
  public RoomDto getRoom(Long id) {
    Room room = getExistingRoom(id);
    return roomMapper.toRoomDto(room);
  }

  public void addUser(RoomParticipation participation) {
    onExistingRoom(participation.getRoomId(), room -> {
      List<Estimate> estimates = room.getEstimates();
      boolean foundSameUsername = estimates.stream()
          .anyMatch(estimate -> estimate.getUsername().equals(participation.getUsername()));
      if (!foundSameUsername) {
        estimates.add(new Estimate(participation.getUsername(), null));
      }
    });
  }

  public void commitEstimate(Long roomId, String userName, String newEstimate) {
    onExistingRoom(roomId, room ->
        room.getEstimates().stream()
            .filter(estimate -> estimate.getUsername().equals(userName))
            .findFirst()
            .ifPresent(estimate -> estimate.setSize(newEstimate))
    );
  }

  public void showEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.setShowEstimates(true));
  }

  public void hideEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.setShowEstimates(false));
  }

  public void clearEstimates(Long roomId) {
    onExistingRoom(roomId, room -> {
      room.getEstimates().forEach(Estimate::clear);
      room.setShowEstimates(false);
    });
  }

  private void onExistingRoom(Long roomId, Consumer<Room> action) {
    Room existingRoom = getExistingRoom(roomId);
    action.accept(existingRoom);
    roomRepository.save(existingRoom);
  }

  private Room getExistingRoom(Long id) {
    return roomRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity not found"));
  }
}
