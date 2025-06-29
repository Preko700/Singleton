package preko.singleton;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Configuration Manager Application ===");
        System.out.println("Initializing...");
        
        // Initialize the configuration manager (Singleton)
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewCurrentConfiguration();
                    break;
                case 2:
                    changeConfiguration();
                    break;
                case 3:
                    showWelcomeScreen();
                    break;
                case 4:
                    runConnectionSimulator();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. View current configuration");
        System.out.println("2. Change configuration");
        System.out.println("3. Go to welcome panel");
        System.out.println("4. Execute connection simulator");
        System.out.println("5. Exit system");
        System.out.print("Select an option: ");
    }
    
    private static void viewCurrentConfiguration() {
        System.out.println("\n=== CURRENT CONFIGURATION ===");
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.printAllConfiguration();
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private static void changeConfiguration() {
        System.out.println("\n=== CHANGE CONFIGURATION ===");
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        System.out.println("Available parameters:");
        System.out.println("1. defaultCurrency");
        System.out.println("2. timeFormat");
        System.out.println("3. maxConnections");
        System.out.println("4. language");
        System.out.println("5. autoSaveInterval");
        System.out.println("6. enableLogs");
        System.out.println("7. theme");
        System.out.println("8. region");
        System.out.println("9. backupEnabled");
        System.out.println("10. backupDirectory");
        
        System.out.print("Enter parameter name to change: ");
        String parameter = scanner.nextLine();
        System.out.print("Enter new value: ");
        String value = scanner.nextLine();
        
        config.setProperty(parameter, value);
        config.saveConfiguration();
        System.out.println("Configuration updated successfully!");
    }
    
    private static void showWelcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.display();
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private static void runConnectionSimulator() {
        ConnectionSimulator simulator = new ConnectionSimulator();
        simulator.simulate();
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private static int getIntInput() {
        while (true) {
            try {
                int result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}