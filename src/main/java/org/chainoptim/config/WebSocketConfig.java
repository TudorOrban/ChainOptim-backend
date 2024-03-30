package org.chainoptim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketMessagingService messagingService; // Autowire the service here

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(simpleTextWebSocketHandler(messagingService), "/ws")
                .setAllowedOrigins("*")
                .addInterceptors(new UserHandshakeInterceptor());
    }

    @Bean
    public SimpleTextWebSocketHandler simpleTextWebSocketHandler(WebSocketMessagingService messagingService) {
        return new SimpleTextWebSocketHandler(messagingService);
    }
}
