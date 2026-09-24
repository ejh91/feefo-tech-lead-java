package fallback;

public class IdentityFallback implements Fallback {
    @Override
    public String apply(String ignored) {
        return null;
    }
}
