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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isShowEstimates() {
    return showEstimates;
  }

  public void setShowEstimates(boolean showEstimates) {
    this.showEstimates = showEstimates;
  }

  public List<Estimate> getEstimates() {
    return estimates;
  }

  public void setEstimates(List<Estimate> estimates) {
    this.estimates = estimates;
  }
}
