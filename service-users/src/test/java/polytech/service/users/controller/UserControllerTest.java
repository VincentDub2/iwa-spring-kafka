package polytech.service.users.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import polytech.service.users.model.User;
import polytech.service.users.service.UserService;

import java.util.Arrays;
import java.util.Optional;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class) // Charge uniquement le contrôleur et ses dépendances
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permet de simuler des requêtes HTTP

    @MockBean
    private UserService userService; // Mock du service pour éviter d'utiliser la vraie couche service

    @Autowired
    private ObjectMapper objectMapper; // Permet de convertir des objets en JSON

    @Test
    public void testGetAllUsers() throws Exception {
        // Préparer les données mockées
        User user1 = new User( "John", "john@example.com", "password", "John", "Doe");
        User user2 = new User( "Jane", "jane@example.com", "password", "Jane", "Smith");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Simuler une requête GET et vérifier le résultat
        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("John"))
                .andExpect(jsonPath("$[1].username").value("Jane"));
    }
}
