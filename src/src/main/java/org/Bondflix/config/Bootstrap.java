package org.Bondflix.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.xml.ws.Endpoint;
import org.Bondflix.database.DatabaseManager;
import org.Bondflix.service.Subscription;
import org.Bondflix.service.Service;
import org.Bondflix.service.ServiceRegistry;

import java.sql.Connection;
import java.util.Map;

//TODO:more config stuff to run the program
public class Bootstrap {
    private ServiceRegistry serviceRegistry;
    private Bootstrap() {
        configureDatabase();
        configureServices();
        configureEndpoints();
    }
    private static class BootstrapHolder {
        private static final Bootstrap INSTANCE = new Bootstrap();
    }

    public static Bootstrap getInstance() {
        return BootstrapHolder.INSTANCE;
    }
    private void configureDatabase() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO: Implement base route name http://localhost:8080/ + name service
    private void configureServices(){
        try {
            serviceRegistry = new ServiceRegistry();
            serviceRegistry.registerService("calculator", new Subscription(), "http://localhost:8080/hello");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void configureEndpoints(){
        for (Map.Entry<String, Service> entry : serviceRegistry.getRegisteredServices().entrySet()) {
            String serviceName = entry.getKey();
            String address = serviceRegistry.getServiceAddress(serviceName);
            Service service = entry.getValue();

            Endpoint.publish(address, service);

            System.out.println("JAX-WS endpoint registered at: " + address);
        }
    }
}
