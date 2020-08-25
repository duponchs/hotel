package fr.projethotel.core.service;

import fr.projethotel.core.dao.ClientDAO;
import fr.projethotel.core.entity.Client;

import java.time.LocalDate;
import java.util.Scanner;

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
        System.out.println("Quel est la date de naissance du client  ? (JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {

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
        System.out.println("Quel est la date de naissance du client recherché ? (JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {

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
    public void deleteClient() {
        Client client = null;
        Character choix;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client à supprimer ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client à supprimer ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client à supprimer ? (JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {

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
}
