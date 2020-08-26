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

    public ServiceReservation() {
        this.reservationDAO = new ReservationDAO();
        this.serviceClient = new ServiceClient();
    }

    public void ajouterReservation() {
        Scanner clavier = new Scanner(System.in);
        Integer nbChambresOccupees = 0;
        Integer nbChambresVoulues = 0;
        Integer nbChambreslibres = 0;
        double divisionChambre = 0.0;
        Integer capaciteHotel = 0;

        //Recherche du client
        //TODO APPEL ServiceClient :  rechercher le client par nom/prénom/date naissance/email
        //si le client n'est pas trouvé
        Client client = serviceClient.lectureClienNomPrenomDateNaissanceEmail();
        System.out.println("Le client n'est pas référencé, créer d'abord le client");
        //sinon on continue

        //Saisie du nombre de personnes
        System.out.println("Combien de personnes  ?");
        Integer nbPersonne = clavier.nextInt();

        //Calcul du nombre de chambres nécessaires
        divisionChambre = (nbPersonne / 3);
        nbChambresVoulues = (int) (Math.ceil(divisionChambre));

        //Saisie de la date de reservation
        System.out.println("Pour quelle date effectuer la reservation  ?");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            LocalDate dateNuitee = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));

            //On vérifie que le nombre de places disponibles permet la réservation voulue
            // TODO APPEL Service chambre : Rechercher nombre de chambre capacité maximale
            //resultat int CapaciteHotel
            //On compte le nombre de chambres occupées
            //TODO service compter les chambres occupés
            //resultat int nbChambresOccupees

            //On déduit le nombre de places disponibles
            //si l'hotel est plein à cette date
            nbChambreslibres = capaciteHotel - nbChambresOccupees;
            if (nbChambresVoulues > nbChambreslibres) {
                System.out.println("Plus de place disponible à cette date");
            }
            //sinon
            else
                //Saisie de la carte bleue
                System.out.println("Veuillez saisir le numéro de carte bleue");
            Integer numeroCb = clavier.nextInt();

            //       reservationDAO.create(reservation);
        }
    }
        public void afficherReservationsJour() {
            Scanner clavier = new Scanner(System.in);
            System.out.println("Indiquer la date des réservations à visualiser  ?");
            String dateNuitee = clavier.nextLine();

        }

        public void modifierReservation () {

        }

        public void supprimerReservation () {

        }
}