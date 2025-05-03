import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleCacheApp {
    private static Map<KeyObject, String> cache = new WeakHashMap<>(); // WeakHashMap to hold cache entries
    private static List<KeyObject> keyReferences = new ArrayList<>(); // List to hold strong references to keys
    private static final int DEFAULT_CACHE_TTL = 60_000; // Default Cache Time To Live in milliseconds
    private static final int DEFAULT_GC_INTERVAL = 1_000; // Default Garbage Collection interval in milliseconds

    public static void main(String... args) {
        System.out.println("This is a simple cache application that demonstrates the use of WeakHashMap developed by Evren Tan!");

        final int cacheTTL = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_CACHE_TTL;

        System.out.println("Cache TTL is set to " + cacheTTL + " milliseconds!");

        SimpleCacheApp app = new SimpleCacheApp();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println();
            System.out.println("[Eviction] Cache TTL expired. Current cache size: " + cache.size());
            System.out.println("[Eviction] Cache TTL expired. Clearing strong references and invoking GC...");
            keyReferences.clear();
            System.gc();
            try {
                Thread.sleep(DEFAULT_GC_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[Eviction] Post-GC cache size: " + cache.size());
        }, cacheTTL, cacheTTL, TimeUnit.MILLISECONDS);

        System.out.println("Starting SimpleCacheApp...");

        while (true) {
            app.printActions();
            Scanner scanner = new Scanner(System.in);

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.print("Enter the key: ");
                    String keyToAdd = scanner.next();
                    System.out.print("Enter the value: ");
                    String valueToAdd = scanner.next();
                    KeyObject keyObject = new KeyObject(keyToAdd);
                    keyReferences.add(keyObject);
                    cache.put(keyObject, valueToAdd);
                    break;
                case 2:
                    System.out.print("Enter the key to retrieve: ");
                    String keyToRetrieve = scanner.next();
                    String value = cache.get(new KeyObject(keyToRetrieve));
                    if (value != null) {
                        System.out.println("Value: " + value);
                    } else {
                        System.out.println("Key not found in cache.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the key to remove: ");
                    String keyToRemove = scanner.next();
                    cache.remove(new KeyObject(keyToRemove));
                    System.out.println("Key removed from cache.");
                    break;
                case 4:
                    app.printCache();
                    break;
                case 5:
                    System.out.println("Cache size: " + cache.size());
                    break;
                case 6:
                    cache.clear();
                    System.out.println("Cache cleared.");
                    break;
                case 7:
                    System.out.println("Exiting SimpleCacheApp...");
                    scanner.close();
                    scheduler.shutdownNow();
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid action. Please select a valid action.");
                    break;
            }
        }
    }

    private void printActions() {
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

    private void printCache() {
        System.out.println("Current cache contents:");
        for (Map.Entry<KeyObject, String> entry : cache.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}