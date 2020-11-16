package me.tshirtplanning.backend.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import me.tshirtplanning.backend.model.Estimate;
import me.tshirtplanning.backend.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class EstimateConverter implements AttributeConverter<List<Estimate>, String> {
  private static final Logger LOG = LoggerFactory.getLogger(Room.class.getName());
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<Estimate> estimates) {
    try {
      return OBJECT_MAPPER.writeValueAsString(estimates);
    } catch (JsonProcessingException e) {
      String message = "Failed to write estimates to room";
      LOG.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  @Override
  public List<Estimate> convertToEntityAttribute(String estimates) {
    try {
      return OBJECT_MAPPER.readValue(estimates, new TypeReference<List<Estimate>>() {});
    } catch (JsonProcessingException e) {
      String message = "Failed to read estimates from room";
      LOG.error(message,e);
      throw new RuntimeException(message);
    }
  }
}
