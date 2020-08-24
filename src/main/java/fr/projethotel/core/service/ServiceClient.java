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
    public ServiceClient(){
        this.clientDAO = new ClientDAO();
    }
    public  void  ajouterClient(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client  ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client  ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client  ? (JJ/MM/YYYY) ");
        String dateString = clavier.nextLine();
        if(dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")){

            LocalDate date = LocalDate.of(Integer.valueOf(dateString.substring(6,10)),Integer.valueOf(dateString.substring(3,5)),Integer.valueOf(dateString.substring(0,2)));
            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setDateNaissance(date);

            clientDAO.create(client);
            System.out.println("le client a été créé, son identifiant est "+client.getId());
        }else{
            logger.info("Une erreur de saisie sur la date de naissance est survenue");
        }

    }
}
