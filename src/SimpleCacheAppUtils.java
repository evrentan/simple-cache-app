import java.util.Map;

public class SimpleCacheAppUtils {
    public static void printActions() {
        System.out.println("Possible actions:");
        System.out.println("1. Add an item to the cache");
        System.out.println("2. Retrieve an item from the cache");
        System.out.println("3. Remove an item from the cache");
        System.out.println("4. Print cache contents");
        System.out.println("5. Print cache size");
        System.out.println("6. Clear the cache");
        System.out.println("7. Exit");
        System.out.print("Please select an action (1-7): ");
    }

    public static void printCache(Map<KeyObject, String> cache) {
        System.out.println("Current cache contents:");
        for (Map.Entry<KeyObject, String> entry : cache.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public static void printCacheSize(Map<KeyObject, String> cache) {
        System.out.println("Current cache size: " + cache.size());
    }

    public static void printDebugInfo(boolean debug, int cacheTTL, boolean skipGC) {
        if (debug) {
            System.out.println("[DEBUG] Cache TTL set to: " + cacheTTL + " ms");
            if (skipGC) {
                System.out.println("[DEBUG] GC disabled: " + skipGC + " - explicit garbage collection will not run within the default/specified TTL");
            } else {
                System.out.println("[DEBUG] GC disabled: " + skipGC + " - explicit garbage collection will run within the default/specified TTL");
            }
        }
    }
}
