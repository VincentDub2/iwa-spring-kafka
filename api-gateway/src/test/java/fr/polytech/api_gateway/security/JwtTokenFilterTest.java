package fr.polytech.api_gateway.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;
import java.net.URI;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenFilterTest {

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private GatewayFilterChain chain;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private ServerHttpResponse response;

    @Mock
    private ServerWebExchange.Builder exchangeBuilder;

    @Mock
    private HttpHeaders mockHeaders;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(exchange.getRequest()).thenReturn(request);
        when(exchange.getResponse()).thenReturn(response);
        when(exchange.mutate()).thenReturn(exchangeBuilder); // Simule mutate()
        when(exchangeBuilder.request(any(ServerHttpRequest.class))).thenReturn(exchangeBuilder); // Simule la méthode request()
        when(exchangeBuilder.build()).thenReturn(exchange); // Retourne l'objet exchange final
    }

    @Test
    void testFilter_IgnorePaths() {
        when(request.getURI()).thenReturn(URI.create("/auth/login"));

        when(chain.filter(exchange)).thenReturn(Mono.empty());

        Mono<Void> result = jwtTokenFilter.filter(exchange, chain);

        verify(chain, times(1)).filter(exchange);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    void testFilter_MissingAuthorizationHeader() {
        when(request.getURI()).thenReturn(URI.create("/api/secure/resource"));
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // Stub de setStatusCode() avec le bon type de retour
        doReturn(true).when(response).setStatusCode(HttpStatus.UNAUTHORIZED);

        // Utilisez un mock pour HttpHeaders
        when(response.getHeaders()).thenReturn(mockHeaders);

        Mono<Void> result = jwtTokenFilter.filter(exchange, chain);

        // Vérifications
        verify(response, times(1)).setStatusCode(HttpStatus.UNAUTHORIZED);
        verify(mockHeaders, times(1)).add("WWW-Authenticate", "Bearer");
        //assertDoesNotThrow(() -> result.block());
    }

    @Test
    void testFilter_InvalidToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer invalidToken");
        when(request.getURI()).thenReturn(URI.create("/api/secure/resource"));
        when(request.getHeaders()).thenReturn(headers);
        when(jwtTokenUtil.validate("invalidToken")).thenReturn(false);

        // Stub setStatusCode() correctement
        doReturn(true).when(response).setStatusCode(HttpStatus.UNAUTHORIZED);

        Mono<Void> result = jwtTokenFilter.filter(exchange, chain);

        verify(response, times(1)).setStatusCode(HttpStatus.UNAUTHORIZED);
        //assertDoesNotThrow(() -> result.block());
    }

    @Test
    void testFilter_ValidToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer validToken");
        when(request.getURI()).thenReturn(URI.create("/api/secure/resource"));
        when(request.getHeaders()).thenReturn(headers);
        when(jwtTokenUtil.validate("validToken")).thenReturn(true);
        when(jwtTokenUtil.getUserId("validToken")).thenReturn(123L);

        when(chain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        Mono<Void> result = jwtTokenFilter.filter(exchange, chain);

        verify(chain, times(1)).filter(any(ServerWebExchange.class));
        assertDoesNotThrow(() -> result.block());
    }
}
