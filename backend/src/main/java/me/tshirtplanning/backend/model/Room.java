package me.tshirtplanning.backend.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import me.tshirtplanning.backend.repository.EstimateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private boolean showEstimates;
  //TODO store as jsonb or elementcollections. Converter now is called like 4 times per request which is too much
  @Convert(converter = EstimateConverter.class)
  private List<Estimate> estimates;

 public Room() {
    this.showEstimates = false;
    this.estimates = new ArrayList<>();
  }

  private Room(Long id, boolean showEstimates, List<Estimate> estimates) {
    this.id = id;
    this.showEstimates = showEstimates;
    this.estimates = estimates;
  }

  public Long getId() {
    return id;
  }

  public boolean isShowEstimates() {
    return showEstimates;
  }

  public Room withShowEstimates(boolean showEstimates) {
    return new Room(
        this.id,
        showEstimates,
        this.estimates
    );
  }

  public List<Estimate> getEstimates() {
    return estimates;
  }

  public Room withEstimates(List<Estimate> estimates) {
    return new Room(
        this.id,
        this.showEstimates,
        estimates
    );
  }
}
