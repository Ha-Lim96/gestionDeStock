package com.mycompany.gestionstock.gestionDeStock.service.impl;

import com.mycompany.gestionstock.gestionDeStock.dto.ClientDto;
import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidEntityException;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidOperationException;
import com.mycompany.gestionstock.gestionDeStock.model.CommandeClient;
import com.mycompany.gestionstock.gestionDeStock.repository.ClientRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.CommandeClientRepository;
import com.mycompany.gestionstock.gestionDeStock.service.ClientService;
import com.mycompany.gestionstock.gestionDeStock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorsCodes.CLIENT_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(
                clientRepository.save(ClientDto.toEntity(dto))
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec cet ID n'a été trouvé",
                        ErrorsCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
       return clientRepository.findAll().stream()
               .map(ClientDto::fromEntity)
               .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un client ayant une commande existante",
                    ErrorsCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }
}
