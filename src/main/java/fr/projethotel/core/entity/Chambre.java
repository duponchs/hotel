package fr.projethotel.core.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = "chambre.findByArchiver",
                query = "from Chambre c where c.archiver=true "),
        @NamedQuery(name = "chambre.findByNotArchiver",
                query = "from Chambre c where c.archiver=false"),
        @NamedQuery(name = "chambre.findCapaciteMax",
                query = " SELECT count (*) from Chambre c where  c.archiver=false and c.hotel.id =:idHotel"),

})

public class Chambre {

    private Integer id;
    private Boolean archiver;
    private Integer nbPersonneMax;
    private Float prix;
    private Integer numero;
    private Hotel  hotel;
    private List<Reservation> reservations;

    public Chambre() {
        this.archiver = false;
        this.nbPersonneMax = 3;
        this.prix = 50f;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    @Column(name = "NB_PERSONNE_MAX")
    public Integer getNbPersonneMax() {
        return nbPersonneMax;
    }

    public void setNbPersonneMax(Integer nbPersonneMax) {
        this.nbPersonneMax = nbPersonneMax;
    }

    @Column(unique = true)
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @ManyToOne
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
