import classifier.StringTokenClassifier;
import fallback.NullFallback;
import normaliser.Normaliser;
import sanitiser.DefaultSanitiser;
import selector.PluralitySelector;
import selector.ThresholdSelector;

import java.util.List;

public class Project {
    static void main(String[] args) {
        List<String> normalValues = List.of(
                "Software engineer",
                "Accountant"
        );
        Normaliser normaliser = new Normaliser(
                new DefaultSanitiser(),
                new StringTokenClassifier(normalValues),
                //new PluralitySelector(),
                new ThresholdSelector(0.5),
                new NullFallback()
        );
        test(normaliser, "Java engineer");
        test(normaliser, "C# engineer");
        test(normaliser, "Accountant");
        test(normaliser, "Chief Accountant");
        test(normaliser, "Engineer");
    }
    private static void test(Normaliser normaliser, String input) {
        System.out.println(input + " probabilities: " + normaliser.classify(input) + " selected " + normaliser.normalise(input));
    }
}
