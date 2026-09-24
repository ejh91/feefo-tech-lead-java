package classifier;

import normaliser.NormalValueProbability;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Completely fake, imagining we've got an API call to a fast general classifier like JEV.
 * In practice, you probably wouldn't do this as part of a synchronous pipeline
 *  due to the risk/extra complexity of handling checked network/API exceptions.
 * But for this exercise we'll just pretend that we've got an in-memory model.
 * @param normalValues
 */
public record JEVChoiceClassifier(
        List<String> normalValues
) implements Classifier {
    private static final Map<String, NormalValueProbability> fixedWeights = Map.of(
            "java engineer", new NormalValueProbability("Software engineer", 0.92d),
            "c# engineer", new NormalValueProbability("Software engineer", 0.92d),
            "accountant", new NormalValueProbability("Accountant", 1.0d),
            "chief accountant", new NormalValueProbability("Accountant", 0.98d),
            "software architect", new NormalValueProbability("Software engineer", 0.82d)
    );
    @Override
    public Stream<NormalValueProbability> classify(String input) {
        final String inputLower = input.toLowerCase();
        //Completely fake implementation pretending to do some natural language mapping
        try {
            //Pretend I'm an API call to an AI model just for fun
            Thread.sleep(50);
        } catch (InterruptedException ignored) {}
        Optional<NormalValueProbability> normalValueProbability = Optional.ofNullable(fixedWeights.get(inputLower));
        return normalValueProbability.map(Stream::of)
                .orElseGet(() -> {
                    //'not in my training data, best effort'
                    StringTokenClassifier fallbackClassifier = new StringTokenClassifier(normalValues);
                    return fallbackClassifier.classify(inputLower);
                });
    }
}
