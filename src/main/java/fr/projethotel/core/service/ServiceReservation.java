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

    public void facturerReservation(){
        Reservation reservation;
     // selectionner une reservation (par client et date)
        reservation = rechercherReservation();
     // passage de l'état de la reservation à payée
        reservation.setEtat("P"); //domaine de valeur : C = crée, P = payée
        reservationDAO.update(reservation);
     // mise à jour du client qui passe en archivé
        clientDAO.setTrueStatusArchiver(reservation.getClient());
}

    public void assignerChambres(){
        Reservation reservation;
        Integer nbChambres;
        List<Chambre> lesChambresDispo = null;
        List<Chambre> lesChambresAttribuees = null;
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
            System.out.println("Veuillez saisir une chambre : "+(increment+1)+ "sur "+nbChambres);
            numeroChambre = clavier.nextInt();
            //On récupère la chambre correspondant au numero saisi
            for (Chambre c:lesChambresDispo) {
                if (numeroChambre == c.getNumero()) {
                    chambreASAuvegarder = c;
                }
            }
            //alimentation de la liste les ChambresAttribuees
            lesChambresAttribuees.add(chambreASAuvegarder);
            increment++;
        }
        while (increment < nbChambres);
        // mise à jour de la reservation : ajout de la liste des chambres et modification etat
        reservation.setEtat("P"); //domaine de valeur : C = crée, P = payée
        reservation.setChambres(lesChambresAttribuees);
        reservationDAO.update(reservation);
        // mise à jour du client qui passe en archivé
        clientDAO.setTrueStatusArchiver(reservation.getClient());
    }

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

    public Reservation rechercherReservation(){
        Client client = null;
        Reservation reservation = null;
        LocalDate dateNuitee;
        //Recherche du client détenant la réservation
        client = rechercheClient();
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

    public Reservation rechercheReservationById(){
        Scanner clavier = new Scanner(System.in);
        Reservation reservation = null;
        System.out.println("Saisir une id de reservation");
        Integer id = clavier.nextInt();
        reservation = reservationDAO.getReservationById(id);
        return reservation;
    }

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

    public Long rechercheCapaciteHotel(){
        Long resultat;
        resultat =serviceChambre.getCapaciteMax();
        return resultat;
    }

    public Integer rechercheNbChambresLibres(LocalDate d){
        Integer resultat;
        resultat = (chambreDAO.getChambreDispoAtDay(d)).size();
        return resultat;
    }

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

    public Float calculMontantReservation(Long capacite, int libres, int voulues){
      Float resultat;
      Float tarifUnitaire;
      Float pourcentageOccupation = 0f;
      Float majoration = 0f;
      long occupe = 0;
      double trancheOccupation;
      tarifUnitaire = (50.0f);

      occupe = (capacite - libres);
      pourcentageOccupation = (float)(occupe/capacite*100);
      trancheOccupation = Math.floor(pourcentageOccupation/10);
      majoration = (float) (trancheOccupation*tarifUnitaire*0.1);

      resultat = ((tarifUnitaire * voulues)+ majoration);
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
