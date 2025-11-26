package frm.utn.global.dto;

/**
 * DTO de salida para /stats.
 * Nombres en snake_case para cumplir con el enunciado de la API.
 */
public class StatsResponse {

    private final long count_mutant_dna;
    private final long count_human_dna;
    private final double ratio;

    public StatsResponse(long mutants, long humans) {
        this.count_mutant_dna = mutants;
        this.count_human_dna = humans;
        this.ratio = humans == 0 ? 0.0 : (double) mutants / humans;
    }

    public long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public long getCount_human_dna() {
        return count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }
}
