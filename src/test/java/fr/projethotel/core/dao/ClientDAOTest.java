package fr.projethotel.core.dao;

import fr.projethotel.core.entity.Client;
import fr.projethotel.core.service.ServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClientDAOTest{
    ClientDAO clientDAO = mock(ClientDAO.class);;
    Client client;
    @BeforeEach
    void init(){
         this.clientDAO = new ClientDAO();

    }
    @Test
    void creationDuCLientTrueSiCree(){
       //when(clientDAO.getById(any(Client.class),anyInt())
    }

}
