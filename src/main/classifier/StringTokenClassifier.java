package classifier;

import normaliser.NormalValueProbability;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record StringTokenClassifier(
        List<String> normalValues
) implements Classifier {
    private double rank(String input, String normalValue) {
        List<String> inputTokens = List.of(input.toLowerCase().split(" "));
        List<String> normalValueTokens = List.of(normalValue.toLowerCase().split(" "));
        //A fairly dumb algorithm.
        //Exact token matches as a fraction of the normal value token count
        double percentageOverlap = ((double) normalValueTokens.stream().filter(inputTokens::contains).count()) / normalValueTokens.size();
        //Reduce confidence if the normal value has greater specificity than the input
        double confidenceScalingFactor = (double) inputTokens.size() / normalValueTokens.size();
        //Cap at 1 for when it's the other way round
        return Math.min(1.0, percentageOverlap * confidenceScalingFactor);
    }

    @Override
    public Stream<NormalValueProbability> classify(String input) {
        return normalValues.stream()
                .map(normalValue -> {
                    final double rank = rank(input, normalValue);
                    return (rank > 0) ? new NormalValueProbability(normalValue, rank) : null;
                })
                .filter(Objects::nonNull);
    }
}
