package fr.projethotel.core.service;

import fr.projethotel.core.dao.HotelDAOImpl;
import fr.projethotel.core.entity.Hotel;


import java.util.Scanner;

public class ServiceHotel {

    private HotelDAOImpl hotelDAO;
    public ServiceHotel(){
        this.hotelDAO = new  HotelDAOImpl();
    }

    public void ajouterHotel(){

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez entrez le nom de l'Hotel : ");
        String nom = clavier.nextLine();

        Hotel hotel = new Hotel();

        hotel.setNom(nom);
        hotelDAO.create(hotel);

    }

}
