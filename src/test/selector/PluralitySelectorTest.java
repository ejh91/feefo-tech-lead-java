package selector;

import normaliser.NormalValueProbability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PluralitySelectorTest {
    @Test
    void emptyListNullReturned() {
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of());
        assertNull(selected);
    }

    @Test
    void singleValueReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.5d);
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of(singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void firstValueHighestReturned() {
        NormalValueProbability notMeValue = new NormalValueProbability("not-me", 0.3d);
        NormalValueProbability notMeEitherValue = new NormalValueProbability("not-me-either", 0.2d);
        NormalValueProbability targetValue = new NormalValueProbability("just-me", 0.5d);
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of(targetValue, notMeValue, notMeEitherValue));
        assertEquals(targetValue, selected);
    }

    @Test
    void middleValueHighestReturned() {
        NormalValueProbability notMeValue = new NormalValueProbability("not-me", 0.3d);
        NormalValueProbability notMeEitherValue = new NormalValueProbability("not-me-either", 0.2d);
        NormalValueProbability targetValue = new NormalValueProbability("just-me", 0.5d);
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of(notMeValue, targetValue, notMeEitherValue));
        assertEquals(targetValue, selected);
    }

    @Test
    void lastValueHighestReturned() {
        NormalValueProbability notMeValue = new NormalValueProbability("not-me", 0.3d);
        NormalValueProbability notMeEitherValue = new NormalValueProbability("not-me-either", 0.2d);
        NormalValueProbability targetValue = new NormalValueProbability("just-me", 0.5d);
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of(notMeValue, notMeEitherValue, targetValue));
        assertEquals(targetValue, selected);
    }

    @Test
    void jointHighestNullReturned() {
        NormalValueProbability notMeValue = new NormalValueProbability("not-me", 0.3d);
        NormalValueProbability targetValue = new NormalValueProbability("just-me", 0.6d);
        NormalValueProbability secondTargetValue = new NormalValueProbability("just-me-too", 0.6d);
        PluralitySelector pluralitySelector = new PluralitySelector();
        NormalValueProbability selected = pluralitySelector.select(List.of(notMeValue, targetValue, secondTargetValue));
        assertNull(selected);
    }
}
