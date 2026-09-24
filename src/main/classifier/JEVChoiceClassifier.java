package classifier;

import normaliser.NormalValueProbability;

import java.util.List;
import java.util.stream.Stream;

public record JEVChoiceClassifier(
        List<String> normalValues
) implements Classifier {
    @Override
    public Stream<NormalValueProbability> classify(String input) {
        //todo wheeeeee AI
        return Stream.of();
    }
}
