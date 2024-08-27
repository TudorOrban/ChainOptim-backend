package org.chainoptim.config;

import org.chainoptim.core.overview.notifications.websocket.SimpleTextWebSocketHandler;
import org.chainoptim.core.overview.notifications.websocket.UserHandshakeInterceptor;
import org.chainoptim.core.overview.notifications.websocket.WebSocketMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketMessagingService messagingService;

    @Autowired
    public WebSocketConfig(WebSocketMessagingService messagingService) {
        this.messagingService = messagingService;
    }

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
