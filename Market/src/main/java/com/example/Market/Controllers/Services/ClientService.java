package com.example.Market.Controllers.Services;

import com.example.Market.Model.Client;
import com.example.Market.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClientService {
    @Autowired
    private ClientRepository rep;

    public List<Client> getAllClients(){
        return rep.findAll();
    }
    public Client getClientById(long id){
        if(rep.findById(id).isPresent()){
            return rep.findById(id).get();
        }
        return null;
    }
    public int deleteById(long id) {
        if(rep.findById(id).isPresent()){
            rep.deleteById(id);
            return 1;
        }
        return 0;
    }
    public void save(Client client){
        rep.save(client);
    }
    public Client findByMailAndPass(String email,String pwd){
        List<Client> clients = rep.findAll();
        for(Client client:clients){
            if(client.getMail().equals(email) && client.getPassword().equals(pwd)){
                return client;
            }
        }
        return null;
    }

}
