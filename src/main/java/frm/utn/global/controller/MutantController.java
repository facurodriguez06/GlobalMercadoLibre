package frm.utn.global.controller;

import frm.utn.global.dto.DnaRequest;
import frm.utn.global.dto.StatsResponse;
import frm.utn.global.service.MutantService;
import frm.utn.global.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST principal del desafío:
 * - POST /mutant: recibe ADN y responde 200 o 403.
 * - GET  /stats : devuelve estadísticas de mutantes vs humanos.
 */
@RestController
@RequestMapping
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService,
                            StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    /**
     * Endpoint que determina si un ADN pertenece a un mutante.
     * 200 OK  si es mutante
     * 403 FORBIDDEN si es humano
     * 400 / 500 se manejan en GlobalExceptionHandler.
     */
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest request) {
        boolean isMutant = mutantService.processDna(
                request.getDna().toArray(String[]::new)
        );

        HttpStatus status = isMutant ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).build();
    }

    /**
     * Devuelve las estadísticas globales de ADN analizados.
     */
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
