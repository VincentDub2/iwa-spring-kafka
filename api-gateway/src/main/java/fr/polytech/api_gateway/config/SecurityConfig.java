package fr.polytech.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * AuthenticationManager Bean definition
     *
     * @return AuthenticationManager object
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {

        LOGGER.info("Configuring security");
        http
                .cors(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)// Disable CSRF protection
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll() // Endpoints publics
                );
        return http.build();
    }

}

