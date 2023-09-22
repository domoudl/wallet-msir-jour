package m1sir.groupe1.applicationwallet.controller;

import m1sir.groupe1.applicationwallet.controller.ClientController;
import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.request.LoginRequest;
import m1sir.groupe1.applicationwallet.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientControllerTest {
    @Mock
    ClientService clientService;

    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientService);
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "assane", "doum", "azou@gmail.com", "motdepasse1"));
        clients.add(new Client(2, "abdou", "fall", "abdou@gmail.com", "motdepasse1"));
        when(clientService.getClients()).thenReturn(clients);
        List<Client> client = clientService.getClients();

        ResponseEntity<List<Client>> response = ResponseEntity.ok(client);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    void testGetClientById() {

        int clientId = 1;
        Client mockClient = new Client(clientId, "azou", "doum", "azou@gmail.com", "motdepasse1");
        when(clientService.findUserByUserId(clientId)).thenReturn(mockClient);
        Client cl=clientService.findUserByUserId(clientId);
        ResponseEntity.ok(cl);
        ResponseEntity<Client> response = ResponseEntity.ok(cl);;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClient, response.getBody());
    }
    @Test
    void testGetClientByUserID() {

        int userId = 1;
        Client mockClient = new Client(userId, "assane", "Doumbouya", "assane@gmail.com", "azou");


        when(clientService.findUserByUserId(userId)).thenReturn(mockClient);


        Client resultClient = clientController.getClientByUserID(userId);

        assertEquals(mockClient, resultClient);
    }

    @Test
    void testAllClients() {
        // Créer une liste fictive de clients pour le test
        List<Client> mockClients = new ArrayList<>();
        mockClients.add(new Client(1, "toure", "hayib", "hayib@gmail.com", "toure"));
        mockClients.add(new Client(2, "ndiaye", "issa", "issa@gmail.com", "issa"));

        when(clientService.getClients()).thenReturn(mockClients);


        List<Client> resultClients = clientController.getAllClients();


        assertEquals(mockClients, resultClients);
    }
    @Test
    void testLoginSuccess() {
        String email = "azou@gmail.com";
        String motDePasse = "motdepasse1";
        Client mockClient = new Client(1, "assane", "doum", email, motDePasse);
        when(clientService.findByEmail(email)).thenReturn(mockClient);

        LoginRequest loginRequest = new LoginRequest(email, motDePasse);
        ResponseEntity<String> response = clientController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Authentification réussie", response.getBody());
    }

    @Test
    void testLoginFailure() {
        String email = "azou@gmail.com";
        String motDePasse = "motdepasse1";
        when(clientService.findByEmail(email)).thenReturn(null);

        LoginRequest loginRequest = new LoginRequest(email, motDePasse);
        ResponseEntity<String> response = clientController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Authentification échouée", response.getBody());
    }

    @Test
    public void testLoginInvalidPassword() {
        String email = "azou@gmail.com";
        String motDePasse = "motdepasse1";
        Client mockClient = new Client(1, "assane", "doum", email, motDePasse);
        when(clientService.findByEmail(email)).thenReturn(mockClient);

        LoginRequest loginRequest = new LoginRequest(email, "wrong_password");
        ResponseEntity<String> response = clientController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Authentification échouée", response.getBody());
    }
}




