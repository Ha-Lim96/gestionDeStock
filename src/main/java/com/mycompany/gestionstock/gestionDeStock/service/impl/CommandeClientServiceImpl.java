package com.mycompany.gestionstock.gestionDeStock.service.impl;

import com.mycompany.gestionstock.gestionDeStock.dto.CommandeClientDto;
import com.mycompany.gestionstock.gestionDeStock.dto.LigneCommandeClientDto;
import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidEntityException;
import com.mycompany.gestionstock.gestionDeStock.model.Article;
import com.mycompany.gestionstock.gestionDeStock.model.CommandeClient;
import com.mycompany.gestionstock.gestionDeStock.model.LigneCommandeClient;
import com.mycompany.gestionstock.gestionDeStock.repository.ArticleRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.ClientRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.CommandeClientRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.LigneCommandeClientRepository;
import com.mycompany.gestionstock.gestionDeStock.service.CommandeClientService;
import com.mycompany.gestionstock.gestionDeStock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {


    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CommandeClientServiceImpl(
            CommandeClientRepository commandeClientRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository,
            ClientRepository clientRepository,
            ArticleRepository articleRepository)
    {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);

        // vérifier que la commande est valide (les champs sont bons)
        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide",
                    ErrorsCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }


        // vérifier l'existance du client
        if (clientRepository.findById(dto.getClient().getId()).isEmpty()) {
            log.warn("Client for this order was not found in the DB");
            throw new EntityNotFoundException("Client for this order was not found in the DB",
                    ErrorsCodes.CLIENT_NOT_FOUND);
        }


        // vérifier les lignes de commandes (l'existance des articles dans la bdd)
        List<String>  articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(lignCmdClt -> {
                if (lignCmdClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(lignCmdClt.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article contenant cet ID n'existe pas");
                    }
                } else {
                    articleErrors.add("Aucun article pour cette ligne de commande");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("Un ou plusieurs articles n'existent pas dans la BDD");
            throw new InvalidEntityException("Un ou plusieurs articles n'existent pas dans la BDD", ErrorsCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        // Associer la commande aux lignes de commandes
        CommandeClient savedCmdtClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        dto.getLigneCommandeClients().forEach(ligCmdClt -> {
            LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
            ligneCommandeClient.setCommandeClient(savedCmdtClt);
            ligneCommandeClientRepository.save(ligneCommandeClient);
        });

        return CommandeClientDto.fromEntity(savedCmdtClt);
    }


    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client ID is null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande n'a été trouvé avec cet ID", ErrorsCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande client CODE is NULL");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande contenante ce code",ErrorsCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client ID is null");
            return;
        }
        commandeClientRepository.deleteById(id);
    }

}
