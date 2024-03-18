package com.mycompany.gestionstock.gestionDeStock.repository;


import com.mycompany.gestionstock.gestionDeStock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
