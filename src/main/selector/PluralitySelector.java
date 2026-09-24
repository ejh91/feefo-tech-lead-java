package selector;

import normaliser.NormalValueProbability;

import java.util.Comparator;
import java.util.stream.Stream;

public class PluralitySelector implements Selector {
    private static final Comparator<NormalValueProbability> comparator = NormalValueProbability.PROBABILITY_ASC.reversed();

    @Override
    public NormalValueProbability select(Stream<NormalValueProbability> ranked) {
        return ranked.sorted(comparator)
                .limit(2)
                .reduce(null, (current, next) -> {
                    //First pass: I'm the max
                    if (current == null) {
                        return next;
                    }

                    //Second pass (if there is one!): I'm the one below the max so accept the max if it's actually bigger
                    return (current.probability() > next.probability()) ? current : null;
                });
    }
}
