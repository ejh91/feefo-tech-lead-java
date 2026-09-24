package classifier;

import normaliser.NormalValueProbability;

import java.util.List;
import java.util.stream.Stream;

public interface Classifier {
    Stream<NormalValueProbability> classify(String input);
    default List<NormalValueProbability> classifyToList(String input) {
        return classify(input).toList();
    }
}
