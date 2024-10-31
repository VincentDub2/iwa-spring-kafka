package polytech.service.users.repository;

import polytech.service.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Vous pouvez définir des méthodes de recherche personnalisées ici

    Optional<User> findByEmail(String email);
}
