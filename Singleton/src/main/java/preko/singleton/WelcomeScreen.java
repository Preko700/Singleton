package preko.singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WelcomeScreen {
    private ConfigurationManager config;
    
    public WelcomeScreen() {
        this.config = ConfigurationManager.getInstance();
    }
    
    public void display() {
        System.out.println("\n" + getThemeDecoration());
        System.out.println(getWelcomeMessage());
        System.out.println("Current Time: " + getCurrentTime());
        System.out.println("Theme: " + config.getProperty("theme"));
        System.out.println("Region: " + config.getProperty("region"));
        System.out.println(getThemeDecoration());
    }
    
    private String getWelcomeMessage() {
        String language = config.getProperty("language", "EN");
        
        switch (language.toUpperCase()) {
            case "ES":
                return "¡Bienvenido al Gestor de Configuración!";
            case "EN":
            default:
                return "Welcome to Configuration Manager!";
        }
    }
    
    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        String timeFormat = config.getProperty("timeFormat", "24H");
        
        if ("AM/PM".equalsIgnoreCase(timeFormat)) {
            return now.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        } else {
            return now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }
    
    private String getThemeDecoration() {
        String theme = config.getProperty("theme", "light");
        
        if ("dark".equalsIgnoreCase(theme)) {
            return "████████████████████████████████████████";
        } else {
            return "========================================";
        }
    }
}