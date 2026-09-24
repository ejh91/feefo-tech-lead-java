package normaliser;

import java.util.Comparator;

public record NormalValueProbability(
        String normalValue,
        double probability
) {
    public static final Comparator<NormalValueProbability> PROBABILITY_ASC = Comparator
            .comparing(NormalValueProbability::probability);

    @Override
    public String toString() {
        return "{" +
                "normalValue='" + normalValue + '\'' +
                ", probability=" + probability +
                '}';
    }
}
