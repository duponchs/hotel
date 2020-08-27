package fr.projethotel.core.service;

import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.dao.ChambreDAO;
import fr.projethotel.core.dao.HotelDAO;
import fr.projethotel.core.entity.Chambre;
import fr.projethotel.core.entity.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ServiceChambre {

    static final Logger logger = LogManager.getLogger("ServiceChambre");

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

        Hotel hotel = hotelDAO.getById(Utilitaire.idHotel);

        chambre.setHotel(hotel);
        hotel.getChambres().add(chambre);
        chambreDAO.create(chambre);
        hotelDAO.update(hotel);

    }

    public List<Chambre> voirChambreNonArchiver(){
        List<Chambre> chambresNotArchived = chambreDAO.getChambreNotArchived();

        System.out.println("--------------------------------- Liste des chambres disponible ---------------------------------------------------");
        for (Chambre chambre:chambresNotArchived) {

            System.out.println(chambre.getNumero() );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        return chambresNotArchived;

    }

    public List<Chambre> voirChambreArchiver(){
        List<Chambre> chambresArchived = null;
        //TODO
        return chambresArchived;

    }

    public void archiverChambre(){
        //TODO

    }

    public  void deArchiverChambre(){
        //TODO

    }

    public  void supprimerChambre(){
        //TODO
    }

    public Long getCapaciteMax(){

        Long capaciteMax = chambreDAO.getCapaciteMax();
        return  capaciteMax;
    }



    public List<Chambre> getChambreDispo(){
        LocalDate date = null;
        List <Chambre> chambresDispo = null;

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez reiseigner la date que vous voulez consulter");
        String dateString = clavier.nextLine();
        if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
             date = LocalDate.of(Integer.valueOf(dateString.substring(6, 10)),
                    Integer.valueOf(dateString.substring(3, 5)),
                    Integer.valueOf(dateString.substring(0, 2)));
            System.out.println(date);
             chambresDispo = chambreDAO.getChambreDispoAtDay(date);

        } else {
            logger.fatal("la date n'est pas dans le bon format ");
        }

        System.out.println("--------------------------------- Liste des chambres disponible ---------------------------------------------------");
        for (Chambre chambre:chambresDispo) {

            System.out.println(chambre.getNumero() );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        return chambresDispo;
    }




}
