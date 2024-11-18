package fr.polytech.api_gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class GatewayConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayConfig.class);

    /**
     * Configure the routes of the gateway
     *
     * @param builder RouteLocatorBuilder object to build the routes
     * @return RouteLocator object containing the routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        LOGGER.info("Configuring routes");

        return builder.routes()
                .route("user-route", r -> r
                        .path("/api/v1/user/**") // Path of the request to match
                        .filters(f -> f.stripPrefix(3)) // Remove the first part of the path
                        .uri("lb://SERVICE-USERS") // Destination URI of the service
                )
                .route("message-route", r -> r
                        .path("/api/v1/messages/**") // Path of the request to match
                        .filters(f -> f.stripPrefix(3)) // Remove the first part of the path
                        .uri("lb://SERVICE-MESSAGING") // Destination URI of the service
                ).route("message-route", r -> r
                        .path("/api/v1/reservation/**") // Path of the request to match
                        .filters(f -> f.stripPrefix(3)) // Remove the first part of the path
                        .uri("lb://SERVICE-RESERVATIONS") // Destination URI of the service
                ).build();
    }

}


