package it.unisa.HAYT.servicies;

import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.repositories.TipSuggestedRepository;
import it.unisa.HAYT.repositories.TipFavoriteRepository;
import it.unisa.HAYT.repositories.TipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private TipFavoriteRepository tipFavoriteRepository;

    public List<TipEntity> getTipsByType(String type) {
        return tipRepository.findByTypeIgnoreCase(type);
    }

    public List<Long> getFavoriteTipIds(PatientEntity patient) {
        return tipFavoriteRepository.findByPatient(patient)
                .stream()
                .map(fav -> fav.getTip().getId())
                .toList();
    }

    public void addFavorite(PatientEntity patient, Long tipId) {
        TipEntity tip = tipRepository.findById(tipId).orElseThrow();
        Optional<TipFavoriteEntity> existing = tipFavoriteRepository.findByPatientAndTip(patient, tip);
        if (existing.isEmpty()) {
            TipFavoriteEntity favorite = new TipFavoriteEntity();
            favorite.setPatient(patient);
            favorite.setTip(tip);
            tipFavoriteRepository.save(favorite);
        }
    }

    @Transactional
    public void removeFavorite(PatientEntity patient, Long tipId) {
        TipEntity tip = tipRepository.findById(tipId).orElseThrow();
        tipFavoriteRepository.deleteByPatientAndTip(patient, tip);
    }



}

