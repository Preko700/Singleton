package preko.singleton;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionSimulator {
    private ConfigurationManager config;
    private Random random;
    
    public ConnectionSimulator() {
        this.config = ConfigurationManager.getInstance();
        this.random = new Random();
    }
    
    public void simulate() {
        System.out.println("\n=== CONNECTION SIMULATOR ===");
        
        int maxConnections = config.getIntProperty("maxConnections");
        boolean enableLogs = config.getBooleanProperty("enableLogs");
        String currency = config.getProperty("defaultCurrency");
        
        AtomicInteger successfulConnections = new AtomicInteger(0);
        AtomicInteger failedConnections = new AtomicInteger(0);
        
        System.out.println("Starting simulation with max connections: " + maxConnections);
        System.out.println("Logging enabled: " + enableLogs);
        System.out.println("Currency: " + currency);
        System.out.println();
        
        // Simulate connections
        for (int i = 1; i <= maxConnections; i++) {
            boolean success = simulateConnection(i, enableLogs);
            
            if (success) {
                successfulConnections.incrementAndGet();
            } else {
                failedConnections.incrementAndGet();
            }
            
            // Add small delay for realistic simulation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Show summary
        System.out.println("\n=== SIMULATION SUMMARY ===");
        System.out.println("Total attempted connections: " + maxConnections);
        System.out.println("Successful connections: " + successfulConnections.get());
        System.out.println("Failed connections: " + failedConnections.get());
        System.out.println("Success rate: " + String.format("%.2f%%", 
            (successfulConnections.get() * 100.0 / maxConnections)));
        System.out.println("Currency for transactions: " + currency);
        System.out.println("Estimated cost: " + calculateCost(successfulConnections.get(), currency));
    }
    
    private boolean simulateConnection(int connectionId, boolean enableLogs) {
        // Simulate 80% success rate
        boolean success = random.nextDouble() < 0.8;
        
        if (enableLogs) {
            if (success) {
                System.out.println("[LOG] Connection #" + connectionId + " - SUCCESS");
            } else {
                System.out.println("[LOG] Connection #" + connectionId + " - FAILED");
            }
        }
        
        return success;
    }
    
    private String calculateCost(int connections, String currency) {
        double costPerConnection = 0.50; // Base cost
        double totalCost = connections * costPerConnection;
        
        // Adjust for currency
        switch (currency.toUpperCase()) {
            case "CRC":
                totalCost *= 650; // Approximate CRC exchange rate
                break;
            case "EUR":
                totalCost *= 0.85; // Approximate EUR exchange rate
                break;
            case "USD":
            default:
                // Keep USD as base
                break;
        }
        
        return String.format("%.2f %s", totalCost, currency);
    }
}