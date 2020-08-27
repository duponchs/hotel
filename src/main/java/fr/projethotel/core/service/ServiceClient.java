package fr.projethotel.core.service;
import fr.projethotel.core.dao.ClientDAO;
import fr.projethotel.core.entity.Client;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import fr.projethotel.core.entity.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServiceClient {

    private ClientDAO clientDAO;
    static final Logger logger = LogManager.getLogger("ServiceClient");

    public ServiceClient() {
        this.clientDAO = new ClientDAO();
    }

    public void ajouterClient() {
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client  ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client  ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client  ? (JJ-MM-YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

            LocalDate date = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));
            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setDateNaissance(date);

            System.out.println("Quel est l'adresse mail du client  ?");
            client.setEmail(clavier.nextLine());

            clientDAO.create(client);
            System.out.println("le client a été créé, son identifiant est " + client.getId());
        } else {
            logger.info("Une erreur de saisie sur la date de naissance est survenue");
        }
    }

    public Client lectureClientParId() {
        Client client = null;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est l'identifiant du client recherché ?");
        Integer id = clavier.nextInt();
        client = clientDAO.getById(id);
        return client;
    }

    public Client lectureClienNomPrenomDateNaissanceEmail() {
        Client client = null;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client recherché ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client recherché ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client recherché ? (JJ-MM-YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

            LocalDate dateNaissance = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));


            System.out.println("Quel est l'adresse mail du client recherché ?");
            String email = clavier.nextLine();

            client = clientDAO.getByNomPrenomEmail(nom, prenom, dateNaissance, email);
            System.out.println("Le client recherché est " + client.getId() + " " + client.getNom() + " " + client.getPrenom());
        } else {
            logger.info("Une erreur de saisie sur la date de naissance est survenue");
        }
        return client;
    }
    public void lectureClientsParNomPrenom() {
        Scanner clavier = new Scanner(System.in);
        List<Client> desClients =null;

        System.out.println("Quel est le nom du client recherché ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client recherché ?");
        String prenom = clavier.nextLine();
        desClients = clientDAO.getListByNomPrenom(nom,prenom);

        System.out.println("--------------------------------- Liste des clients recherchés ---------------------------------------------------");
        for (Client client:desClients) {
            System.out.println("Id : "+client.getId());
            System.out.println("Nom : "+client.getNom());
            System.out.println(("Prenom : "+client.getPrenom()));
            System.out.println("Date de naissance : "+ client.getDateNaissance());
            System.out.println("Email : "+client.getEmail());
            System.out.println("Archiver ? :"+client.getArchiver());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
    public void effacerClientFullDetails() {
        Client client = null;
        Character choix;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client à supprimer ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client à supprimer ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client à supprimer ? (JJ-MM-YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

            LocalDate dateNaissance = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));

            System.out.println("Quel est l'adresse mail du client à supprimer ?");
            String email = clavier.nextLine();

            client = clientDAO.getByNomPrenomEmail(nom, prenom, dateNaissance, email);

            if(client!=null){
                System.out.println("Le client supprimé est " + client.getId() + " " + client.getNom() + " " + client.getPrenom()+" "+client.getDateNaissance()+" "+client.getEmail());
                do{
                    System.out.println("Êtes-vous sûr de bien vouloir supprimer ce client ? (y/n)");
                    choix = clavier.nextLine().toLowerCase().charAt(0);
                }while(choix != 'y' && choix!= 'n');

                if(choix=='y')
                    clientDAO.delete(client);
                else
                    System.out.println("Abandon de suppression");
            }else{
                logger.fatal("Client non reconnue");
            }

        } else {
            logger.info("Une erreur de saisie sur la date de naissance est survenue");
        }
    }
    public void effacerClient() {
        lectureClientsParNomPrenom();
        Client client = null;
        Client clientOk = null;
        Scanner clavier = new Scanner(System.in);
        client = lectureClientParId();
        Character choix;

        if(client!=null){
            System.out.println("Le client supprimé est id : "
                    + client.getId() + " nom  "
                    + client.getNom() + " prenom "
                    + client.getPrenom()+" date de naissance "
                    + client.getDateNaissance()+" email "
                    + client.getEmail()
            );
            do{
                System.out.println("Êtes-vous sûr de bien vouloir supprimer ce client ? (y/n)");
                choix = clavier.nextLine().toLowerCase().charAt(0);
            }while(choix != 'y' && choix!= 'n');

            if(choix=='y')
                clientDAO.delete(client);
            else
                System.out.println("Abandon de suppression");
        }else{
            logger.fatal("Client non reconnue");
        }

    }

    public void miseAJourClientFullDetails() {
        Client client = lectureClienNomPrenomDateNaissanceEmail();
        Scanner clavier = new Scanner(System.in);
        Character choix;
        if(client!=null){
            System.out.println("Le client à actualiser est " + client.getId() + " " + client.getNom() + " " + client.getPrenom()+" "+client.getDateNaissance()+" "+client.getEmail());
            do{
                System.out.println("Êtes-vous sûr de bien vouloir actualiser ce client ? (y/n)");
                choix = clavier.nextLine().toLowerCase().charAt(0);
            }while(choix != 'y' && choix!= 'n');

            if(choix=='y'){
                System.out.println("Quel est le nouveau nom du client ?");
                String nom = clavier.nextLine();
                System.out.println("Quel est le nouveau prenom du client ?");
                String prenom = clavier.nextLine();
                System.out.println("Quel est la nouvelle date de naissance du client ? (JJ-MM-YYYY) ");
                String dateString = clavier.nextLine();
                if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

                    LocalDate dateNaissance = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));

                    System.out.println("Quel est la nouvelle adresse mail du client ?");
                    String email = clavier.nextLine();

                    client.setNom(nom);
                    client.setPrenom(prenom);
                    client.setDateNaissance(dateNaissance);
                    client.setEmail(email);
                    //todo : Voir ici pour désarchiver ??

                    clientDAO.update(client);
                }else{
                    logger.info("Une erreur de saisie sur la date de naissance est survenue");
                }
            }else {
                System.out.println("Abandon de l'actualisation");
            }
        }else{
            logger.fatal("Client non reconnue");
        }
    }
    public void miseAJourClientChoixDansListClients() {
        lectureClientsParNomPrenom();
        Client client = null;
        Client clientOk = null;
        Scanner clavier = new Scanner(System.in);

        client = lectureClientParId();

        if(client != null){
            clientOk = client;
            System.out.println("Quel est le nouveau nom du client ?");
            String nom = clavier.nextLine();
            System.out.println("Quel est le nouveau prenom du client ?");
            String prenom = clavier.nextLine();
            System.out.println("Quel est la nouvelle date de naissance du client ? (JJ-MM-YYYY) ");
            String dateString = clavier.nextLine();
            if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

                LocalDate dateNaissance = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)), Integer.valueOf(dateString.substring(3, 5)), Integer.valueOf(dateString.substring(0, 2)));

                System.out.println("Quel est la nouvelle adresse mail du client ?");
                String email = clavier.nextLine();
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setDateNaissance(dateNaissance);
                client.setEmail(email);
                //todo : Voir ici pour désarchiver ??

                clientDAO.update(client);
            }else{
                logger.info("Une erreur de saisie sur la date de naissance est survenue");
            }
        } else{
            logger.trace("Pas de client avec cette identifiant");
        }
    }
    public void rechercheDesReservations(){
        Scanner clavier = new Scanner(System.in);
        List<Reservation> desReservations =null;

        System.out.println("Quel est le client recherché ?");
        Integer id = clavier.nextInt();

        desReservations = clientDAO.listSearchReservation(id);

        if (desReservations != null){
            System.out.println("--------------------------------- Liste des Reservations du client recherché ---------------------------------------------------");
            for (Reservation reservation: desReservations) {
                System.out.println("Numéro de reservation : "+reservation.getId());
                System.out.println("hotel : "+ reservation.getIdHotel());
                System.out.println("Prix : "+reservation.getMontant());
                System.out.println("Date de Nuitée : "+reservation.getDateNuitee());
                System.out.println("Nombre de personnes : "+reservation.getNbPersonne());
                System.out.println("Client associe a la reservation : "+reservation.getClient().getId());
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------");
        }else{
            logger.trace("aucune reservation pour ce client");
        }
    }
    public Client rechercheDesReservationsPlusIntegrationClient(){
        Scanner clavier = new Scanner(System.in);
        List<Reservation> desReservations =null;
        Client client = null;
        lectureClientsParNomPrenom();
        System.out.println("Quel est le client recherché ?");
        Integer id = clavier.nextInt();

        desReservations = clientDAO.listSearchReservation(id);

        if (desReservations != null){
            System.out.println("--------------------------------- Liste des Reservations du client recherché ---------------------------------------------------");
            for (Reservation reservation: desReservations) {
                System.out.println("Numéro de reservation : "+reservation.getId());
                System.out.println("hotel : "+ reservation.getIdHotel());
                System.out.println("Prix : "+reservation.getMontant());
                System.out.println("Date de Nuitée : "+reservation.getDateNuitee());
                System.out.println("Nombre de personnes : "+reservation.getNbPersonne());
                System.out.println("Client associe a la reservation : "+reservation.getClient().getId());
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------");
        }else{
            logger.trace("aucune reservation pour ce client");
        }
        client = clientDAO.getById(id);
        return client;
    }
    public void archiverClient(){
        Scanner clavier = new Scanner(System.in);
        lectureClientsParNomPrenom();
        System.out.println("Quel est le client à archiver ?");
        System.out.println("Id ? :");
        Integer id = clavier.nextInt();
        Client client = clientDAO.getById(id);
        clientDAO.setTrueStatusArchiver(client);
    }
    public void DesarchiverClient(){
        Scanner clavier = new Scanner(System.in);
        lectureClientsParNomPrenom();
        System.out.println("Quel est le client à désarchiver ?");
        System.out.println("Id ? :");
        Integer id = clavier.nextInt();
        Client client = clientDAO.getById(id);
        clientDAO.setFalseStatusArchiver(client);
    }

    
}
