package selector;

import normaliser.NormalValueProbability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ThresholdSelectorTest {
    @Test
    void emptyListNullReturned() {
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of());
        assertNull(selected);
    }

    @Test
    void singleValueAtThresholdReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.7d);
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of(singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void singleValueAboveThresholdReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.8d);
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of(singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void singleValueBelowThresholdNullReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.5d);
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of(singleValue));
        assertNull(selected);
    }

    @Test
    void highestValueAboveThresholdReturned() {
        NormalValueProbability highEnoughValue = new NormalValueProbability("high-enough", 0.8d);
        NormalValueProbability highestValue = new NormalValueProbability("highest", 0.9d);
        NormalValueProbability lowerValue = new NormalValueProbability("noy-me", 0.5d);
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of(lowerValue, highEnoughValue, highestValue));
        assertEquals(highestValue, selected);
    }

    @Test
    void jointHighestFirstMatchReturned() {
        NormalValueProbability highestValue = new NormalValueProbability("highest", 0.8d);
        NormalValueProbability duplicateHighestValue = new NormalValueProbability("also-highest", 0.8d);
        NormalValueProbability lowerValue = new NormalValueProbability("noy-me", 0.5d);
        ThresholdSelector thresholdSelector = new ThresholdSelector(0.7d);
        NormalValueProbability selected = thresholdSelector.select(List.of(lowerValue, highestValue, duplicateHighestValue));
        assertEquals(highestValue, selected);
    }
}
