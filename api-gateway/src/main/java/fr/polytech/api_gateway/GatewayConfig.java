package fr.polytech.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /**
     * Configure the routes of the gateway
     *
     * @param builder RouteLocatorBuilder object to build the routes
     * @return RouteLocator object containing the routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-route", r -> r
                        .path("/api/v1/user/**") // Path of the request to match
                        .filters(f -> f.stripPrefix(3)) // Remove the first part of the path
                        .uri("lb://SERVICE-USERS") // Destination URI of the service
                )
                .build();
    }

}


