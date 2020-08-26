package fr.projethotel.core.service;

import fr.projethotel.core.dao.ReservationDAO;
import fr.projethotel.core.service.ServiceClient;
import fr.projethotel.core.entity.Reservation;
import fr.projethotel.core.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.Scanner;


public class ServiceReservation {
    private ReservationDAO reservationDAO;
    private ServiceClient serviceClient;
    static final Logger logger = LogManager.getLogger("ServiceReservation");
    public ServiceReservation(){
        this.reservationDAO = new ReservationDAO();
        this.serviceClient = new ServiceClient();}


    public  void  ajouterReservation(){
        Scanner clavier = new Scanner(System.in);
        Integer nbChambresOccupees = 0;
        Integer nbChambresVoulues = 0;
        Integer nbChambreslibres = 0;
        double divisionChambre = 0.0;
        Integer capaciteHotel = 0;
        Float montantReservation = 0f;
        LocalDate dateNuitee = null;

        //Recherche du client
        Client client = serviceClient.lectureClienNomPrenomDateNaissanceEmail();
        if (client == null){
            logger.fatal("Le client n'est pas référencé, créer d'abord le client");}

        //Saisie du nombre de personnes pour la reservation
        System.out.println("Combien de personnes  ?");
        Integer nbPersonne = clavier.nextInt();

        //Calcul du nombre de chambres nécessaires
        divisionChambre = (nbPersonne /3);
        nbChambresVoulues = (int)(Math.ceil(divisionChambre));

        //Saisie de la date de reservation
        System.out.println("Pour quelle date effectuer la reservation  ? JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if(dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            dateNuitee = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));}
        else {
            logger.fatal("Une erreur de saisie sur la date de reservation est survenue");}

        //On recherche la capacité maximale disponible de l'hotel
        // TODO APPEL Service chambre : Rechercher nombre de chambre capacité maximale
        //resultat int CapaciteHotel alimenté en attendant
        capaciteHotel = 10;

        //On compte le nombre de chambres occupées
        //TODO service compter les chambres occupés
        //resultat int nbChambresOccupees

        //On déduit le nombre de places disponibles
        //si l'hotel est plein à cette date
        nbChambreslibres = capaciteHotel - nbChambresOccupees;
        if (nbChambresVoulues > nbChambreslibres) {
            System.out.println("Plus de place disponible à cette date");}

        //Calcul du montant à payer

        //Validation de la transaction suite à l'accord du client
        System.out.println("Prix de la réservation : "+montantReservation+" Euros");
        System.out.println("Confirmer la réservation ? [O]ui ou [N]on");
        String validation = clavier.nextLine();
        do {
            switch (validation) {
                case "O":
                    System.out.println("Transaction acceptée par le client");
                case "N":
                    System.out.println("Transaction refusée par le client");
                    break;
                default:
            }
        } while (validation != "O" && "N");

        //Saisie de la carte bleue
        System.out.println("Veuillez saisir le numéro de carte bleue");
        Integer numeroCb = clavier.nextInt();

        //Finalisation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setNbPersonne(nbPersonne);
        reservation.setEtat("E");
        reservation.setMontant(montantReservation);
        reservation.setNumeroCb(numeroCb);
        reservation.setDateNuitee(dateNuitee);
        //reservation.setIdHotel(Utilitaire.IdHotel);
        reservationDAO.create(reservation);
    }

    public void afficherReservationsJour(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Indiquer la date des réservations à visualiser  ?");
        String dateNuitee = clavier.nextLine();

    }

    public void modifierReservation(){

    }

    public void supprimerReservation(){

    }
}
