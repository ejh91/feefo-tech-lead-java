import classifier.StringTokenClassifier;
import fallback.NullFallback;
import normaliser.Normaliser;
import sanitiser.DefaultSanitiser;
import selector.PluralitySelector;

private static final List<String> normalValues = List.of(
        "Architect",
        "Software engineer",
        "Quantity surveyor",
        "Accountant"
);

void main(String[] args) {
    Normaliser normaliser = new Normaliser(
            new DefaultSanitiser(),
            new StringTokenClassifier(normalValues),
            new PluralitySelector(),
            new NullFallback()
    );
    //The basic algorithm I set up in StringTokenClassifier gives you 'Architect' for 'Software architect'
    // - natural language ambiguity is a problem for something like that!
    for (String input : args) {
        test(normaliser, input);
    }
}

private static void test(Normaliser normaliser, String input) {
    IO.println(input + " probabilities: " + normaliser.classify(input) + " selected " + normaliser.normalise(input));
    IO.println(input + " -> " + normaliser.normalise(input));
}
