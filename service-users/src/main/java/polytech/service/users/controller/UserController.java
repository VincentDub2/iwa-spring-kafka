package polytech.service.users.controller;

import polytech.service.users.exception.UserNotFoundException;
import polytech.service.users.model.User;
import polytech.service.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.debug("Récupération de tous les utilisateurs");
        List<User> users = userService.getAllUsers();
        logger.debug("Nombre d'utilisateurs récupérés : {}", users.size());
        return users;

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.debug("Recherche de l'utilisateur avec l'ID : {}", id);
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
