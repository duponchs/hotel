package fr.projethotel.core;

import fr.projethotel.core.entity.Client;
import fr.projethotel.core.service.ServiceChambre;
import fr.projethotel.core.service.ServiceClient;
import fr.projethotel.core.service.ServiceHotel;
import fr.projethotel.core.service.ServiceMenu;

public class Main {
    public static void main(String... args){
        //MenuService.menu();
       // HibernateUtil.getSessionFactory();
        //ServiceClient serviceClient = new ServiceClient();
        //serviceClient.ajouterClient();
        //serviceClient.miseAJourClient();
        //serviceClient.miseAJourClientChoixDansListClients();
        //serviceClient.rechercheDesReservations();

        ServiceMenu serviceMenu = new ServiceMenu();
        serviceMenu.menu();

        //ServiceHotel serviceHotel = new ServiceHotel();
       // serviceHotel.ajouterHotel();
        //serviceHotel.getBynom();
        //ServiceChambre serviceChambre = new ServiceChambre();
        //serviceChambre.ajouterChambre();
        //System.out.println(serviceChambre.getCapaciteMax());
        //serviceChambre.getChambreDispo();



    }
}
