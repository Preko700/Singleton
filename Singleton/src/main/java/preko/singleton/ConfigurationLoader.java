package preko.singleton;

import java.io.*;
import java.util.Properties;

public class ConfigurationLoader {
    private static final String CONFIG_FILE = "config.properties";
    
    public Properties loadConfiguration() throws IOException {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            }
        } else {
            System.out.println("Configuration file not found. Creating with default values.");
            properties = createDefaultConfiguration();
            saveConfiguration(properties);
        }
        
        return properties;
    }
    
    public void saveConfiguration(Properties properties) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Configuration Manager Settings");
        }
    }
    
    private Properties createDefaultConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("defaultCurrency", "USD");
        properties.setProperty("timeFormat", "24H");
        properties.setProperty("maxConnections", "10");
        properties.setProperty("language", "EN");
        properties.setProperty("autoSaveInterval", "5");
        properties.setProperty("enableLogs", "true");
        properties.setProperty("theme", "light");
        properties.setProperty("region", "LATAM");
        properties.setProperty("backupEnabled", "true");
        properties.setProperty("backupDirectory", "./backups");
        return properties;
    }
}