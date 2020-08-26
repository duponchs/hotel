package fr.projethotel.core;

import fr.projethotel.core.entity.Client;
import fr.projethotel.core.service.ServiceChambre;
import fr.projethotel.core.service.ServiceClient;
import fr.projethotel.core.service.ServiceHotel;

public class Main {
    public static void main(String... args){
        //MenuService.menu();

        //ServiceClient serviceClient = new ServiceClient();
        //serviceClient.ajouterClient();
        //serviceClient.miseAJourClient();
        //serviceClient.miseAJourClientChoixDansListClients();



       ServiceHotel serviceHotel = new ServiceHotel();
       //serviceHotel.ajouterHotel();
        serviceHotel.getBynom();
        ServiceChambre serviceChambre = new ServiceChambre();
        serviceChambre.ajouterChambre();
    }
}
