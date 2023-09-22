package m1sir.groupe1.applicationwallet.entite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CompteTest {

    @Test
    void testCompteConstructor() {
        int accountID = 1;
        Client client = new Client();
        double solde = 1000.0;
        String typeDeCompte = "Épargne";

        Compte compte = new Compte(accountID, client, solde, typeDeCompte);

        assertEquals(accountID, compte.getAccountID());
        assertEquals(client, compte.getClient());
        assertEquals(solde, compte.getSolde());
        assertEquals(typeDeCompte, compte.getTypeDeCompte());
    }

    @Test
    void testAccountIDGetterSetter() {
        int accountID = 1;
        Compte compte = new Compte();
        compte.setAccountID(accountID);
        assertEquals(accountID, compte.getAccountID());
    }

    @Test
    void testClientGetterSetter() {
        Client client = new Client();
        Compte compte = new Compte();
        compte.setClient(client);
        assertEquals(client, compte.getClient());
    }

    @Test
    void testSoldeGetterSetter() {
        double solde = 1000.0;
        Compte compte = new Compte();
        compte.setSolde(solde);
        assertEquals(solde, compte.getSolde(), 0.01);
    }

    @Test
    void testTypeDeCompteGetterSetter() {
        String typeDeCompte = "Épargne";
        Compte compte = new Compte();
        compte.setTypeDeCompte(typeDeCompte);
        assertEquals(typeDeCompte, compte.getTypeDeCompte());
    }
}
