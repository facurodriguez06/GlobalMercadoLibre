package frm.utn.global.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad persistente que registra cada ADN analizado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dna_records")
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hash único del ADN, para evitar recomputar resultados.
    @Column(nullable = false, unique = true, length = 512)
    private String hash;

    // ADN original, almacenado como un solo String (separado por comas).
    @Column(nullable = false, length = 2000)
    private String dna;

    @Column(nullable = false)
    private boolean isMutant;

    public DnaRecord(String hash, String dna, boolean isMutant) {
        this.hash = hash;
        this.dna = dna;
        this.isMutant = isMutant;
    }
}
