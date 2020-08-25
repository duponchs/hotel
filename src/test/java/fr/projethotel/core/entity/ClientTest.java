package fr.projethotel.core.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClientTest {
    Client client;

    @BeforeEach
    void init() {
        client = new Client();
    }

    @Test
    @DisplayName("Test simple de id")
    void IdClientDoitetreEgal() {
        this.client.setId(1);
        assertEquals(1, this.client.getId(), "L'identifiant du client doit etre à 1");
    }

    @Test
    @DisplayName("Test simple de nom")
    void nomDoitEtreEgal() {
        this.client.setNom("Duponchelle");
        assertEquals("Duponchelle", this.client.getNom(), "le nom doit etre à duponchelle");
    }
    @Test
    @DisplayName("Test simple de nom")
    void nomNeDoitPasEtreEgal() {
        this.client.setNom("du");
        assertNotEquals("Duponchelle", this.client.getNom(), "le nom doit etre à duponchelle");
    }

    @Test
    @DisplayName("Test simple de prenom")
    void prenomDoitEtreEgal() {
        this.client.setPrenom("stephane");
        assertEquals("stephane", this.client.getPrenom(), "le prenom doit etre à stephane");
    }
    @Test
    @DisplayName("Test simple de prenom")
    void prenomNeDoitPasEtreEgal() {
        this.client.setNom("Stephane");
        assertNotEquals("stephane", this.client.getPrenom(), "le prenom ne doit pas etre à stephane");
    }
    @Test
    @DisplayName("Test simple de date de naissance")
    void dateDeNaissanceDoitEtreUneDate(){
        this.client.setDateNaissance(LocalDate.of(2018,9,2));
    }
}
