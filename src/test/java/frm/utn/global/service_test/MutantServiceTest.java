package frm.utn.global.service_test;

import frm.utn.global.entity.DnaRecord;
import frm.utn.global.repository.DnaRecordRepository;
import frm.utn.global.service.MutantDetector;
import frm.utn.global.service.MutantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de negocio MutantService.
 */
class MutantServiceTest {

    private DnaRecordRepository repository;
    private MutantDetector detector;
    private MutantService service;

    @BeforeEach
    void setup() {
        repository = mock(DnaRecordRepository.class);
        detector = mock(MutantDetector.class);
        service = new MutantService(repository, detector);
    }

    @Test
    void mutantDnaIsDetectedAndStored() {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};

        when(detector.isMutant(dna)).thenReturn(true);
        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, times(1)).save(Mockito.any(DnaRecord.class));
    }

    @Test
    void humanDnaIsStoredWithMutantFlagFalse() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(detector.isMutant(dna)).thenReturn(false);
        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.processDna(dna);

        assertFalse(result);
        verify(repository, times(1)).save(Mockito.any(DnaRecord.class));
    }

    @Test
    void existingRecordIsReturnedFromCache() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};

        DnaRecord stored = DnaRecord.builder()
                .isMutant(true)
                .hash("abc")
                .dna("AAAA,AAAA,AAAA,AAAA")
                .build();

        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.of(stored));

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, never()).save(Mockito.any());
    }

    @Test
    void hashGenerationDoesNotThrowForValidDna() {
        String[] dna = {"AAAA"};

        when(repository.findByHash(Mockito.anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        assertDoesNotThrow(() -> service.processDna(dna));
    }

    @Test
    void invalidDnaThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.processDna(null));
    }
}
