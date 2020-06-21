package me.tshirtplanning.backend.config;

import me.tshirtplanning.backend.controller.socket.SocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final SocketHandler socketHandler;

  public WebSocketConfig(SocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(socketHandler, "/ws/planning");
  }
}
