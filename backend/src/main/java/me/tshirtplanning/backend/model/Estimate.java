package me.tshirtplanning.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Estimate {
  private String username;
  private String size;

  @JsonCreator
  public Estimate(@JsonProperty("username") String username, @JsonProperty("simze") String size) {
    this.username = username;
    this.size = size;
  }

  public void clear() {
    this.size = null;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }
}
