package fr.projethotel.core.service;

import fr.projethotel.core.dao.ChambreDAO;
import fr.projethotel.core.dao.ClientDAO;
import fr.projethotel.core.dao.ReservationDAO;
import fr.projethotel.core.entity.Chambre;
import fr.projethotel.core.entity.Reservation;
import fr.projethotel.core.entity.Client;
import fr.projethotel.core.Utilitaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ServiceReservation {
    private ReservationDAO reservationDAO;
    private ChambreDAO chambreDAO;
    private ClientDAO clientDAO;
    private ServiceClient serviceClient;
    private ServiceChambre serviceChambre;
    static final Logger logger = LogManager.getLogger("ServiceReservation");
    public ServiceReservation() {
        this.reservationDAO = new ReservationDAO();
        this.chambreDAO = new ChambreDAO();
        this.clientDAO = new ClientDAO();
        this.serviceClient = new ServiceClient();
        this.serviceChambre = new ServiceChambre();
    }
    //Méthode d'ajout d'une réservation
    public  void  ajouterReservation(){
        Integer nbChambresOccupees = 0;
        Integer nbChambresVoulues = 0;
        Integer nbChambresLibres = 0;
        Long capaciteHotel ;
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
            System.out.println("nbPersonne"+nbPersonne);
            if (nbPersonne > 0) {
                //Calcul du nombre de chambres nécessaires
                nbChambresVoulues = calculNbChambresVoulues(nbPersonne);
                //Saisie de la date de réservation
                dateNuitee = saisieDateNuitee();
                if (dateNuitee != null) {
                    //Recherche de la capacité de l'hotel
                    capaciteHotel = rechercheCapaciteHotel();
                    //Recherche du nombre de chambres libres à la date voulue
                    nbChambresLibres = rechercheNbChambresLibres(dateNuitee);
                    //Vérification que le nombre de chambres est suffisant
                    nbChambreSuffisant = verificationNbChambreSuffisant(nbChambresLibres, nbChambresVoulues);
                    if (nbChambreSuffisant = true) {
                        //Calcul du montant à payer et affichage
                        montantReservation = calculMontantReservation(capaciteHotel,nbChambresLibres, nbChambresVoulues);
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
    //Méthode pour passer une réservation à l'état facturable
    public void facturerReservation(){
        Reservation reservation;
     // selectionner une reservation (par client et date)
        reservation = rechercherReservation();
     // passage de l'état de la reservation à payée
        System.out.println(reservation.getEtat());
        reservationDAO.update(reservation);
     // mise à jour du client qui passe en archivé
        clientDAO.setTrueStatusArchiver(reservation.getClient());
}
    //Méthode pour assigner une ou plusieurs chambres à une reservation
    public void assignerChambres(){
        Reservation reservation;
        Integer nbChambres;
        List<Chambre> lesChambresDispo = null;
        List<Chambre> lesChambresAttribuees = new ArrayList<>();
        Integer increment = 0;
        Integer numeroChambre;
        Scanner clavier = new Scanner(System.in);
        Chambre chambreASAuvegarder = null;

        // selectionner une reservation (par client et date)
        reservation = rechercherReservation();

        // recuperer la liste des chambres disponibles pour cet hotel
        lesChambresDispo = serviceChambre.getChambreDispo();

        // création de la liste de chambre(s) à assigner
        nbChambres = calculNbChambresVoulues(reservation.getNbPersonne());
        do{
            //Saisie d'un numero de chambre
            System.out.println("Veuillez saisir une chambre : "+(increment+1)+ " sur "+nbChambres);
            numeroChambre = clavier.nextInt();
            //On récupère la chambre correspondant au numero saisi
            for (Chambre c:lesChambresDispo) {
                if (numeroChambre == c.getNumero()) {
                    chambreASAuvegarder = c;
                    lesChambresAttribuees.add(chambreASAuvegarder);
                    increment++;
                }
            }
        }
        while (increment < nbChambres);
        System.out.println("Récapitulatif des chambres attribuées");
        for (Chambre c2:lesChambresAttribuees) {
            System.out.println("chambre numéro "+c2.getNumero()+" Id : "+c2.getId());
        }
        // mise à jour de la reservation : ajout de la liste des chambres et modification etat
        reservation.setEtat("P"); //domaine de valeur : C = crée, P = payée
        reservation.setChambres(lesChambresAttribuees);
        for (Chambre c3:reservation.getChambres()) {
            System.out.println("chambre numéro X "+c3.getNumero()+" Id : "+c3.getId());
        }
        reservationDAO.update(reservation);
        // mise à jour du client qui passe en archivé
        clientDAO.setTrueStatusArchiver(reservation.getClient());
    }
    //Méthode pour afficher les réservation à une date donnée en entrée
    public void afficherReservationsJour(){
        LocalDate dateNuitee;
        List<Reservation> desReservations =null;
        List<Chambre> desChambres =null;
        //Saisie de la date
        dateNuitee = saisieDateNuitee();
        //Recherche de toutes les réservations à cette date
        desReservations = reservationDAO.getListByDate(dateNuitee);
        System.out.println("--------------------------------- Liste des réservations ---------------------------------------------------");
        for (Reservation reservation:desReservations) {
            //Affichage d'une réservation
            System.out.println("Réservation :"+reservation.getId());
            System.out.println("Id hotel :" + reservation.getIdHotel());
            System.out.println("Date :" + reservation.getDateNuitee());
            System.out.println("Montant :"+ reservation.getMontant());
            System.out.println("Etat :"+reservation.getEtat());
            System.out.println("Client nom:" + reservation.getClient().getNom());
            System.out.println("Client prénom:" + reservation.getClient().getPrenom());
            System.out.println("Client mail:" + reservation.getClient().getEmail());
            desChambres = reservation.getChambres();
            for (Chambre chambre:desChambres) {
                System.out.println("Chambre " + chambre.getNumero());
            }
        }
    }


    //Méthode pour supprimer une réservation
    public void supprimerReservation(){
        Reservation reservation = null;
        LocalDate dateNuitee;
        Boolean validation;
        //Selection de la reservation
        reservation = rechercherReservation();
        //valider la transaction
        validation = saisieValidation();
        if (validation = true) {
            reservationDAO.delete(reservation);
        }

    }
    //Méthode pour récupérer une réservation avec choix du client et de la date de réservation
    public Reservation rechercherReservation(){
        Client client = null;
        Reservation reservation = null;
        LocalDate dateNuitee;
        //Recherche du client détenant la réservation
        client = rechercheClient();
        System.out.println("marqueur test");
        //Saisie de la date de nuitée de la réservation à annuler
        if (client != null) {
            dateNuitee = saisieDateNuitee();
            if (dateNuitee != null) {
                //Retrouver la reservation correspondante
                reservation = reservationDAO.getByClientDate(client, dateNuitee);

            }
        }
    return reservation;
    }
    //Méthode pour récupérer une réservation par son id en entrée
    public Reservation rechercheReservationById(){
        Scanner clavier = new Scanner(System.in);
        Reservation reservation = null;
        System.out.println("Saisir une id de reservation");
        Integer id = clavier.nextInt();
        reservation = reservationDAO.getReservationById(id);
        return reservation;
    }
    //Méthode pour afficher une liste de client selon nom et prénom
    public Client rechercheClient() {
        serviceClient.lectureClientsParNomPrenom();
        Client client = serviceClient.lectureClientParId();
        if (client == null) {
            logger.fatal("Le client n'est pas référencé, créer d'abord le client");
        }
        else {
            System.out.println("Client trouvé");

        }
        return client;
    }
    //Méthode pour saisir et enregistrer le nombre de personnes voulues pour une réservation
    public Integer saisieNbPersonne(){
        int saisie;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Combien de personnes  ?");
        saisie = clavier.nextInt();
        return saisie;
    }
    //Méthode pour calculer le nombre de chambres nécessaires en fonction du nombre de personnes voulues
    public Integer calculNbChambresVoulues(int i){
        double divisionChambre;
        double nbPlaceMax;
        nbPlaceMax = 3;
        divisionChambre = (i /nbPlaceMax);
        return (int)(Math.ceil(divisionChambre));
    }
    //méthode pour saisir et enregistrer la date de réservation
    public LocalDate saisieDateNuitee(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Saisir la date au format JJ-MM-AAAA) ");
        String dateString = clavier.nextLine();
        if(dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
            return LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));}
        else {
            logger.fatal("Une erreur de saisie sur la date est survenue");
            return null;
        }
    }
    //Méthode pour récupérer la capacité de l'hotel
    public Long rechercheCapaciteHotel(){
        Long resultat;
        resultat =serviceChambre.getCapaciteMax();
        return resultat;
    }
    //méthode pour récupérer le nombre de chambres libres
    public Integer rechercheNbChambresLibres(LocalDate d){
        Integer resultat;
        resultat = (chambreDAO.getChambreDispoAtDay(d)).size();
        return resultat;
    }
    //Méthode pour vérifier si il reste assez de chambres libres
    public Boolean verificationNbChambreSuffisant(int libres, int voulues){

        if (libres >= voulues ){
            System.out.println("Nombre de chambre suffisant");
            return true;
        }
        else {
            System.out.println("Nombre de chambre insuffisant");
            return false;
        }
    }
    //méthode pour calculer le montant de la réservation
    public Float calculMontantReservation(Long capacite, int libres, int voulues){
        Float resultat;
        Float tarifUnitaire;
        Float pourcentageOccupation = 0f;
        Float majoration = 0f;
        long occupe = 0;
        double trancheOccupation;
        tarifUnitaire = (50.0f);
        System.out.println("capacite "+capacite);
        System.out.println("libres "+libres);
        System.out.println("voulues"+voulues);
        occupe = (capacite - libres);
        System.out.println("occupe "+occupe);
        pourcentageOccupation = (float)(occupe/capacite*100);
        System.out.println("pourcentageOccupation "+pourcentageOccupation);
        trancheOccupation = Math.floor(pourcentageOccupation/10);
        System.out.println("trancheOccupation "+trancheOccupation);
        majoration = (float) (trancheOccupation*tarifUnitaire*0.1);
        System.out.println("majoration " + majoration);

        resultat = ((tarifUnitaire * voulues)+ majoration);
        System.out.println("resultat montant " +resultat);
        System.out.println("Prix de la réservation : "+resultat+" Euros");
        return resultat;
    }
    //Méthode pour saisir et enregistrer le numéro de carte bleue
    public Integer saisieNumeroCb(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez saisir un numéro de CB?");
        Integer numero = clavier.nextInt();
        return numero;
    }

    //Méthode pour valider une transaction
    public Boolean saisieValidation(){
        Scanner clavier = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Confirmer l'opération ? [O]ui ou [N]on");
            choix = clavier.nextLine();
            switch (choix) {
                case "O":
                    System.out.println("Transaction acceptée");
                    return true;
                case "N":
                    System.out.println("Transaction annulée");
                    return false;
                default:
                    System.out.println("Saisie incorrecte");
                    return false;
            }
        } while (choix != "O" && choix != "N");
    }

    // Méthode pour enregistrer une réservation
    public void enregistrerReservation(Client client, Integer nbPersonne, Float montantReservation, Integer numeroCb, LocalDate dateNuitee){
        this.reservationDAO = new ReservationDAO();
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setNbPersonne(nbPersonne);
        reservation.setEtat("C"); //domaine de valeur : C = crée, P = payée
        reservation.setMontant(montantReservation);
        reservation.setNumeroCb(numeroCb);
        reservation.setDateNuitee(dateNuitee);
        reservation.setIdHotel(Utilitaire.getIdHotel());
        reservationDAO.create(reservation);
    }

}
