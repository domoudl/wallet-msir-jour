package m1sir.groupe1.applicationwallet.cucumber.steep;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.services.ClientService;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Cllientstep {
    private int userId;
    private Client returnedClient;
    private List<Client> clientList;
    private ResponseEntity<Client> responseEntity;

    ResponseEntity<List<Client>> response;



    private ClientService clientService;
    public Cllientstep(ClientService clientService) {
        this.clientService = clientService;
    }
    @Given("creer un utilisateur avec comme nom {string} prenom {string} et email {string}")
    public void unUtilisateurAvecIDExiste(String nom,String prenom,String email) {
        Client newClient = new Client();
        newClient.setPrenom(prenom);
        newClient.setNom(nom);
        newClient.setEmail(email);
        this.returnedClient=this.clientService.lireOUCreer(newClient);
        this.userId = userId;
    }

    @When("on recupere le client creer ou lu")
    public void lUtilisateurDemandeClientAvecID() {
        Client cl=clientService.findUserByUserId(returnedClient.getUserID());
        responseEntity =ResponseEntity.ok(cl);

    }

    @Then("le client creer ou lu est retourné avec le code de statut 200")
    public void clientAvecIDEstRetourne() {
        Assert.assertNotNull(this.returnedClient);
        int actualStatusCode = responseEntity.getStatusCodeValue();
        Assert.assertEquals(200, actualStatusCode);
    }
    @Given("creer plusieurs clients")
    public void plusieursClientsExistent() {
        Client client1 = new Client();
        client1.setPrenom("Hayib");
        client1.setNom("Toure");
        client1.setEmail("hayib@gmail.com");
        clientService.lireOUCreer(client1);

        Client client2 = new Client();
        client2.setPrenom("Issa");
        client2.setNom("Ndiaye");
        client2.setEmail("issa@gmail.com");
        clientService.lireOUCreer(client2);
    }

    @When("l'utilisateur demande la liste de tous les clients")
    public void lUtilisateurDemandeListeTousClients() {
        clientList = clientService.getClients();
        response = ResponseEntity.ok(clientList);
    }

    @Then("une liste de clients est retournée avec le code de statut 200")
    public void listeClientsEstRetournee() {
        Assert.assertNotNull(clientList);
        int actualStatusCode = response.getStatusCodeValue();
        Assert.assertEquals(200, actualStatusCode);

    }


}