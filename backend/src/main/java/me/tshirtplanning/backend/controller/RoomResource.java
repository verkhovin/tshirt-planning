package me.tshirtplanning.backend.controller;

import java.net.URI;
import me.tshirtplanning.backend.dto.RoomDto;
import me.tshirtplanning.backend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomResource {
  private final RoomService roomService;

  public RoomResource(RoomService roomService) {
    this.roomService = roomService;
  }

  @PostMapping
  public ResponseEntity<Void> createRoom() {
    Long roomId = roomService.createRoom();
    return ResponseEntity.created(URI.create("/api/rooms/" + roomId)).build();
  }

  @GetMapping("/{id}")
  public RoomDto getRoom(@PathVariable("id") Long id){
    return roomService.getRoom(id);
  }
}
