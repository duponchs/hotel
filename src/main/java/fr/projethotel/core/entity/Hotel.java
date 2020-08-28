package fr.projethotel.core.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "hotel.findByNom",
                query = "from Hotel h where h.nom=:nom ")
})
public class Hotel {

    private Integer id;
    private String nom;
    private Set<Chambre> chambres;

    public Hotel() {
        this.chambres = new HashSet<Chambre>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    public Set<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
    }
}
