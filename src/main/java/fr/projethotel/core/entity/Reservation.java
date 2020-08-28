package fr.projethotel.core.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQueries({
       @NamedQuery(name = "reservation.findByDate",
            query = "from Reservation r where r.dateNuitee=:dateNuitee"),
        @NamedQuery(name = "reservation.findByClientDate",
            query = "from Reservation r where r.dateNuitee=:dateNuitee and r.client =:client")

})
public class Reservation {
    private Integer id;
    private Integer nbPersonne;
    private LocalDate dateNuitee;
    private Float montant;
    private Integer numeroCb;
    private String etat; //domaine de valeur : C = crée, P = payée
    private Client client;
    private List<Chambre> chambres;
    private Integer idHotel;

    public Reservation() {
    }

    public Reservation(Integer nbPersonne, LocalDate dateNuitee, Float montant, Integer numeroCb, String etat, Client client ) {
        this.nbPersonne = nbPersonne;
        this.dateNuitee = dateNuitee;
        this.montant = montant;
        this.numeroCb = numeroCb;
        this.etat = etat;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NOMBRE_PERSONNES")
    public Integer getNbPersonne() {
        return nbPersonne;
    }

    public void setNbPersonne(Integer nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    @Column(name = "DATE_NUITEE")
    public LocalDate getDateNuitee() {
        return dateNuitee;
    }

    public void setDateNuitee(LocalDate dateNuitee) {
        this.dateNuitee = dateNuitee;
    }

    @Column(name = "MONTANT_NUITEE")
    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {this.montant = montant;}

    @Column(name = "NUMERO_CB")
    public Integer getNumeroCb() {
        return numeroCb;
    }

    public void setNumeroCb(Integer numeroCb) {
        this.numeroCb = numeroCb;
    }

    @Column(name = "ETAT")
    public String  getEtat(){ return etat;}

    public void setEtat(String etat) { this.etat = etat;}

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(List<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Integer getIdHotel() {
        return idHotel;
    }
    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }
}
