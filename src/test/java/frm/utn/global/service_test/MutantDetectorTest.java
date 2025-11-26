package frm.utn.global.service_test;

import frm.utn.global.service.MutantDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del algoritmo de detección de mutantes.
 */
public class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    void detectsHorizontalSequence() {
        String[] dna = {
                "AAAA",
                "TGCT",
                "CGTA",
                "TTTT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void detectsVerticalSequence() {
        String[] dna = {
                "ATGC",
                "ATGT",
                "ATGA",
                "ATGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void detectsDiagonalRightSequence() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void detectsDiagonalLeftSequence() {
        String[] dna = {
                "TGGG",
                "CTGA",
                "AGTG",
                "GGGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void detectsTwoDifferentSequences() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTTT",
                "AGAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void returnsFalseWhenNoSequenceFound() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void smallMatrixSingleRowNotMutant() {
        String[] dna = {"ATGC"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void smallMatrixSingleColumnNotMutant() {
        String[] dna = {"A", "T", "G", "C"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void matrix2x2NotMutant() {
        String[] dna = {"AT", "CG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void diagonalRightWithoutFourEqualsNotMutant() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void diagonalLeftWithoutFourEqualsNotMutant() {
        String[] dna = {
                "TGA",
                "CGT",
                "ACT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void allSameLetterIsMutant() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void lowercaseInputStillProcessedAsNonMutantInThisImpl() {
        String[] dna = {
                "atgc",
                "cagt",
                "ttat",
                "agac"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void invalidCharactersWithoutSequenceReturnFalse() {
        String[] dna = {
                "ATXG",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void emptyRowReturnsFalse() {
        String[] dna = {""};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void nullMatrixThrowsNpeWithCurrentImplementation() {
        assertThrows(NullPointerException.class, () -> detector.isMutant(null));
    }

    @Test
    void nullRowThrowsNpeWithCurrentImplementation() {
        String[] dna = {null};
        assertThrows(NullPointerException.class, () -> detector.isMutant(dna));
    }
}
