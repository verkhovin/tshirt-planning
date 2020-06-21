package me.tshirtplanning.backend.service;

import java.util.List;
import java.util.function.Consumer;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.dto.RoomParticipation;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import me.tshirtplanning.backend.model.RoomSeq;
import static me.tshirtplanning.backend.model.RoomSeq.SEQ_RECORD_ID;
import me.tshirtplanning.backend.repository.RoomRepository;
import me.tshirtplanning.backend.repository.RoomSeqRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
  private final RoomRepository roomRepository;
  private final RoomSeqRepository roomSeqRepository;
  private final RoomMapper roomMapper;

  public RoomService(RoomRepository roomRepository, RoomSeqRepository roomSeqRepository, RoomMapper roomMapper) {
    this.roomRepository = roomRepository;
    this.roomSeqRepository = roomSeqRepository;
    this.roomMapper = roomMapper;
  }

  public Long createRoom() {
    Room newRoom = roomRepository.save(new Room(getNextRoomId()));
    return newRoom.getRoomId();
  }

  public RoomDto getRoom(Long id) {
    Room room = getExistingRoom(id);
    return roomMapper.toRoomDto(room);
  }

  @Transactional
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

  @Transactional
  public void commitEstimate(Long roomId, String userName, String newEstimate) {
    onExistingRoom(roomId, room ->
        room.getEstimates().stream()
            .filter(estimate -> estimate.getUsername().equals(userName))
            .findFirst()
            .ifPresent(estimate -> estimate.setSize(newEstimate))
    );
  }

  @Transactional
  public void showEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.setShowEstimates(true));
  }

  @Transactional
  public void hideEstimates(Long roomId) {
    onExistingRoom(roomId, room -> room.setShowEstimates(false));
  }

  @Transactional
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
    return roomRepository.findByRoomId(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity not found"));
  }

  @Transactional
  public Long getNextRoomId() {
    RoomSeq seq = roomSeqRepository.findById(SEQ_RECORD_ID)
        .orElseGet(() -> roomSeqRepository.save(RoomSeq.init()));
    Long value = seq.getValue();
    seq.setValue(value + 1);
    roomSeqRepository.save(seq);
    return value;
  }
}
