package fr.projethotel.core.entity;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@NamedQueries({
        @NamedQuery(name = "client.findByNomPrenomEmail",
                query = "from Client c where c.nom=:nom and c.prenom=:prenom and c.dateNaissance=:dateNaissance and c.email=:email"),
        @NamedQuery(name = "client.listfindByNomPrenom",
                query = "from Client c where c.nom=:nom and c.prenom=:prenom")
})
public class Client {
    private Integer id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private Boolean archiver = false;

    public Client() {
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    @Column(name = "DATE_NAISSANCE")
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }
}
