package sanitiser;

import java.util.Optional;
import java.util.regex.Pattern;

public class DefaultSanitiser implements Sanitiser {
    private static final Pattern whitespace = Pattern.compile("\\s+");
    @Override
    public Optional<String> sanitise(String input) {
        if (input == null || input.isBlank()) {
            return Optional.empty();
        }
        //Could be made loads faster but not important for now.
        String sanitised = whitespace.matcher(input.trim()).replaceAll(" ");
        return Optional.of(sanitised);
    }
}