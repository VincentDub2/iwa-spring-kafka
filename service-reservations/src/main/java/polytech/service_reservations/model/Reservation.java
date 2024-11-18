package polytech.service_reservations.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreservation")
    private Long idReservation;

    @Column(name = "idemplacement", nullable = false)
    private Long idEmplacement;

    @Column(name = "idvoyageur", nullable = false)
    private Long idVoyageur;

    @Column(name = "datearrive", nullable = false)
    private LocalDateTime dateArrive;

    @Column(name = "datedepart", nullable = false)
    private LocalDateTime dateDepart;

    @Column(name = "prix", nullable = false)
    private Double prix;

    public Reservation() {}

    public Reservation(Long idEmplacement, Long idVoyageur, LocalDateTime dateArrive, LocalDateTime dateDepart, Double prix) {
        this.idEmplacement = idEmplacement;
        this.idVoyageur = idVoyageur;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.prix = prix;
    }

    // Getters et setters
    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public Long getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(Long idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    public Long getIdVoyageur() {
        return idVoyageur;
    }

    public void setIdVoyageur(Long idVoyageur) {
        this.idVoyageur = idVoyageur;
    }

    public LocalDateTime getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(LocalDateTime dateArrive) {
        this.dateArrive = dateArrive;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}