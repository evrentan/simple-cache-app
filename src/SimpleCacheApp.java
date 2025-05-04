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

        int cacheTTL = DEFAULT_CACHE_TTL;
        boolean debug = true;
        boolean skipGC = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--ttl":
                    if (i + 1 < args.length) {
                        cacheTTL = Integer.parseInt(args[++i]);
                    }
                    break;
                case "--nodebug":
                    debug = false;
                    break;
                case "--nogc":
                    skipGC = true;
                    break;
                default:
                    System.out.println("Unknown argument: " + args[i]);
            }
        }

        if (debug) {
            SimpleCacheAppUtils.printDebugInfo(debug, cacheTTL, skipGC);
        }

        ScheduledExecutorService scheduler = null;
        if (!skipGC) {
            scheduler = Executors.newScheduledThreadPool(1);
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
        }

        System.out.println("Starting SimpleCacheApp...");

        while (true) {
            SimpleCacheAppUtils.printActions();
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
                    SimpleCacheAppUtils.printCache(cache);
                    break;
                case 5:
                    SimpleCacheAppUtils.printCacheSize(cache);
                    break;
                case 6:
                    cache.clear();
                    System.out.println("Cache cleared.");
                    break;
                case 7:
                    System.out.println("Exiting SimpleCacheApp...");
                    scanner.close();
                    if (scheduler != null) {
                        scheduler.shutdown();
                    }
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid action. Please select a valid action.");
                    break;
            }
        }
    }
}