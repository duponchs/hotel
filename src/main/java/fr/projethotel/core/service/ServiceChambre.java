package fr.projethotel.core.service;

import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.dao.ChambreDAO;
import fr.projethotel.core.dao.HotelDAO;
import fr.projethotel.core.entity.Chambre;
import fr.projethotel.core.entity.Hotel;


import java.util.Scanner;

public class ServiceChambre {

    private ChambreDAO chambreDAO;
    private HotelDAO hotelDAO;
    public ServiceChambre(){
        this.chambreDAO = new ChambreDAO();
        this.hotelDAO = new HotelDAO();
    }


    public void ajouterChambre(){

        Chambre chambre = new Chambre();

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez renseigner le numero de la nouvelle chambre : ");
        Integer numero = clavier.nextInt();

        chambre.setNumero(numero);

        Hotel hotel = new Hotel();

        hotel.setId(Utilitaire.idHotel);
        hotel.setNom(Utilitaire.nomHotel);
        chambre.setHotel(hotel);
        chambreDAO.create(chambre);
        hotel.getChambres().add(chambre);
        hotelDAO.update(hotel);


    }

    public Long getCapaciteMax(){

        Long capaciteMax = chambreDAO.getCapaciteMax();
        return  capaciteMax;
    }
    //TODO getCapacitémax(date)




}
