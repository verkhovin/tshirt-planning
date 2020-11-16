package me.tshirtplanning.backend.repository;

import me.tshirtplanning.backend.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
