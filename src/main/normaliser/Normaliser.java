package normaliser;

import classifier.Classifier;
import fallback.Fallback;
import sanitiser.Sanitiser;
import selector.Selector;

import java.util.List;

public record Normaliser(
        Sanitiser sanitiser,
        Classifier classifier,
        Selector selector,
        Fallback fallback
) {
    public String normalise(String input) {
        return sanitiser().sanitise(input)
                .map(classifier::classify)
                .map(selector::select)
                .map(NormalValueProbability::normalValue)
                .orElseGet(() -> fallback.apply(input));
    }

    public List<NormalValueProbability> classify(String input) {
        return sanitiser().sanitise(input)
                .map(classifier::classifyToList)
                .orElseGet(List::of);
    }
}
