package m1sir.groupe1.applicationwallet.service;


import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.repository.CompteRepository;
import m1sir.groupe1.applicationwallet.services.ClientService;
import m1sir.groupe1.applicationwallet.services.CompteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompteServiceTest {


    @Mock
    private CompteRepository compteRepository;

    @Mock
    private ClientService clientService;

    private CompteService compteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        compteService = new CompteService(compteRepository, clientService);
    }

    @Test
    public void testCreerCompte() {

        Compte compte = new Compte();
        compte.setAccountID(1);
        compte.setSolde(1000.0);
        compte.setTypeDeCompte("Compte courant");


        Client client = new Client();
        client.setUserID(1);


        when(clientService.lireOUCreer(client)).thenReturn(client);


        compteService.creer(compte);


        verify(compteRepository, times(1)).save(compte);
    }

    @Test
    public void testLireCompte() {

        Compte compte = new Compte();
        compte.setAccountID(1);
        compte.setSolde(1000.0);
        compte.setTypeDeCompte("Compte courant");


        when(compteRepository.findById(1)).thenReturn(java.util.Optional.of(compte));


        Compte result = compteService.lire(1);


        assertEquals(compte, result);
    }
    @Test
    public void testSauvegarderCompte() {

        Compte compte = new Compte();
        compte.setAccountID(1);
        compte.setSolde(1000.0);
        compte.setTypeDeCompte("Compte courant");

        compteService.sauvegarder(compte);

        verify(compteRepository, times(1)).save(compte);
    }
    @Test
    public void testLireComptes() {

        Compte compte1 = new Compte();
        compte1.setAccountID(1);
        compte1.setSolde(1000.0);
        compte1.setTypeDeCompte("Compte courant");

        Compte compte2 = new Compte();
        compte2.setAccountID(2);
        compte2.setSolde(2000.0);
        compte2.setTypeDeCompte("Compte épargne");

        when(compteRepository.findAll()).thenReturn(List.of(compte1, compte2));

        Iterable<Compte> result = compteService.lireComptes();

        List<Compte> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(2, resultList.size());
        assertEquals(compte1, resultList.get(0));
        assertEquals(compte2, resultList.get(1));
    }
    @Test
    public void testLireCompteQuandNonTrouve() {

        int idNonExistant = 999;

        when(compteRepository.findById(idNonExistant)).thenReturn(Optional.empty());

        Compte result = compteService.lire(idNonExistant);


        assertNull(result);
    }
}