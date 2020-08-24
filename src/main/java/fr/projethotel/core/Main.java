package fr.projethotel.core;

import fr.projethotel.core.service.ServiceClient;

public class Main {
    public static void main(String... args){
        //MenuService.menu();
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.ajouterClient();
    }
}
