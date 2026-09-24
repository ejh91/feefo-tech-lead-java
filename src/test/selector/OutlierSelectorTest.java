package selector;

import normaliser.NormalValueProbability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OutlierSelectorTest {
    @Test
    void emptyListNullReturned() {
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of());
        assertNull(selected);
    }

    @Test
    void singleValueAtOutlierThresholdReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.2d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void singleValueAboveOutlierThresholdReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.3d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void singleValueBelowOutlierThresholdNullReturned() {
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.1d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(singleValue));
        assertNull(selected);
    }



    @Test
    void oneValueAtOutlierThresholdReturned() {
        NormalValueProbability lowerValue = new NormalValueProbability("too-low", 0.2d);
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.4d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(lowerValue, singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void oneValueAboveOutlierThresholdReturned() {
        NormalValueProbability lowerValue = new NormalValueProbability("too-low", 0.1d);
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.4d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(lowerValue, singleValue));
        assertEquals(singleValue, selected);
    }

    @Test
    void noValueAboveOutlierThresholdNullReturned() {
        NormalValueProbability lowerValue = new NormalValueProbability("too-low", 0.1d);
        NormalValueProbability singleValue = new NormalValueProbability("just-me", 0.29d);
        OutlierSelector outlierSelector = new OutlierSelector(0.2d);
        NormalValueProbability selected = outlierSelector.select(List.of(lowerValue, singleValue));
        assertNull(selected);
    }
}
