package preko.singleton;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    private Properties configuration;
    private ConfigurationLoader loader;
    
    private ConfigurationManager() {
        loader = new ConfigurationLoader();
        loadConfiguration();
    }
    
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    private void loadConfiguration() {
        lock.writeLock().lock();
        try {
            configuration = loader.loadConfiguration();
            System.out.println("Configuration loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            loadDefaultConfiguration();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    private void loadDefaultConfiguration() {
        configuration = new Properties();
        configuration.setProperty("defaultCurrency", "USD");
        configuration.setProperty("timeFormat", "24H");
        configuration.setProperty("maxConnections", "10");
        configuration.setProperty("language", "EN");
        configuration.setProperty("autoSaveInterval", "5");
        configuration.setProperty("enableLogs", "true");
        configuration.setProperty("theme", "light");
        configuration.setProperty("region", "LATAM");
        configuration.setProperty("backupEnabled", "true");
        configuration.setProperty("backupDirectory", "./backups");
        
        System.out.println("Default configuration loaded.");
    }
    
    public String getProperty(String key) {
        lock.readLock().lock();
        try {
            return configuration.getProperty(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public String getProperty(String key, String defaultValue) {
        lock.readLock().lock();
        try {
            return configuration.getProperty(key, defaultValue);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void setProperty(String key, String value) {
        lock.writeLock().lock();
        try {
            configuration.setProperty(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    public int getIntProperty(String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public void saveConfiguration() {
        lock.readLock().lock();
        try {
            loader.saveConfiguration(configuration);
            System.out.println("Configuration saved successfully!");
        } catch (Exception e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void printAllConfiguration() {
        lock.readLock().lock();
        try {
            System.out.println("Current Configuration:");
            configuration.forEach((key, value) -> 
                System.out.println("  " + key + " = " + value));
        } finally {
            lock.readLock().unlock();
        }
    }
}