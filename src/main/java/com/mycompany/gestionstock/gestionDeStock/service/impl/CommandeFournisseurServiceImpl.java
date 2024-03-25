package com.mycompany.gestionstock.gestionDeStock.service.impl;

import com.mycompany.gestionstock.gestionDeStock.dto.CommandeFournisseurDto;
import com.mycompany.gestionstock.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidEntityException;
import com.mycompany.gestionstock.gestionDeStock.model.Article;
import com.mycompany.gestionstock.gestionDeStock.model.CommandeFournisseur;
import com.mycompany.gestionstock.gestionDeStock.model.LigneCommandeFournisseur;
import com.mycompany.gestionstock.gestionDeStock.repository.ArticleRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.CommandeFournisseurRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.FournisseurRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.LigneCommandeFournisseurRepository;
import com.mycompany.gestionstock.gestionDeStock.service.CommandeFournisseurService;
import com.mycompany.gestionstock.gestionDeStock.validator.CommandeFournisseurValidator;
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
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;


    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, FournisseurRepository fournisseurRepository, ArticleRepository articleRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);

        // vérifier si la commande valide (les champs sont bons)
        if (CommandeFournisseurValidator.validate(dto).isEmpty()) {
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide",
                    ErrorsCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        // vérifier l'existance du fournisseur
        if (fournisseurRepository.findById(dto.getFournisseur().getId()).isEmpty()) {
            log.warn("Fournisseur for this order was not found in the DB");
            throw new EntityNotFoundException("",
                    ErrorsCodes.FOURNISSEUR_NOT_FOUND);
        }

        // vérifier que les lignes de commande (existance des articles dans la bdd)
        List<String> articlesErrors = new ArrayList<>();

        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(lignCmdFour -> {
                if (lignCmdFour.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(lignCmdFour.getArticle().getId());
                    if (article.isEmpty()) {
                        articlesErrors.add("L'article contenant cet ID n'existe pas dans la bdd");
                    }
                } else {
                    articlesErrors.add("Aucun article pour cette ligne de commande");
                }
            });
        }

        if (!articlesErrors.isEmpty()) {
            log.warn("Un ou plusieurs articles n'existent pas dans la BDD");
            throw new InvalidEntityException("Un ou plusieurs articles n'existent pas dans la BDD", ErrorsCodes.ARTICLE_NOT_FOUND, articlesErrors);
        }

        // Associer la commande aux lignes de commande
        CommandeFournisseur savedCmdFour = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        dto.getLigneCommandeFournisseurs().forEach(lignCmdFour -> {
            LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(lignCmdFour);
            ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFour);
            ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        });

        return CommandeFournisseurDto.fromEntity(savedCmdFour);
    }



    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
        log.error("No commande exist in the BDD with this ID");
        return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Aucune commande n'a été trouvé avec cet ID", ErrorsCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("No commande exist in the BDD with this CODE");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande contenante ce code",
                        ErrorsCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }


    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("No commande exist in the BDD with this CODE");
            return;
        }
        commandeFournisseurRepository.deleteById(id);
    }


}
