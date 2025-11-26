package frm.utn.global.service;

import frm.utn.global.dto.StatsResponse;
import frm.utn.global.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de lectura de métricas globales.
 */
@Service
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsService(DnaRecordRepository repository) {
        this.repository = repository;
    }

    public StatsResponse getStats() {
        long mutants = repository.countMutants();
        long humans  = repository.countHumans();
        return new StatsResponse(mutants, humans);
    }
}
