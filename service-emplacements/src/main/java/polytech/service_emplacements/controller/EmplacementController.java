package polytech.service_emplacements.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polytech.service_emplacements.model.Emplacement;
import polytech.service_emplacements.service.EmplacementService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/emplacement")
public class EmplacementController {

    private final EmplacementService emplacementService;

    @Autowired
    public EmplacementController(EmplacementService emplacementService) {
        this.emplacementService = emplacementService;
    }

    // Récupérer tous les emplacements
    @GetMapping
    public ResponseEntity<List<Emplacement>> getAllEmplacements() {
        System.out.println("GET /emplacement recu");
        List<Emplacement> emplacements = emplacementService.getAllEmplacements();
        return new ResponseEntity<>(emplacements, HttpStatus.OK);
    }

    // Récupérer un emplacement par ID
    @GetMapping("/{id}")
    public ResponseEntity<Emplacement> getEmplacementById(@PathVariable Long id) {
        Optional<Emplacement> emplacement = emplacementService.getEmplacementById(id);
        return emplacement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Créer un nouvel emplacement
    @PostMapping
    public ResponseEntity<Emplacement> createEmplacement(@RequestBody Emplacement emplacement) {
        Emplacement newEmplacement = emplacementService.createEmplacement(emplacement);
        return new ResponseEntity<>(newEmplacement, HttpStatus.CREATED);
    }

    // Mettre à jour un emplacement existant
    @PutMapping("/{id}")
    public ResponseEntity<Emplacement> updateEmplacement(@PathVariable Long id, @RequestBody Emplacement emplacement) {
        Optional<Emplacement> updatedEmplacement = emplacementService.updateEmplacement(id, emplacement);
        return updatedEmplacement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Supprimer un emplacement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmplacement(@PathVariable Long id) {
        boolean isDeleted = emplacementService.deleteEmplacement(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
