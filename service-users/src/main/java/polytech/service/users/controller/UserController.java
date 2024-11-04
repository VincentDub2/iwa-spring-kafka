package polytech.service.users.controller;

import polytech.service.users.exception.UserNotFoundException;
import polytech.service.users.model.User;
import polytech.service.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur avec l'ID " + id + " non trouvé."));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.getUserById(id)
                .map(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    return userService.updateUser(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("Utilisateur avec l'ID " + id + " non trouvé."));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
        } else {
            throw new UserNotFoundException("Utilisateur avec l'ID " + id + " non trouvé.");
        }
    }
}
