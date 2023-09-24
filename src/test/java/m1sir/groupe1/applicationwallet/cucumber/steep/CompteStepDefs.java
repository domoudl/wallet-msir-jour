package m1sir.groupe1.applicationwallet.cucumber.steep;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import m1sir.groupe1.applicationwallet.controller.CompteController;
import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class CompteStepDefs {
    int accountID;
    private Compte compte;
    private ResponseEntity<Compte> response;
    private Map<String, Double> responseCons;
    private ResponseEntity<Map<String, Object>> responseDR;

    private ResponseEntity<List<Compte>> listResponse;
    private List<Compte> comptes;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteController compteController;
    private double montantDepot;
    private double montantRetrait;

    @Given("creer un compte avec comme informations nom {string}, prenom {string}, type de compte {string}, email {string}, mot de passe {string}")
    public void creerUnCompteAvecCommeInformationsNomPrenomTypeDeCompteEmailMotDePasse(String nom, String prenom, String typeCompte, String email, String motDePasse) {
        compte = new Compte();
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setMotDePasse(motDePasse);

        compte.setClient(client);
        compte.setSolde(0.0);
        compte.setTypeDeCompte(typeCompte);
    }

    @When("le client envoie une demande de création de compte")
    public void leClientEnvoieUneDemandeDeCreationDeCompte() {
        response = compteController.creer(compte);
    }

    @Then("le statut de la réponse devrait renvoyé le code {int}")
    public void leStatutDeLaReponseDevraitRenvoyeLeCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Given("Un client nous fournit son numero de compte avec l'ID {int}")
    public void unClientAvecCompteExistant(int accountID) {
        this.accountID = accountID;
    }

    @When("le client consulte le solde de son compte")
    public void leClientConsulteLeSoldeDeSonCompte() {
        responseCons = compteController.consultationSolde(accountID);
    }

    @Then("le solde du compte devrait être retournée avec la valeur {double}")
    public void leSoldeDuCompteDevraitEtreRetoureeAvecLaValeur(double soldeAttendu) {
        double soldeActuel = responseCons.get("solde");
        assertEquals(soldeAttendu, soldeActuel, 0.001);
    }

    //Depot sur compte
    @Given("un client avec un compte existant ayant l'ID {int}")
    public void unClientAvecCompteExistantAyant(int accountID) {
        this.accountID = accountID;
        compte = compteRepository.findById(accountID).orElse(null);
    }

    @And("le montant à déposer est de {double}")
    public void leMontantADeposerEst(double montantDepot) {
        this.montantDepot = montantDepot;
    }

    @When("le client effectue le dépôt")
    public void leClientEffectueDepot() {
        Compte nouveauCompte = new Compte();
        nouveauCompte.setSolde(montantDepot);
        responseDR = compteController.depot(accountID, nouveauCompte);
    }

    @Then("le solde du compte devrait être de {double}")
    public void leSoldeDuCompteDevraitEtreDe(double soldeAttendu) {
        Map<String, Object> responseBody = responseDR.getBody();
        double soldeActuel = compteRepository.findById(accountID).orElse(null).getSolde();
        assertEquals(soldeAttendu, soldeActuel, 0.001); // U
    }
    // fin dépot

    @Given("un client avec un compte existant ayant l'ID {int}")
    public void unClientAvecCompteExistantRetrait(int accountID) {
        this.accountID = accountID;
    }
    @When("le client effectue un retrait de {double}")
    public void leClientEffectueRetrait(double montantRetrait) {
        this.montantRetrait = montantRetrait;
        Compte nouveauCompte = new Compte();
        nouveauCompte.setSolde(montantRetrait);
        responseDR = compteController.retrait(accountID, nouveauCompte);
    }
    @Then("le solde du compte devrait être de {double}")
    public void leSoldeDuCompteDevraitEtre(double soldeAttendu) {
        double soldeActuel = (Double) responseDR.getBody().get("nouveau_solde");
        assertEquals(soldeAttendu, soldeActuel, 0.001);
    }
    @And("une erreur devrait être renvoyée avec le message {string}")
    public void uneErreurDevraitEtreRenvoyeeAvecMessage(String message) {
        String messageErreur = (String) responseDR.getBody().get("erreur");
        assertEquals(message, messageErreur);
    }

    @Given("un client a un compte existant avec l'ID {int}")
    public void unClientAvecCompteExistantConsCompte(int accountID) {
        this.accountID = accountID;
    }
    @When("le client demande les informations du compte avec l'ID {int}")
    public void leClientDemandeInformationsCompte(int accountID) {
        compte = compteController.lireCompte(accountID);
    }
    @Then("les informations du compte devraient être retournées avec l'ID client {int}, type de compte {string}, et comme solde {double}")
    public void informationsCompteDevraientEtreRetournees(Client client, String typeCompte, double solde) {
        assertEquals(client, compte.getClient());
        assertEquals(typeCompte, compte.getTypeDeCompte());
        assertEquals(solde, compte.getSolde(), 0.001);
    }


}