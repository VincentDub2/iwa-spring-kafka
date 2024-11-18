package fr.polytech.api_gateway.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(1)
public class JwtTokenFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);



    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Filter the request to validate the JWT token
     *
     * @param exchange ServerWebExchange object
     * @param chain   GatewayFilterChain object
     * @return Mono<Void> objet
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        logger.info("Incoming request path: {}", path);

        // Ignorer les requêtes vers /auth/* et /api/v1/user/*
        if (path.startsWith("/auth/") || path.startsWith("/api/v1/user/auth/")) {
            logger.debug("Skipping authentication for path: {}", path);
            return chain.filter(exchange);
        }

        // Récupère le jeton d'autorisation
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or malformed Authorization header : {}", authHeader);
            // Si le jeton est manquant ou mal formé, renvoyer une erreur 401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            //Add a header to the response for say we need a token
            exchange.getResponse().getHeaders().add("WWW-Authenticate", "Bearer");


            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        // Valider le jeton
        if (!jwtTokenUtil.validate(token)) {
            logger.warn("Invalid token : {}", token);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extraire l'ID utilisateur du jeton
        Long userId = jwtTokenUtil.getUserId(token);
        String role = jwtTokenUtil.getRole(token);

        // Vérifier les permissions pour des routes spécifiques
        if (path.startsWith("/admin/") && !"ROLE_ADMIN".equals(role)) {
            logger.warn("Access denied for user with role: {}", role);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // Créer une nouvelle requête avec les en-têtes modifiés
        ServerHttpRequest modifiedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(super.getHeaders());
                headers.add("X-User-Id", String.valueOf(userId));
                return headers;
            }
        };

        logger.info("request modified with user id: {}", userId);

        // Continuer le filtre avec la requête modifiée
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

}
