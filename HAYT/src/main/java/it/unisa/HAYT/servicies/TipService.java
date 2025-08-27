package it.unisa.HAYT.servicies;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipCompletedEntity;
import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.entities.TipSelectionEntity;
import it.unisa.HAYT.repositories.DiaryRepository;
import it.unisa.HAYT.repositories.TipCompletedRepository;
import it.unisa.HAYT.repositories.TipRepository;
import it.unisa.HAYT.entities.DiaryEntity.Sentiment;
import it.unisa.HAYT.repositories.TipSelectionRepository;
import it.unisa.HAYT.entities.DiaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private TipCompletedRepository tipCompletedRepository;

    @Autowired
    private TipSelectionRepository tipSelectionRepository;

    public List<TipEntity> getTipsByType(String type) {
        return tipRepository.findByTypeIgnoreCase(type);
    }

    public void markAsCompleted(PatientEntity patient, Long tipId) {
        TipEntity tip = tipRepository.findById(tipId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tip id"));

        if (!tipCompletedRepository.existsByPatientAndTip(patient, tip)) {
            TipCompletedEntity completed = new TipCompletedEntity();
            completed.setPatient(patient);
            completed.setTip(tip);
            completed.setCompletedAt(LocalDateTime.now());
            tipCompletedRepository.save(completed);
        }
    }

    public List<Long> getCompletedTipIds(PatientEntity patient) {
        return tipCompletedRepository.findByPatient(patient).stream()
                .map(ct -> ct.getTip().getId())
                .toList();
    }

    public Set<Long> getActiveTipsForPatient(PatientEntity patient) {
        List<Long> completedIds = getCompletedTipIds(patient);

        List<TipSelectionEntity> activeSelections = tipSelectionRepository
                .findByPatient(patient).stream()
                .filter(sel -> !completedIds.contains(sel.getTip().getId()))
                .toList();

        return activeSelections.stream()
                .map(sel -> sel.getTip().getId())
                .collect(Collectors.toSet());
    }

    public void generateTipsForNewDiaryEntry(PatientEntity patient, DiaryEntity diary, int limit) {
        List<Long> completedIds = getCompletedTipIds(patient);

        List<TipSelectionEntity> activeSelections = tipSelectionRepository
                .findByPatient(patient).stream()
                .filter(sel -> !completedIds.contains(sel.getTip().getId()))
                .toList();

        int missing = limit - activeSelections.size();
        if (missing <= 0) return;

        List<TipEntity> availableTips = tipRepository.findAll().stream()
                .filter(tip -> !completedIds.contains(tip.getId()))
                .filter(tip -> activeSelections.stream()
                        .noneMatch(sel -> sel.getTip().getId().equals(tip.getId())))
                .collect(Collectors.toList());

        Collections.shuffle(availableTips);

        availableTips.stream().limit(missing).forEach(tip -> {
            TipSelectionEntity sel = new TipSelectionEntity();
            sel.setPatient(patient);
            sel.setTip(tip);
            sel.setCreatedAt(diary.getCreatedAt());
            tipSelectionRepository.save(sel);
        });
    }

}

