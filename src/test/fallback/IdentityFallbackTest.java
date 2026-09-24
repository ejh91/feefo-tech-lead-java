package fallback;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class IdentityFallbackTest {
    private final IdentityFallback identityFallback = new IdentityFallback();

    @Test
    void nullInputReturnsNull() {
        String fallback = identityFallback.apply(null);
        assertNull(fallback);
    }

    @Test
    void realInputReturnsItself() {
        String input = "this-object";
        String fallback = identityFallback.apply(input);
        assertSame(input, fallback);
    }
}
