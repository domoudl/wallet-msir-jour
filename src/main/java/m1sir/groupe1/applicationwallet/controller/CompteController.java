package m1sir.groupe1.applicationwallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Compte")
public class CompteController {
    @Autowired
    private CompteService compteService;
    private String erreur = "erreur";

    @Operation(
            description = "Creates an account and a customer at the same time if it does not exist",
            summary = "Create an account"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Compte> creer(@RequestBody Compte compte){
        return this.compteService.creer(compte);
    }
    @Operation(
            description = "Gives the amount of an account from Id",
            summary = "Gives the amount of an account"
    )
    @GetMapping(path = "compte/solde/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Double> consultationSolde(@PathVariable int id) {
        Compte compte = this.compteService.lire(id);

        Map<String, Double> response = new HashMap<>();
        response.put("solde", compte.getSolde());

        return response;
    }

    @Operation(
            description = "Deposit into an account from its Id",
            summary = "Deposit into an account"
    )
    @PutMapping("compte/depot/{id}")
    public ResponseEntity<Map<String, Object>> depot(@PathVariable int id, @RequestBody Compte nouveauCompte) {
        Compte compte = this.compteService.lire(id);
        Double ancienSolde = compte.getSolde();
        Double soldeDepot = nouveauCompte.getSolde();

        compte.setSolde(ancienSolde + soldeDepot);

        Map<String, Object> response = new HashMap<>();
        if(soldeDepot <= 0) {
            response.put(erreur, "Solde dépot négatif ou nul");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            compteService.sauvegarder(compte);
            response.put("nouveau_solde", compte.getSolde());
            return ResponseEntity.ok(response);
        }

    }

    @Operation(
            description = "withdrawal from an account from its Id",
            summary = "withdrawal from an account"
    )
    @PutMapping("compte/retrait/{id}")
    public ResponseEntity<Map<String, Object>> retrait(@PathVariable int id, @RequestBody Compte nouveauCompte) {
        Compte compte = this.compteService.lire(id);
        Double ancienSolde = compte.getSolde();
        Double soldeRetrait = nouveauCompte.getSolde();
        Map<String, Object> response = new HashMap<>();
        if(soldeRetrait <= 0) {
            response.put(erreur, "Solde retrait négatif ou nul");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else if(ancienSolde >= soldeRetrait) {
            compte.setSolde(ancienSolde - soldeRetrait);
            compteService.sauvegarder(compte);
            response.put("nouveau_solde", compte.getSolde());
            return ResponseEntity.ok(response);
        }
        else
        {
            response.put(erreur, "Solde Insuffisant");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            description = "It gives account by Id",
            summary = "It gives account by Id"
    )
        @GetMapping("compte/{id}")
    public Compte lireCompte(@PathVariable("id") final int id) {
        return compteService.lire(id);
    }

    @Operation(
            description = "It gives all account",
            summary = "It gives all account"
    )
    @GetMapping("comptes")
    public Iterable<Compte> lireComptes() {
        return compteService.lireComptes();
    }
}
