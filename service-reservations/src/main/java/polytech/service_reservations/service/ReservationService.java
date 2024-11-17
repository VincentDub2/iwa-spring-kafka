package polytech.service_reservations.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import polytech.service_reservations.model.Reservation;
import polytech.service_reservations.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    // Crée un nouveau service avec un repository de réservation
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Récupère toutes les réservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée !");
        } else {
            reservations.forEach(reservation -> System.out.println("Réservation : " + reservation));
        }
        return reservations;
    }

    // Récupère une réservation par son id
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    
    // Crée une nouvelle réservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Supprime une réservation par son id
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Ajoutez d'autres méthodes métier si nécessaire
}