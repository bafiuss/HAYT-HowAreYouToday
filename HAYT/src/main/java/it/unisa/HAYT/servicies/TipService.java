package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.TipCountDTO;
import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.repositories.TipSuggestedRepository;
import it.unisa.HAYT.repositories.TipFavoriteRepository;
import it.unisa.HAYT.repositories.TipRepository;
import it.unisa.HAYT.entities.DiaryEntity.Sentiment;
import it.unisa.HAYT.entities.TipEntity.Type;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private TipFavoriteRepository tipFavoriteRepository;

    @Autowired
    private TipSuggestedRepository tipSuggestedRepository;

    @Autowired
    private DiaryService diaryService;

    public List<TipEntity> getTipsByType(Type type) {
        return tipRepository.findByType(type);
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

    @Transactional
    public TipSuggestedEntity suggestRandomTip(PatientEntity patient) {
        List<TipFavoriteEntity> favorites = tipFavoriteRepository.findByPatient(patient);

        TipEntity chosen;
        if (!favorites.isEmpty()) {
            chosen = favorites.get(new Random().nextInt(favorites.size())).getTip();
        } else {
            List<TipEntity> allTips = tipRepository.findAll();
            chosen = allTips.get(new Random().nextInt(allTips.size()));
        }

        TipSuggestedEntity suggestion = new TipSuggestedEntity();
        suggestion.setTip(chosen);
        suggestion.setPatient(patient);
        suggestion.setCompleted(false);

        return tipSuggestedRepository.save(suggestion);
    }

    @Transactional
    public void markAsComplete(Long suggestionId) {
        TipSuggestedEntity suggestion = tipSuggestedRepository.findById(suggestionId)
                .orElseThrow();

        suggestion.setCompleted(true);
        tipSuggestedRepository.save(suggestion);
    }

    public Optional<TipSuggestedEntity> getLatestSuggestion(PatientEntity patient) {
        return tipSuggestedRepository.findTopByPatientOrderByIdDesc(patient);
    }

    public Optional<TipSuggestedEntity> getSuggestionIfLastEntryNegative(PatientEntity patient) {
        return diaryService.findLastEntryByPatient(patient)
                .filter(entry -> entry.getSentiment() == Sentiment.negative)
                .flatMap(entry -> getLatestSuggestion(patient))
                .filter(suggestion -> !suggestion.isCompleted());
    }

    public Map<Long, Long> getCompletedCounts(Long patientId) {
        return tipSuggestedRepository.countCompletedByTipAndPatient(patientId)
                .stream()
                .collect(Collectors.toMap(TipCountDTO::getTipId, TipCountDTO::getCount));
    }

    public Map<Long, Long> getSuggestedCounts(Long patientId) {
        return tipSuggestedRepository.countSuggestedByTipAndPatient(patientId)
                .stream()
                .collect(Collectors.toMap(TipCountDTO::getTipId, TipCountDTO::getCount));
    }



}

