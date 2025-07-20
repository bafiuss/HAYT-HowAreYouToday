package it.unisa.HAYT.servicies;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.HAYT.dto.DiaryDTO;
import it.unisa.HAYT.dto.SentimentDTO;
import it.unisa.HAYT.entities.DiaryEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.repositories.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryEntryRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    public DiaryEntity saveEntry(DiaryDTO dto, PatientEntity patient) {
        DiaryEntity entry = new DiaryEntity();

        entry.setTitle(dto.getTitle());
        entry.setContent(dto.getContent());
        entry.setMood(dto.getMood());
        entry.setPatient(patient);

        String sentiment = analyzeWithPython(dto.getContent());
        entry.setSentiment(sentiment);

        return diaryEntryRepository.save(entry);
    }

    private String analyzeWithPython(String text) {
        String url = "http://localhost:5000/analyze";
        var request = Map.of("text", text);
        var response = restTemplate.postForEntity(url, request, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SentimentDTO sentimentResponse = objectMapper.readValue(response.getBody(), SentimentDTO.class);
            return sentimentResponse.getReadableLabel();
        } catch (Exception e) {
            e.printStackTrace();
            return "UNKNOWN";
        }

    }


    public List<DiaryEntity> findByPatientIdOrderByCreatedAtDesc(Long patientId) {
        return diaryEntryRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }
}

