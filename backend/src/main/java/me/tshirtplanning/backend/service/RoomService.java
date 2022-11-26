package me.tshirtplanning.backend.service;

import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.dto.RoomParticipation;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import me.tshirtplanning.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
      boolean userExists = estimates.stream()
          .anyMatch(estimate -> estimate.getUsername().equals(participation.getUsername()));
      if (userExists) {
        return room;
      } else {
        return room.withEstimates(
            Stream.concat(
                room.getEstimates().stream(),
                Stream.of(new Estimate(participation.getUsername()))
            ).collect(Collectors.toList())
        );
      }
    });
  }

  public void commitEstimate(Long roomId, String userName, String newEstimate) {
    onExistingRoom(roomId, room ->
        room.withEstimates(
            room.getEstimates().stream()
                .map(estimate -> {
                  if (estimate.getUsername().equals(userName)) {
                    return estimate.withSize(newEstimate);
                  } else {
                    return estimate;
                  }
                }).collect(Collectors.toList())
        )
    );
  }

  public void showEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.withShowEstimates(true));
  }

  public void hideEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.withShowEstimates(false));
  }

  public void clearEstimates(Long roomId) {
    onExistingRoom(roomId, room ->
        room.withEstimates(
            room.getEstimates().stream()
                .map(Estimate::clear)
                .collect(Collectors.toList())
        ).withShowEstimates(false)
    );
  }

  private void onExistingRoom(Long roomId, UnaryOperator<Room> action) {
    Room existingRoom = getExistingRoom(roomId);
    roomRepository.save(action.apply(existingRoom));
  }

  private Room getExistingRoom(Long id) {
    return roomRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity not found"));
  }
}
