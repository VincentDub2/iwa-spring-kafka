package polytech.service_emplacements.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Emplacement")
public class Emplacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmplacement;

    @Column(nullable = false)
    private Long idHote;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "emplacement_commodites", joinColumns = @JoinColumn(name = "emplacement_id"))
    @Column(name = "commodite")
    private List<String> commodites;

    @Lob
    private byte[] image;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double prixParNuit;

    @Embedded
    private Dispo dispo;

    // Constructeur par défaut
    protected Emplacement() {}

    public Emplacement(Long idHote, String nom, String adresse, String description, List<String> commodites,
                       byte[] image, Double latitude, Double longitude, Double prixParNuit, Dispo dispo) {
        this.idHote = idHote;
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.commodites = commodites;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.prixParNuit = prixParNuit;
        this.dispo = dispo;
    }

    // Getters et Setters
    public Long getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(Long idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    public Long getIdHote() {
        return idHote;
    }

    public void setIdHote(Long idHote) {
        this.idHote = idHote;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCommodites() {
        return commodites;
    }

    public void setCommodites(List<String> commodites) {
        this.commodites = commodites;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrixParNuit() {
        return prixParNuit;
    }

    public void setPrixParNuit(Double prixParNuit) {
        this.prixParNuit = prixParNuit;
    }

    public Dispo getDispo() {
        return dispo;
    }

    public void setDispo(Dispo dispo) {
        this.dispo = dispo;
    }
}

@Embeddable
class Dispo {

    @Column(nullable = false)
    private String dateDebut;

    @Column(nullable = false)
    private String dateFin;

    public Dispo() {}

    public Dispo(String dateDebut, String dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
