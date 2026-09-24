package selector;

import normaliser.NormalValueProbability;

import java.util.List;
import java.util.stream.Stream;

public interface Selector {
    NormalValueProbability select(Stream<NormalValueProbability> ranked);
    default NormalValueProbability select(List<NormalValueProbability> ranked) {
        return select(ranked.stream());
    }
}
