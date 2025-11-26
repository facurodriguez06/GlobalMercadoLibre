package frm.utn.global.service;

import frm.utn.global.entity.DnaRecord;
import frm.utn.global.repository.DnaRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

/**
 * Orquesta el flujo completo:
 * validar ADN, revisar cache en BD, ejecutar detector, persistir y devolver resultado.
 */
@Service
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    public MutantService(DnaRecordRepository repository, MutantDetector detector) {
        this.repository = repository;
        this.detector = detector;
    }

    @Transactional
    public boolean processDna(String[] dna) {

        if (!DnaValidator.isValid(dna)) {
            throw new IllegalArgumentException("DNA NOT VALID");
        }

        // Normalizamos el ADN como un único string (sin espacios) para hashear.
        String joined = String.join(",", dna);
        String hash = hashDna(joined);

        // Evitar reprocesar ADN ya conocido
        Optional<DnaRecord> existing = repository.findByHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        boolean isMutant = detector.isMutant(dna);

        DnaRecord record = DnaRecord.builder()
                .hash(hash)
                .dna(joined)
                .isMutant(isMutant)
                .build();

        repository.save(record);
        return isMutant;
    }

    // Hash SHA-256 del string normalizado de ADN.
    private String hashDna(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] h = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : h) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            // Si falla el hashing, preferimos propagar como Runtime.
            throw new IllegalStateException("Error generando hash del ADN", e);
        }
    }
}
