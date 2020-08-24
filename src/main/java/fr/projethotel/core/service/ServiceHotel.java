package fr.projethotel.core.service;

import fr.projethotel.core.dao.HotelDAO;
import fr.projethotel.core.entity.Hotel;


import java.util.Scanner;

public class ServiceHotel {

    private HotelDAO hotelDAO;
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

}
