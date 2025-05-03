public class KeyObject {
    private final String key;

    public KeyObject(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof KeyObject other)) return false;
        return key.equals(other.key);
    }
}
