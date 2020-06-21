package me.tshirtplanning.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "room_seq")
public class RoomSeq {
  public final static String SEQ_RECORD_ID = "SEQ";

  @Id
  private String id;
  private Long value;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public static RoomSeq init() {
    RoomSeq roomSeq = new RoomSeq();
    roomSeq.setId(SEQ_RECORD_ID);
    roomSeq.setValue(1L);
    return roomSeq;
  }
}
