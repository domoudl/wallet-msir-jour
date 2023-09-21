package m1sir.groupe1.applicationwallet.cucumber.steep;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.repository.CompteRepository;
import m1sir.groupe1.applicationwallet.services.TransactionService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionStep {

    private double sourceBalance;
    private Compte sourceAccount;
    private Compte destinationAccount;
    private double destinationBalance;
    private String transferResult;


    private TransactionService transactionService;
    private CompteRepository compteRepository;


    public TransactionStep(TransactionService transactionService, CompteRepository compteRepository) {
        this.transactionService = transactionService;
        this.compteRepository = compteRepository;
    }


    @Given("a source account with balance {double}")
    public void aSourceAccountWithBalance(double balance) {
        sourceBalance = balance;
       sourceAccount = new Compte();
        sourceAccount.setSolde(sourceBalance);
        compteRepository.save(sourceAccount);


    }

    @Given("a destination account")
    public void aDestinationAccount() {
        destinationBalance = 0.0;
        destinationAccount = new Compte();
        destinationAccount.setSolde(destinationBalance);
       compteRepository.save(destinationAccount);

    }

    @When("I transfer {double} from source account to destination account")
    public void iTransferFromSourceAccountToDestinationAccount(double amount) {
        transferResult = transactionService.transfert(sourceAccount.getAccountID(),destinationAccount.getAccountID(),amount);
        if(Objects.equals(transferResult, "Transaction reussie")){
            sourceBalance -= amount;
            destinationBalance += amount;
        }
    }

    @Then("the transfer should be successful")
    public void theTransferShouldBeSuccessful() {
        assertEquals("Transaction reussie", transferResult);
    }

    @And("the source account balance should be {double}")
    public void theSourceAccountBalanceShouldBe(double balance) {
        assertEquals(balance, sourceBalance);
    }

    @And("the destination account balance should be {double}")
    public void theDestinationAccountBalanceShouldBe(double balance) {
        assertEquals(balance, destinationBalance);
    }

    @Then("the transfer should fail with {string}")
    public void theTransferShouldFailWith(String errorMessage) {
        assertEquals(errorMessage, transferResult);
    }


    @And("the source account balance should remain {double}")
    public void theSourceAccountBalanceShouldRemain(double balance) {
        assertEquals(balance, sourceBalance);
    }

    @And("the destination account balance should remain {double}")
    public void theDestinationAccountBalanceShouldRemain(double balance) {
        assertEquals(balance, destinationBalance);
    }
}
