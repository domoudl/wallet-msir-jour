package m1sir.groupe1.applicationwallet.entite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClientTest {

    @Test
    void testClientConstructor() {
        int userID = 1;
        String nom = "Toure";
        String prenom = "Hayib";
        String email = "hayib@toure.com";
        String motDePasse = "password";

        Client client = new Client(userID, nom, prenom, email, motDePasse);

        assertEquals(userID, client.getUserID());
        assertEquals(nom, client.getNom());
        assertEquals(prenom, client.getPrenom());
        assertEquals(email, client.getEmail());
        assertEquals(motDePasse, client.getMotDePasse());
    }

    @Test
    void testUserIDGetterSetter() {
        int userID = 1;
        Client client = new Client();
        client.setUserID(userID);
        assertEquals(userID, client.getUserID());
    }

    @Test
    void testNomGetterSetter() {
        String nom = "Toure";
        Client client = new Client();
        client.setNom(nom);
        assertEquals(nom, client.getNom());
    }

    @Test
    void testPrenomGetterSetter() {
        String prenom = "Hayib";
        Client client = new Client();
        client.setPrenom(prenom);
        assertEquals(prenom, client.getPrenom());
    }

    @Test
    void testEmailGetterSetter() {
        String email = "hayib@toure.com";
        Client client = new Client();
        client.setEmail(email);
        assertEquals(email, client.getEmail());
    }

    @Test
    void testMotDePasseGetterSetter() {
        String motDePasse = "password";
        Client client = new Client();
        client.setMotDePasse(motDePasse);
        assertEquals(motDePasse, client.getMotDePasse());
    }
}
