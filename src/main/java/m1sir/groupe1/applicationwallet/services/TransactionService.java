package m1sir.groupe1.applicationwallet.services;

import m1sir.groupe1.applicationwallet.entite.Compte;
import m1sir.groupe1.applicationwallet.entite.Transaction;
import m1sir.groupe1.applicationwallet.enums.Statut;
import m1sir.groupe1.applicationwallet.repository.CompteRepository;
import m1sir.groupe1.applicationwallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private CompteRepository compteRepository;

    public TransactionService(TransactionRepository transactionRepository,CompteRepository compteRepository) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
    }

    @Transactional
    public String transfert(int idCompteSource, int idCompteDestination, double montant) {

        String response = "Transaction reussie";
        Compte compteSource = compteRepository.findById(idCompteSource)
                .orElse(null);

        Compte compteDestination = compteRepository.findById(idCompteDestination)
                .orElse(null);

        if(compteSource != null && compteDestination != null) {
            if (compteSource.getSolde() >= montant) {
                compteSource.setSolde(compteSource.getSolde() - montant);


                compteDestination.setSolde(compteDestination.getSolde() + montant);
                compteRepository.save(compteSource);
                compteRepository.save(compteDestination);

                Transaction transaction = new Transaction();
                transaction.setCompteSource(compteSource);
                transaction.setCompteDestination(compteDestination);
                transaction.setMontant(montant);
                transaction.setDateHeure(new Date());
                transaction.setStatut(Statut.SUCCEED);

                transactionRepository.save(transaction);

            }else {
                response = "Solde Insuffisant";
            }
        }else {
            response = "Compte not found";
        }
        return response;
    }


    public String annulerTransfert(int transactionId) {
        String response = "Transfer cancelled successfully";
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElse(null);
        if (transaction != null) {
            if (transaction.getStatut() == Statut.CANCEL) {
                response = "Transaction Already Cancelled";
            }

            Compte compteSource = transaction.getCompteSource();
            Compte compteDestination = transaction.getCompteDestination();
            double montant = transaction.getMontant();

            compteSource.setSolde(compteSource.getSolde() + montant);
            compteDestination.setSolde(compteDestination.getSolde() - montant);
            compteRepository.save(compteSource);
            compteRepository.save(compteDestination);

            transaction.setStatut(Statut.CANCEL);
            transactionRepository.save(transaction);
        }else {
            response = "Transaction not found";
        }
        return response;
    }

    public Transaction lire(int id) {
        Optional<Transaction> optionalTransaction= this.transactionRepository.findById(id);
        return optionalTransaction.orElse(null);
    }

    public Iterable<Transaction> lireTransaction() {
        return transactionRepository.findAll();
    }



}
