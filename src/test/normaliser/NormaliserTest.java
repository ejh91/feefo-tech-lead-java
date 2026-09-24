package normaliser;

import classifier.JEVChoiceClassifier;
import classifier.StringTokenClassifier;
import fallback.NullFallback;
import org.junit.jupiter.api.Test;
import sanitiser.DefaultSanitiser;
import selector.OutlierSelector;
import selector.PluralitySelector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NormaliserTest {
    private final List<String> normalValues = List.of(
            "Architect",
            "Software engineer",
            "Quantity surveyor",
            "Accountant"
    );
    private final Normaliser standardPluralityNormaliser = new Normaliser(
            new DefaultSanitiser(),
            new StringTokenClassifier(normalValues),
            new PluralitySelector(),
            new NullFallback()
    );
    private final Normaliser aiOutlierNormaliser = new Normaliser(
            new DefaultSanitiser(),
            new JEVChoiceClassifier(normalValues),
            new OutlierSelector(0.3d),
            new NullFallback()
    );

    @Test
    void standardPluralityNormaliser() {
        String nullNormalised = standardPluralityNormaliser.normalise(null);
        String noMatchNormalised = standardPluralityNormaliser.normalise("Butler");
        String javaEngineerNormalised = standardPluralityNormaliser.normalise("Java engineer");
        String cSharpEngineerNormalised = standardPluralityNormaliser.normalise("C# engineer");
        String accountantNormalised = standardPluralityNormaliser.normalise("Accountant");
        String chiefAccountantNormalised = standardPluralityNormaliser.normalise("chief    accountant");
        String softwareArchitectNormalised = standardPluralityNormaliser.normalise("Software architect");
        assertNull(nullNormalised);
        assertNull(noMatchNormalised);
        assertEquals("Software engineer", javaEngineerNormalised);
        assertEquals("Software engineer", cSharpEngineerNormalised);
        assertEquals("Accountant", accountantNormalised);
        assertEquals("Accountant", chiefAccountantNormalised);
        assertEquals("Architect", softwareArchitectNormalised);
    }

    @Test
    void aiOutlierNormaliser() {
        String javaEngineerNormalised = aiOutlierNormaliser.normalise("Java engineer");
        String cSharpEngineerNormalised = aiOutlierNormaliser.normalise("C# engineer");
        String accountantNormalised = aiOutlierNormaliser.normalise("Accountant");
        String chiefAccountantNormalised = aiOutlierNormaliser.normalise("chief    accountant");
        String softwareArchitectNormalised = aiOutlierNormaliser.normalise("Software architect");
        assertEquals("Software engineer", javaEngineerNormalised);
        assertEquals("Software engineer", cSharpEngineerNormalised);
        assertEquals("Accountant", accountantNormalised);
        assertEquals("Accountant", chiefAccountantNormalised);
        //Here our entirely fictitious fixed weights do a better job with 'software architect'
        // - of course this is because I've chosen them to do this.
        assertEquals("Software engineer", softwareArchitectNormalised);
    }
}
