package fallback;

public class NullFallback implements Fallback {
    @Override
    public String apply(String ignored) {
        return null;
    }
}
