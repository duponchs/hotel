package fr.projethotel.core.service;

import fr.projethotel.core.DAO.ClientDAOImpl;
import fr.projethotel.core.entity.Client;

import java.time.LocalDate;
import java.util.Scanner;

public class ServiceClient {

    private ClientDAOImpl clientDAO;
    public ServiceClient(){
        this.clientDAO = new ClientDAOImpl();
    }
    public  void  ajouterClient(){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le nom du client  ?");
        String nom = clavier.nextLine();
        System.out.println("Quel est le prenom du client  ?");
        String prenom = clavier.nextLine();
        System.out.println("Quel est la date de naissance du client  ? (YYYY/MM/JJ) ");
        String dateString = clavier.nextLine();

        LocalDate date = LocalDate.of(Integer.valueOf(dateString.substring(0,5)),Integer.valueOf(dateString.substring(0,5)),Integer.valueOf(dateString.substring(0,5)));


        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(date);

        clientDAO.create(client);
        System.out.println("le joueur a été créé, son identifiant est "+client.getId());
    }
}
