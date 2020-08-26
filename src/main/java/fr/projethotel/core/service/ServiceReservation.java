package fr.projethotel.core.service;

import fr.projethotel.core.dao.ReservationDAO;
import fr.projethotel.core.service.ServiceClient;
import fr.projethotel.core.entity.Reservation;
import fr.projethotel.core.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ServiceReservation {
    private ReservationDAO reservationDAO;
    private ServiceClient serviceClient;
    static final Logger logger = LogManager.getLogger("ServiceReservation");
    public ServiceReservation(){
        this.reservationDAO = new ReservationDAO();
        this.serviceClient = new ServiceClient();}


    public  void  ajouterReservation(){
        Integer nbChambresOccupees = 0;
        Integer nbChambresVoulues = 0;
        Integer capaciteHotel = 0;
        Float montantReservation = 0f;
        LocalDate dateNuitee = null;
        Client client = null;
        Integer nbPersonne = 0;
        Boolean nbChambreSuffisant = false;
        Integer numeroCb = 0;
        Boolean validation = false;

        System.out.println("TRANSACTION : Ajout d'une réservation");

        //Saisie et recherche du client
        client = rechercheClient();
        if (client != null) {
            //Saisie du nombre de personnes
            nbPersonne = saisieNbPersonne();
            if (nbPersonne > 0) {
                //Calcul du nombre de chambres nécessaires
                nbChambresVoulues = calculNbChambresVoulues(nbPersonne);
                //Saisie de la date de réservation
                dateNuitee = saisieDateNuitee();
                if (dateNuitee != null) {
                    //Recherche de la capacité de l'hôtel à la date voulue
                    capaciteHotel = rechercheCapaciteHotel(dateNuitee);
                    //Recherche du nombre de chambres occupées à la date voulue
                    nbChambresOccupees = rechercheNbChambresOccupees(dateNuitee);
                    //Vérification que le nombre de chambres est suffisant
                    nbChambreSuffisant = verificationNbChambreSuffisant(capaciteHotel,nbChambresOccupees, nbChambresVoulues);
                    if (nbChambreSuffisant = true) {
                        //Calcul du montant à payer et affichage
                        montantReservation = calculMontantReservation(nbChambresVoulues);
                        //Validation de la transaction
                        validation = saisieValidation();
                        if (validation = true) {
                            //Saisie du numéro de carte bleue
                            numeroCb = saisieNumeroCb();
                            //Enregistrement de la transaction
                            enregistrerReservation(client, nbPersonne, montantReservation, numeroCb, dateNuitee);
                            System.out.println("Fin de la transaction de création d'une réservation");
                        }
                    }
                }
            }
        }
    }


    public void afficherReservationsJour(){
        LocalDate dateNuitee;
        List<Reservation> desReservations =null;
        //Saisie de la date
        dateNuitee = saisieDateNuitee();
        //Recherche de toutes les réservations à cette date
        desReservations = reservationDAO.getListByDate(dateNuitee);
        System.out.println("--------------------------------- Liste des réservations ---------------------------------------------------");
        for (Reservation reservation:desReservations) {
            //recherche de(s) chambre(s) pour la reservation de l'itération en cours

            //Affichage d'une réservation
            System.out.println("Réservation :"+reservation.getId());
            System.out.println("Id hotel :" + reservation.getIdHotel());
            System.out.println("Date :" + reservation.getDateNuitee());
            System.out.println("Montant :"+ reservation.getMontant());
            System.out.println("Etat :"+reservation.getEtat());
            System.out.println("Client nom:" + reservation.getClient().getNom());
            System.out.println("Client prénom:" + reservation.getClient().getPrenom());
            System.out.println("Client mail:" + reservation.getClient().getEmail());
            System.out.println("Chambres "); //TODO


        }


    }



    public void supprimerReservation(){
        Client client = null;
        LocalDate dateNuitee;
        Boolean validation;
        //Recherche du client détenant la réservation
        client = rechercheClient();
        //Saisie de la date de nuitée de la réservation à annuler
        if (client != null) {
            dateNuitee = saisieDateNuitee();
            if (dateNuitee != null) {
                //Retrouver la reservation correspondante
                //reservationDAO.getByClientDate(client, dateNuitee);
                //valider la transaction
                validation = saisieValidation();
                //if (validation = true) {
                //    reservationDAO.delete(reservation);
                //}
            }
        }

    }

    public Client rechercheClient() {
        Client client = serviceClient.lectureClienNomPrenomDateNaissanceEmail();
        if (client == null) {
            logger.fatal("Le client n'est pas référencé, créer d'abord le client");
        }
        else {
            System.out.println("Client trouvé");

        }
        return client;
    }

    public Integer saisieNbPersonne(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Combien de personnes  ?");
        if (clavier.nextInt() < 1) {
            logger.fatal("Saisir au moins une personne");
        }
        return clavier.nextInt();
    }

    public Integer calculNbChambresVoulues(int i){
        double divisionChambre;
        Integer nbPlaceMax;
        nbPlaceMax = 3;
        divisionChambre = (i /nbPlaceMax);
        return (int)(Math.ceil(divisionChambre));
    }

    public LocalDate saisieDateNuitee(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Saisir la date au format JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if(dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            return LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));}
        else {
            logger.fatal("Une erreur de saisie sur la date est survenue");
            return null;
        }
    }

    public Integer rechercheCapaciteHotel(LocalDate d){
        Integer resultat;
        //TODO resultat = ChambreDAO.getCapaciteMax(d)
        resultat = 10;
        return resultat;
    }

    public Integer rechercheNbChambresOccupees(LocalDate d){
        Integer resultat;
        //TODO implémenter la recherche
        resultat = 4;
        return resultat;
    }

    public Boolean verificationNbChambreSuffisant(int capacite, int occupe, int voulues){

        if (capacite-occupe >= voulues ){
            System.out.println("Nombre de chambre suffisant");
            return true;
        }
        else {
            System.out.println("Nombre de chambre insuffisant");
            return false;
        }
    }

    public Float calculMontantReservation(int i){
      Float resultat;
      Float tarifUnitaire;
      tarifUnitaire = (50.0f);
      resultat = (tarifUnitaire * i);
      System.out.println("Prix de la réservation : "+resultat+" Euros");
      return resultat;
    }

    public Integer saisieNumeroCb(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Combien de personnes  ?");
        Integer numero = clavier.nextInt();
        return numero;
    }

    public Boolean saisieValidation(){
        Scanner clavier = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Confirmer l'opération ? [O]ui ou [N]on");
            choix = clavier.nextLine();
            switch (choix) {
                case "O":
                    System.out.println("Transaction acceptée");
                    break;
                case "N":
                    System.out.println("Transaction annulée");
                    break;
                default:
                    System.out.println("Saisie incorrecte");
                    break;
            }
        } while (choix != "O" && choix != "N");

        return true;
    }

    public void enregistrerReservation(Client client, Integer nbPersonne, Float montantReservation, Integer numeroCb, LocalDate dateNuitee){
        this.reservationDAO = new ReservationDAO();
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setNbPersonne(nbPersonne);
        reservation.setEtat("C");
        reservation.setMontant(montantReservation);
        reservation.setNumeroCb(numeroCb);
        reservation.setDateNuitee(dateNuitee);
        //reservation.setIdHotel(Utilitaire.IdHotel);
        reservationDAO.create(reservation);
    }

}
