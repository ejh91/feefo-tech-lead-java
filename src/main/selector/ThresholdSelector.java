package selector;

import normaliser.NormalValueProbability;

import java.util.stream.Stream;

public record ThresholdSelector(
        double threshold
) implements Selector {
    @Override
    public NormalValueProbability select(Stream<NormalValueProbability> ranked) {
        return ranked.reduce(null, (current, next) -> {
            if (next.probability() < threshold) {
                return current;
            }

            if (current == null) {
                return next;
            }

            return NormalValueProbability.PROBABILITY_ASC.max(current, next);
        });
    }
}
