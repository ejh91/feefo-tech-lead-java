package sanitiser;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultSanitiserTest {
    private final DefaultSanitiser defaultSanitiser = new DefaultSanitiser();

    @Test
    void nullInputReturnsNull() {
        Optional<String> sanitised = defaultSanitiser.sanitise(null);
        assertTrue(sanitised.isEmpty());
    }

    @Test
    void emptyInputReturnsNull() {
        Optional<String> sanitised = defaultSanitiser.sanitise("");
        assertTrue(sanitised.isEmpty());
    }

    @Test
    void blankInputReturnsNull() {
        Optional<String> sanitised = defaultSanitiser.sanitise("         \n");
        assertTrue(sanitised.isEmpty());
    }

    @Test
    void whitespaceRemovedFromInput() {
        Optional<String> sanitised = defaultSanitiser.sanitise(" text  in \n  here        \n");
        assertFalse(sanitised.isEmpty());
        assertEquals("text in here", sanitised.get());
    }
}
