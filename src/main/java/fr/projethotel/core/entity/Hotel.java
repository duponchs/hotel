package fr.projethotel.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hotel {


    private Integer id;
    private String nom;
    private List<Chambre> chambres;

    public Hotel() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @OneToMany
    public List<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(List<Chambre> chambres) {
        this.chambres = chambres;
    }
}
