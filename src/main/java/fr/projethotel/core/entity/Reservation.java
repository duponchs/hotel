package fr.projethotel.core.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {
    private int id;
    private int nbPersonne;
    private LocalDate dateNuitee;
    private float montant;
    private int numeroCb;
    private char etat;
    private Client client;

    public Reservation() {
    }

    public Reservation(int nbPersonne, LocalDate dateNuitee, float montant, int numeroCb, char etat, Client client ) {
        this.nbPersonne = nbPersonne;
        this.dateNuitee = dateNuitee;
        this.montant = montant;
        this.numeroCb = numeroCb;
        this.etat = etat;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public int getNbPersonne() {
        return nbPersonne;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    @Column
    public LocalDate getDateNuitee() {
        return dateNuitee;
    }

    public void setDateNuitee(LocalDate dateNuitee) {
        this.dateNuitee = dateNuitee;
    }

    @Column
    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {this.montant = montant;}

    @Column
    public int getNumeroCb() {
        return numeroCb;
    }

    public void setNumeroCB(int numeroCb) {
        this.numeroCb = numeroCb;
    }

    @Column
    public char  getEtat(){ return etat;}

    public void setEtat(char etat) { this.etat = etat;}

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
