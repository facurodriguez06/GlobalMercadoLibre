package frm.utn.global.service;

import org.springframework.stereotype.Service;

/**
 * Implementa el algoritmo de detección de mutantes:
 * busca secuencias de 4 letras iguales en:
 * - horizontal
 * - vertical
 * - diagonal ↘
 * - diagonal ↙
 */
@Service
public class MutantDetector {

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        int sequencesFound = 0;

        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                char base = matrix[row][col];

                // →
                if (col + 3 < n &&
                        base == matrix[row][col + 1] &&
                        base == matrix[row][col + 2] &&
                        base == matrix[row][col + 3]) {
                    if (++sequencesFound > 1) return true;
                }

                // ↓
                if (row + 3 < n &&
                        base == matrix[row + 1][col] &&
                        base == matrix[row + 2][col] &&
                        base == matrix[row + 3][col]) {
                    if (++sequencesFound > 1) return true;
                }

                // ↘
                if (row + 3 < n && col + 3 < n &&
                        base == matrix[row + 1][col + 1] &&
                        base == matrix[row + 2][col + 2] &&
                        base == matrix[row + 3][col + 3]) {
                    if (++sequencesFound > 1) return true;
                }

                // ↙
                if (row + 3 < n && col - 3 >= 0 &&
                        base == matrix[row + 1][col - 1] &&
                        base == matrix[row + 2][col - 2] &&
                        base == matrix[row + 3][col - 3]) {
                    if (++sequencesFound > 1) return true;
                }
            }
        }
        return false;
    }
}
