package frm.utn.global.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * DTO de entrada para el endpoint /mutant.
 * Representa la matriz de ADN como una lista de Strings.
 */
@Data
public class DnaRequest {

    @NotEmpty(message = "El campo 'dna' no puede ser vacío.")
    private List<String> dna;
}
