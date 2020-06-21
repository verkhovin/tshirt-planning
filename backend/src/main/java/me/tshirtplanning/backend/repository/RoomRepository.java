package me.tshirtplanning.backend.repository;

import java.util.Optional;
import me.tshirtplanning.backend.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
  Optional<Room> findByRoomId(Long roomId);
}
