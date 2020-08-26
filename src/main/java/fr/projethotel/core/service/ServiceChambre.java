package fr.projethotel.core.service;

import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.dao.ChambreDAO;
import fr.projethotel.core.entity.Chambre;
import fr.projethotel.core.entity.Hotel;


import java.lang.ref.PhantomReference;
import java.util.Scanner;

public class ServiceChambre {

    private ChambreDAO chambreDAO;
    public ServiceChambre(){
        this.chambreDAO = new ChambreDAO();
    }

    public void ajouterChambre(){

        Chambre chambre = new Chambre();
        Float prixDeBase = 50.0f;
        Integer nbDePlaceMax = 3;
        String choix="2";

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez renseigner le numero de la nouvelle chambre : ");
        Integer numero = clavier.nextInt();
        clavier.nextLine();
        chambre.setNumero(numero);

        System.out.println("Le prix de la chambre de base et de " + prixDeBase + " " +"€" + " Souhaitez vous le modifier ?  Oui(1) ou Non(0)");
        choix = clavier.nextLine();


        do {
            System.out.println("Vous n'avez pas renseigner un choix correcte veuillez entrez 1 pour modifier le prix ou 0 pour continuer ");
            choix = clavier.nextLine();
        } while (!choix.equals("1") && !choix.equals("0"));

        if(choix.equals("1")){
            System.out.println("Veuillez saisir le nouveau prix :");
            prixDeBase= clavier.nextFloat();
            clavier.nextLine();
            chambre.setPrix(prixDeBase);
        } else {
            chambre.setPrix(prixDeBase);
        }

        choix="2";
        System.out.println("Le nombre de place Maximun est de " + nbDePlaceMax + " " + " Voulez vous le modifer ? Oui(1) ou Non(0)");
        choix = clavier.nextLine();

        do {
            System.out.println("Vous n'avez pas renseigner un choix correcte veuillez entrez 1 pour modifier le nombre de place ou 0 pour continuer ");
            choix = clavier.nextLine();
        } while (!choix.equals("1") && !choix.equals("0"));

        if(choix.equals("1")){
            System.out.println("Veuillez saisir le nombre de place :");
            nbDePlaceMax= clavier.nextInt();
            clavier.nextLine();
            chambre.setNbPersonneMax(nbDePlaceMax);
        } else {
            chambre.setNbPersonneMax(nbDePlaceMax);
        }

        Hotel hotel = new Hotel();
        hotel.setId(Utilitaire.idHotel);
        hotel.setNom(Utilitaire.nomHotel);


        hotel.getChambres().add(chambre);

        chambre.setHotel(hotel);
        chambreDAO.create(chambre);

    }


}
