package m1sir.groupe1.applicationwallet.request;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginRequestTest {

    @Test
    void testLoginRequestConstructor() {
        String email = "hayib@toure.com";
        String motDePasse = "password";

        LoginRequest loginRequest = new LoginRequest(email, motDePasse);

        assertEquals(email, loginRequest.getEmail());
        assertEquals(motDePasse, loginRequest.getMotDePasse());
    }

    @Test
    void testEmailGetterSetter() {
        String email = "hayib@toure.com";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        assertEquals(email, loginRequest.getEmail());
    }

    @Test
    void testMotDePasseGetterSetter() {
        String motDePasse = "password";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMotDePasse(motDePasse);
        assertEquals(motDePasse, loginRequest.getMotDePasse());
    }
}
