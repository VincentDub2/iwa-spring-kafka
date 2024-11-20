package polytech.service_emplacements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polytech.service_emplacements.model.Emplacement;

import java.util.List;

@Repository
public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {

    // Trouver les emplacements par ID de l'hôte
    List<Emplacement> findByIdHote(Long idHote);
}
