package org.Bondflix.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.xml.ws.Endpoint;
import org.Bondflix.database.DatabaseManager;
import org.Bondflix.service.Calculator;
import org.Bondflix.service.Service;
import org.Bondflix.service.ServiceRegistry;

import java.util.Map;

//TODO:more config stuff to run the program
public class Bootstrap {
    private ServiceRegistry serviceRegistry;
    private Bootstrap() {
        configureDatabase();
        configureServices();
        configureEndpoint();
    }
    private static class BootstrapHolder {
        private static final Bootstrap INSTANCE = new Bootstrap();
    }

    public static Bootstrap getInstance() {
        return BootstrapHolder.INSTANCE;
    }
    private void configureDatabase() {
        try {
            Dotenv dotenv = Dotenv.load();
            String dbURL = dotenv.get("DB_URL_DEV");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASS");

            DatabaseManager.getInstance(dbURL, dbUser, dbPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO: Implement base route name http://localhost:8080/ + nama service
    private void configureServices(){
        try {
            serviceRegistry = new ServiceRegistry();
            serviceRegistry.registerService("calculator", new Calculator(), "http://localhost:8080/hello");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void configureEndpoint(){
        for (Map.Entry<String, Service> entry : serviceRegistry.getRegisteredServices().entrySet()) {
            String serviceName = entry.getKey();
            String address = serviceRegistry.getServiceAddress(serviceName);
            Service service = entry.getValue();

            Endpoint.publish(address, service);

            System.out.println("JAX-WS endpoint registered at: " + address);
        }
    }
}
