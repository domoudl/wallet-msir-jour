package m1sir.groupe1.applicationwallet.services;

import m1sir.groupe1.applicationwallet.entite.Client;
import m1sir.groupe1.applicationwallet.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client lireOUCreer(Client clients) {

        Client clientdanslabasededonnee = this.clientRepository.findByEmail(clients.getEmail());

        if ( clientdanslabasededonnee== null) {
            clientdanslabasededonnee = this.clientRepository.save(clients);
        }

        return clientdanslabasededonnee;

    }
    public Client  findUserByUserId(int userID){
        return clientRepository.findByUserID(userID);
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }


    public Client findByEmail(String email) {
        return  this.clientRepository.findByEmail(email);
    }
}
