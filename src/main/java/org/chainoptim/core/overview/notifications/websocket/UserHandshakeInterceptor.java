package org.chainoptim.core.overview.notifications.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String uri = request.getURI().toString();
        String userId = extractUserIdFromUri(uri);
        if (userId == null) {
            return false;
        }
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Do nothing
    }

    private String extractUserIdFromUri(String uri) {
        try {
            // Convert the URI string to a URI object to make parsing easier
            URI actualUri = new URI(uri);

            // Extract the query part of the URI
            String query = actualUri.getQuery();

            // Split the query into parameters
            String[] queryParams = query.split("&");

            // Iterate through the parameters to find the userId parameter
            for (String param : queryParams) {
                if (param.startsWith("userId=")) {
                    // Return the value part of the userId parameter
                    return param.split("=")[1];
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

}
