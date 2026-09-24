package selector;

import normaliser.NormalValueProbability;

import java.util.Comparator;
import java.util.stream.Stream;

public record OutlierSelector(
        double outlierThreshold
) implements Selector {
    private static final Comparator<NormalValueProbability> comparator = NormalValueProbability.PROBABILITY_ASC.reversed();

    @Override
    public NormalValueProbability select(Stream<NormalValueProbability> ranked) {
        return ranked.sorted(comparator)
                .limit(2)
                .reduce(null, (current, next) -> {
                    //First pass: I'm the max - if I'm not above the outlier threshold then by definition nothing else can be
                    //Second pass: If current is still null from first pass then by induction I'll fail this check too.
                    if (current == null) {
                        return next.probability() < outlierThreshold ? null : next;
                    }

                    //Second pass (if there is one!): I'm the one below the max so accept the max if it's bigger enough
                    return (current.probability() - next.probability()) < outlierThreshold ? null : current;
        });
    }
}
