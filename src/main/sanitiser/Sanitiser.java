package sanitiser;

import java.util.Optional;

public interface Sanitiser {
    Optional<String> sanitise(String input);
}