package classifier;

import normaliser.NormalValueProbability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTokenClassifierTest {
    private final StringTokenClassifier classifier = new StringTokenClassifier(List.of(
            "A B",
            "A",
            "B",
            "C"
    ));

    @Test
    void totalMismatchReturnsNoNonZeroProbabilities() {
        List<NormalValueProbability> probabilities = classifier.classifyToList("D");
        assertEquals(0, probabilities.size());
    }

    @Test
    void exactMatchReturnsProbabilityOne() {
        List<NormalValueProbability> probabilities = classifier.classifyToList("A B");
        assertEquals(3, probabilities.size());
        assertEquals(1.0d, probabilities.getFirst().probability());
    }

    @Test
    void lowInformationPartialMatchReturnsExtraReducedProbability() {
        List<NormalValueProbability> probabilities = classifier.classifyToList("B");
        assertEquals(2, probabilities.size());
        assertEquals(0.25d, probabilities.getFirst().probability());
    }

    @Test
    void highInformationPartialMatchReturnsHighProbability() {
        List<NormalValueProbability> probabilities = classifier.classifyToList("E B");
        assertEquals(2, probabilities.size());
        assertEquals(1.0d, probabilities.get(1).probability());
    }
}
