package me.tshirtplanning.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Estimate {
  private final String username;
  private final String size;

  @JsonCreator
  public Estimate(@JsonProperty("username") String username, @JsonProperty("size") String size) {
    this.username = username;
    this.size = size;
  }

  public Estimate(String username) {
    this.username = username;
    this.size = null;
  }

  public Estimate clear() {
    return withSize(null);
  }

  public String getUsername() {
    return username;
  }

  public String getSize() {
    return size;
  }

  public Estimate withSize(String size) {
    return new Estimate(this.username, size);
  }
}
