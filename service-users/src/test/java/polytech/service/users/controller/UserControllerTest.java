package polytech.service.users.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import polytech.service.users.model.User;
import polytech.service.users.service.UserService;

import java.util.Arrays;
import java.util.Optional;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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

    // Test pour la méthode getUserById
    @Test
    public void testGetUserById() throws Exception {
        User user = new User("John", "John", "Doe", "john@example.com", "password");
        user.setId(1L);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Test pour la méthode updateUser
    @Test
    public void testUpdateUser() throws Exception {
        User existingUser = new User("John", "john", "password", "john@example.com\"", "Doe");
        existingUser.setId(1L);

        User updatedUser = new User("UpdatedJohn", "John", "newpassword", "updatedjohn@example.com", "Doe");

        when(userService.getUserById(1L)).thenReturn(Optional.of(existingUser));
        when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("UpdatedJohn"))
                .andExpect(jsonPath("$.email").value("updatedjohn@example.com"));
    }

    @Test
    public void testUpdateUser_NotFound() throws Exception {
        User user = new User( "John", "john@example.com", "password", "John", "Doe");

        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    // Test pour la méthode deleteUser
    @Test
    public void testDeleteUser() throws Exception {
        User user = new User( "John", "john@example.com", "password", "John", "Doe");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
