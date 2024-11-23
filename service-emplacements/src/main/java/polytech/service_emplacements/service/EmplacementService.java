package polytech.service_emplacements.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polytech.service_emplacements.model.Emplacement;
import polytech.service_emplacements.repository.EmplacementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmplacementService {

    private final EmplacementRepository emplacementRepository;

    @Autowired
    public EmplacementService(EmplacementRepository emplacementRepository) {
        this.emplacementRepository = emplacementRepository;
    }

    // Récupérer tous les emplacements
    public List<Emplacement> getAllEmplacements() {
        return emplacementRepository.findAll();
    }

    // Récupérer un emplacement par ID
    public Optional<Emplacement> getEmplacementById(Long id) {
        return emplacementRepository.findById(id);
    }

    // Créer un nouvel emplacement
    public Emplacement createEmplacement(Emplacement emplacement) {
        return emplacementRepository.save(emplacement);
    }

    // Mettre à jour un emplacement existant
    public Optional<Emplacement> updateEmplacement(Long id, Emplacement updatedEmplacement) {
        return emplacementRepository.findById(id).map(emplacement -> {
            emplacement.setNom(updatedEmplacement.getNom());
            emplacement.setAdresse(updatedEmplacement.getAdresse());
            emplacement.setDescription(updatedEmplacement.getDescription());
            emplacement.setCommodites(updatedEmplacement.getCommodites());
            emplacement.setImage(updatedEmplacement.getImage());
            emplacement.setLatitude(updatedEmplacement.getLatitude());
            emplacement.setLongitude(updatedEmplacement.getLongitude());
            emplacement.setPrixParNuit(updatedEmplacement.getPrixParNuit());
            emplacement.setDateDebut(updatedEmplacement.getDateDebut());
            emplacement.setDateFin(updatedEmplacement.getDateFin());
            return emplacementRepository.save(emplacement);
        });
    }

    // Supprimer un emplacement
    public boolean deleteEmplacement(Long id) {
        if (emplacementRepository.existsById(id)) {
            emplacementRepository.deleteById(id);
            return true; // Retourne true si l'emplacement a été supprimé
        }
        return false; // Retourne false si l'emplacement n'existe pas
    }


    // Récupérer les emplacements par un hôte spécifique
    public List<Emplacement> getEmplacementsByHoteId(Long idHote) {
        return emplacementRepository.findByIdHote(idHote);
    }
}
