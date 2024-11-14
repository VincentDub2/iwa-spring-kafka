package polytech.service.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import polytech.service.users.exception.InvalidCredentialsException;
import polytech.service.users.exception.UserAlreadyExistsException;
import polytech.service.users.model.User;
import polytech.service.users.security.JwtTokenUtil;
import polytech.service.users.service.UserService;
import java.util.Optional;
import org.springframework.kafka.core.KafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Inject KafkaTemplate

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        logger.debug("Enregistrement d'un nouvel utilisateur");
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("L'email est déjà utilisé.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Enregistrer l'utilisateur
        User newUser = userService.createUser(user);

        // Envoyer un message Kafka après l'enregistrement
        String message = "Nouvel utilisateur enregistré avec l'ID : " + newUser.getId();
        kafkaTemplate.send("user-registration-topic", newUser.getId().toString(), message);

        return newUser;
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        logger.debug("Authentification de l'utilisateur");
        Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return jwtTokenUtil.generateAccessToken(user.get().getId());
        } else {
            throw new InvalidCredentialsException("Email ou mot de passe incorrect");
        }
    }
}
