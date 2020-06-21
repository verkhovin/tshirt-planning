package me.tshirtplanning.backend.repository;

import me.tshirtplanning.backend.model.RoomSeq;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomSeqRepository extends MongoRepository<RoomSeq, String> {
}
