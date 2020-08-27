package fr.projethotel.core.service;
import fr.projethotel.core.dao.ChambreDAO;

import java.util.Scanner;
public class ServiceMenu {
    private ServiceHotel serviceHotel;
    private ServiceClient serviceClient;
    private ServiceChambre serviceChambre;
    private ServiceReservation serviceReservation;

    public ServiceMenu(){
        this.serviceHotel = new ServiceHotel();

    }

    public void menu() {
        Scanner clavier = new Scanner(System.in);
        this.serviceHotel = new ServiceHotel();
        int choix;
        do {
            // Affichage du Menu tant que le choix est différent de 0
            System.out.println("--------- Gestion Transit'Air ----------------------");
            System.out.println();
            System.out.println("Ajouter un Hotel           : tapez 1");
            System.out.println("Mettre à jour un Hotel     : tapez 2");
            System.out.println("Supprimer un hôtel         : tapez 3");
            System.out.println("Sélectionner un Hotel      : tapez 4");
            System.out.println("----------------------------------------------------");
            System.out.println("quitter : tapez 0");
            choix = clavier.nextInt();
            // Traitement du choix
            switch (choix) {
                case 1: serviceHotel.ajouterHotel();
                    break;
                //case 2:
                //    break;
                //case 3:
                //    break;
                //case 4:
                //    break;
            }
        } while (choix != 0);
        clavier.close();
    }
    public void menuGestionGenerale() {
        Scanner clavier = new Scanner(System.in);
        this.serviceHotel = new ServiceHotel();
        int choix;
        do {
            // Affichage du Menu tant que le choix est différent de 0
            System.out.println("--------- Gestion Générale ----------------------");
            System.out.println();
            System.out.println("Gérer les clients          : tapez 1");
            System.out.println("gérer les chambres         : tapez 2");
            System.out.println("Gérer les réservations     : tapez 3");
            System.out.println("----------------------------------------------------");
            System.out.println("quitter : tapez 0");
            choix = clavier.nextInt();
            // Traitement du choix
            switch (choix) {
                case 1: menuClient();
                    break;
                case 2: menuChambre();
                //    break;
                case 3: menuReservation();
                    break;
            }
        } while (choix != 0);
        clavier.close();
    }
    public void menuClient() {
        Scanner clavier = new Scanner(System.in);
        this.serviceClient = new ServiceClient();
        int choix;
        do {
            // Affichage du Menu tant que le choix est différent de 0
            System.out.println("--------- Gestion Transit'Air ----------------------");
            System.out.println();
            System.out.println("Ajouter un Client          : tapez 1");
            System.out.println("Modifier un client         : tapez 2");
            System.out.println("Rechercher un client       : tapez 3");
            System.out.println("Supprimer un client        : tapez 4");
            System.out.println("Archiver un client         : tapez 5");
            System.out.println("Désarchiver un client      : tapez 6");
            System.out.println("----------------------------------------------------");
            System.out.println("quitter : tapez 0");
            choix = clavier.nextInt();
            // Traitement du choix
            switch (choix) {
                case 1: serviceClient.ajouterClient();
                    break;
                case 2: serviceClient.miseAJourClientChoixDansListClients();
                    break;
                case 3: serviceClient.lectureClientsParNomPrenom();
                    break;
                case 4: serviceClient.effacerClient();
                    break;
                case 5: serviceClient.archiverClient();
                    break;
                case 6: serviceClient.DesarchiverClient();
                    break;
            }
        } while (choix != 0);
        clavier.close();
    }
    public void menuChambre() {
        Scanner clavier = new Scanner(System.in);
        this.serviceChambre = new ServiceChambre();
        int choix;
        do {
            // Affichage du Menu tant que le choix est différent de 0
            System.out.println("--------- Gestion des chambres ----------------------");
            System.out.println();
            System.out.println("Consulter les chambres disponibles  : tapez 1");
            System.out.println("Ajouter une chambre                 : tapez 2");
            System.out.println("Archiver une chambre                : tapez 3");
            System.out.println("Désarchiver une chambre             : tapez 4");
            System.out.println("Supprimer une chambre               : tapez 5");
            System.out.println("----------------------------------------------------");
            System.out.println("quitter : tapez 0");
            choix = clavier.nextInt();
            // Traitement du choix
            switch (choix) {
                case 1: serviceChambre.getChambreDispo();
                    break;
                case 2: serviceChambre.ajouterChambre();
                    break;
                case 3: serviceChambre.archiverChambre();
                    break;
                case 4: serviceChambre.DesarchiverChambre();
                    break;
                case 5: serviceChambre.supprimerChambre();
                    break;
            }
        } while (choix != 0);
        clavier.close();
    }
    public void menuReservation() {
        Scanner clavier = new Scanner(System.in);
        this.serviceReservation = new ServiceReservation();
        int choix;
        do {
            // Affichage du Menu tant que le choix est différent de 0
            System.out.println("--------- Gestion Transit'Air ----------------------");
            System.out.println();
            System.out.println("Ajouter une réservation                 : tapez 1");
            System.out.println("Confirmer une réservation               : tapez 2");
            System.out.println("Afficher les réservations pour une date : tapez 3");
            System.out.println("Supprimer une réservation               : tapez 4");
            System.out.println("Facturer une réservation non pourvue    : tapez 5");
            System.out.println("----------------------------------------------------");
            System.out.println("quitter : tapez 0");
            choix = clavier.nextInt();
            // Traitement du choix
            switch (choix) {
                case 1: serviceReservation.ajouterReservation();
                    break;
                case 2: serviceReservation.assignerChambres();
                    break;
                case 3: serviceReservation.afficherReservationsJour();
                    break;
                case 4: serviceReservation.supprimerReservation();
                    break;
                case 5: serviceReservation.facturerReservation();
                    break;
            }
        } while (choix != 0);
        clavier.close();
    }
}


