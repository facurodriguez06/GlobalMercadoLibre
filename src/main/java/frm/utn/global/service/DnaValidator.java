package frm.utn.global.service;

import java.util.regex.Pattern;

/**
 * Utilidad estática para validar que el ADN:
 * - no sea nulo ni vacío
 * - forme una matriz NxN
 * - solo contenga A, T, C o G.
 */
public final class DnaValidator {

    // Expresión regular para validar solo A, T, C, G
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    private DnaValidator() {
        // Evitamos instanciación
    }

    public static boolean isValid(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;

        for (String row : dna) {
            if (row == null) return false;

            if (row.length() != n) return false;

            if (!VALID_DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }

        return true;
    }
}
