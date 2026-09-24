package fallback;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NullFallbackTest {
    private final NullFallback nullFallback = new NullFallback();

    @Test
    void nullInputReturnsNull() {
        String fallback = nullFallback.apply(null);
        assertNull(fallback);
    }

    @Test
    void realInputReturnsNull() {
        String input = "this-object";
        String fallback = nullFallback.apply(input);
        assertNull(fallback);
    }
}
