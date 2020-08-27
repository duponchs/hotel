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

        System.out.println("--------------------------------- Liste des Chambres  non Archiver  ---------------------------------------------------");
        for (Chambre chambre:chambresNotArchived) {

            System.out.println(chambre.getNumero() );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        return chambresNotArchived;

    }

    public List<Chambre> voirChambreArchiver(){
        List<Chambre> chambresArchived = chambreDAO.getChambreArchived();

        System.out.println("--------------------------------- Liste des Chambres Archiver  ---------------------------------------------------");
        for (Chambre chambre:chambresArchived) {

            System.out.println(chambre.getNumero() );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        return chambresArchived;

    }

    public Chambre trouverParNumero(){
        Chambre chambre = null;
        Scanner clavier = new Scanner(System.in);
        System.out.println("Quel est le numero de chambre rechercher?");
        Integer numero = clavier.nextInt();
        chambre = chambreDAO.findChambreByNumero(numero);

        return chambre;
    }

    public void archiverChambre(){
        Scanner clavier = new Scanner(System.in);

        System.out.println("Quel est le client à archiver ?");
        System.out.println("Id ? :");
        Integer numero = clavier.nextInt();
        Chambre chambre = chambreDAO.findChambreByNumero(numero);
        chambreDAO.setTrueStatusArchiver(chambre);
    }
    public void DesarchiverChambre(){
        Scanner clavier = new Scanner(System.in);

        System.out.println("Quel est le chambre à désarchiver ?");
        System.out.println("Veuillez numero de la chambre ? :");
        Integer numero = clavier.nextInt();
        Chambre chambre = chambreDAO.findChambreByNumero(numero);
        chambreDAO.setFalseStatusArchiver(chambre);
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
        List<Chambre> chambresDispo = null;

        Scanner clavier = new Scanner(System.in);
        System.out.println("Veuillez confirmez la date que vous voulez consulter");
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
