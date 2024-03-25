package com.mycompany.gestionstock.gestionDeStock.service.impl;

import com.mycompany.gestionstock.gestionDeStock.dto.LigneVenteDto;
import com.mycompany.gestionstock.gestionDeStock.dto.VentesDto;
import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidEntityException;
import com.mycompany.gestionstock.gestionDeStock.model.Article;
import com.mycompany.gestionstock.gestionDeStock.model.LigneVente;
import com.mycompany.gestionstock.gestionDeStock.model.Ventes;
import com.mycompany.gestionstock.gestionDeStock.repository.ArticleRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.LigneVenteRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.VentesRepository;
import com.mycompany.gestionstock.gestionDeStock.service.VentesService;
import com.mycompany.gestionstock.gestionDeStock.validator.VentesValidator;
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
public class VentesServiceImpl implements VentesService {

    private VentesRepository ventesRepository;
    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;


    @Autowired
    public VentesServiceImpl(VentesRepository ventesRepository, ArticleRepository articleRepository, LigneVenteRepository ligneVenteRepository) {
        this.ventesRepository = ventesRepository;
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    @Override
    public VentesDto save(VentesDto dto) {

        // Vérifier la validité de l'objet vente (les champs sont bons)
        List<String> errors = VentesValidator.validate(dto);
        if (! errors.isEmpty()) {
            log.error("La vente n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorsCodes.VENTE_NOT_VALID, errors);
        }

        // Vérifier l'existance des articles dans la bdd
        List<String> articleErrors = new ArrayList<>();
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErrors.add("Aucun article existant avec cet ID");
            }
        });
        if (!articleErrors.isEmpty()) {
            log.error("One or more articles were not found in the database", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas été trouvé dans la bdd",
                    ErrorsCodes.VENTE_NOT_VALID,
                    errors);
        }

        // Enregistrement de la vente dans la bdd
        Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));

        // associer la vente aux lignes de ventes et les sauvegarder
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
        });

        return VentesDto.fromEntity(savedVentes);
    }


    @Override
    public VentesDto findById(Integer id) {
        if (id == null) {
            log.warn("Vente ID is NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a été trouvé avec cet ID", ErrorsCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findVenteByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("No vente exist in the BDD with this CODE");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a été trouvé avec cet ID", ErrorsCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.warn("Vente ID is NULL");
            return ;
        }
        ventesRepository.deleteById(id);
    }
}
