package fr.projethotel.core.service;


import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.dao.HotelDAO;
import fr.projethotel.core.entity.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





import java.util.Scanner;

public class ServiceHotel {

    private HotelDAO hotelDAO;
    static final Logger logger = LogManager.getLogger("hotelDAO");
    public ServiceHotel(){
        this.hotelDAO = new HotelDAO();
    }

    public void ajouterHotel(){

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez entrez le nom de l'Hotel : ");
        String nom = clavier.nextLine();

        Hotel hotel = new Hotel();
        hotel.setNom(nom);
        hotelDAO.create(hotel);

    }


    public Hotel getBynom(){
        Hotel hotel = null;

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez entrez le nom de votre hotel.");
        String nom = clavier.nextLine();
        hotel = hotelDAO.getByNom(nom);

        System.out.println("le nom de votre hotel est " + hotel.getNom() + " sont numéro est le "  + hotel.getId() + ".");

        Utilitaire.nomHotel = hotel.getNom();
        Utilitaire.idHotel = hotel.getId();

        return hotel;

    }

    public void modifier(){

        Hotel hotel = getBynom();
        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez rentrer le nouveau nom de l'hotel");
        String nom = clavier.nextLine();
        hotel.setNom(nom);
        hotelDAO.update(hotel);

    }




}
