package it.unisa.HAYT.servicies;

import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.repositories.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    public List<TipEntity> getTipsByType(String type) {
        return tipRepository.findByTypeIgnoreCase(type);
    }

}
