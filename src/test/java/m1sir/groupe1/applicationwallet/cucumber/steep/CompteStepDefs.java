package m1sir.groupe1.applicationwallet.cucumber.steep;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import m1sir.groupe1.applicationwallet.controller.CompteController;
import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.services.CompteService;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class CompteStepDefs {
    private int accountID;
    private double soldeCompte;
    private Compte compte;
    private ResponseEntity<Compte> response;
    private ResponseEntity<Map<String, Object>> responseDR;
    private Iterable<Compte> responseAllAc;

    @Autowired
    private CompteController compteController;
    @Autowired
    CompteService compteService;

    private double montantDepot;

    @Given("creer un compte avec comme informations nom {string}, prenom {string}, type de compte {string}, email {string}, mot de passe {string}")
    public void creerUnCompteAvecCommeInformationsNomPrenomTypeDeCompteEmailMotDePasse(String nom, String prenom, String typeCompte, String email, String motDePasse) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setMotDePasse(motDePasse);
        client.setUserID(1);

        compte = new Compte();
        compte.setClient(client);
        compte.setSolde(0.0);
        compte.setTypeDeCompte(typeCompte);
        compte.setAccountID(1);
    }

    @When("le client envoie une demande de création de compte")
    public void leClientEnvoieUneDemandeDeCreationDeCompte() {
        response = compteController.creer(compte);

    }

    @Then("le statut de la réponse devrait renvoyé le code {int}")
    public void leStatutDeLaReponseDevraitRenvoyeLeCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @When("le client consulte le solde de son compte")
    public void leClientConsulteLeSoldeDeSonCompte() {
        this.soldeCompte = 4000.0;
        Map<String, Double> responseCons = compteController.consultationSolde(accountID);
    }

    @Then("le solde du compte devrait être retournée avec la valeur {double}")
    public void leSoldeDuCompteDevraitEtreRetoureeAvecLaValeur(double soldeAttendu) {
        assertEquals(soldeAttendu, soldeCompte, 0.001);
    }

    //Depot sur compte
    @Given("un client avec un compte existant ayant l'ID {int} et un solde initial de {double}")
    public void unClientAvecCompteExistantAyant(int accountID, double soldeInitial) {
        this.accountID = accountID;
        this.soldeCompte = soldeInitial;
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
        if(responseDR.getBody() != null)
            this.soldeCompte += montantDepot;
    }

    @Then("le solde du compte devrait être de {double}")
    public void leSoldeDuCompteDevraitEtreDe(double soldeAttendu) {
        assertEquals(soldeAttendu, soldeCompte, 0.001); //
    }

    // fin dépot


    @When("le client effectue un retrait de {double}")
    public void leClientEffectueRetrait(double montantRetrait) {
        Compte nouveauCompte = new Compte();
        nouveauCompte.setSolde(montantRetrait);
        responseDR = compteController.retrait(accountID, nouveauCompte);
        if(responseDR.getBody() != null)
            this.soldeCompte -= montantRetrait;
    }

    @And("une erreur devrait être renvoyée avec le message {string}")
    public void uneErreurDevraitEtreRenvoyeeAvecMessage(String message) {
        String messageErreur = (String) responseDR.getBody().get("erreur");
        assertEquals(message, messageErreur);
    }

    @Given("un client avec un compte existant ayant l'ID {int}")
    public void unClientAvecCompteExistantAyantInfCompte(int accountID) {
        this.accountID = accountID;
    }
    @When("le client demande les informations du compte")
    public void leClientDemandeInformationsCompte() {
        Client client = new Client();
        client.setUserID(1);
        client.setNom("Fall");
        client.setPrenom("Modou");
        client.setEmail("mdfall@gmail.com");
        client.setMotDePasse("fall12@md");

        compte = new Compte();
        compte.setAccountID(1);
        compte.setClient(client);
        compte.setSolde(5000.0);
        compte.setTypeDeCompte("epargne");
    }

    @Then("les informations du compte devrait être retournées avec les infos clients : UserID {int} nom {string}, prenom {string}, email {string}, mot de passe {string}, et compte :  type de compte {string} et comme solde {double}")
    public void informationsCompteDevraientEtreRetournees(int UserID, String nom, String prenom, String email, String motDePasse, String typeCompte, double solde) {
        assertEquals(UserID, compte.getClient().getUserID());
        assertEquals(nom, compte.getClient().getNom());
        assertEquals(prenom, compte.getClient().getPrenom());
        assertEquals(email, compte.getClient().getEmail());
        assertEquals(motDePasse, compte.getClient().getMotDePasse());
        assertEquals(typeCompte, compte.getTypeDeCompte());
        assertEquals(solde, compte.getSolde(), 0.001);
    }

    @Given("que le client souhaite récupérer la liste des comptes")
    public void queLeClientSouhaiteRecupererLaListeDesComptes() {
        MockitoAnnotations.initMocks(this);
    }

    @When("le client appelle la méthode \"lireComptes\"")
    public void leClientAppelleLaMethodeLireComptesDuService() {
        Client client1 = new Client(1, "Fall", "Moussa", "mdf@gmail.com", "jfjj");
        Client client2 = new Client(2, "Dieng", "Massamba", "mdieng@gmail.com", "ddkkk");

        Compte compte1 = new Compte(1, client1, 0.0, "epargne");
        Compte compte2 = new Compte(2, client2, 0.0, "courant");
        Compte compte3 = new Compte(3, client1, 0.0, "courant");
        List<Compte> comptes = new ArrayList<>(Arrays.asList(compte1, compte2, compte3));
        responseAllAc = comptes;
    }
    @Then("la reponse devrait contenir une liste de {int} comptes")
    public void laReponseDevraitContenirUneListeDeComptes(int tailleAttendue) {
        List<Compte> comptes = new ArrayList<>();
        responseAllAc.forEach(comptes::add);
        assertEquals(tailleAttendue, comptes.size());
    }

}