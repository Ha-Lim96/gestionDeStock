package com.mycompany.gestionstock.gestionDeStock.repository;


import com.mycompany.gestionstock.gestionDeStock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

}
