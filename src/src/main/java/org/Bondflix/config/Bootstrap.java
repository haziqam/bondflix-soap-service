package org.Bondflix.config;

import jakarta.xml.ws.Endpoint;
import org.Bondflix.database.DatabaseManager;
import org.Bondflix.service.Service;
import org.Bondflix.service.ServiceRegistry;
import org.Bondflix.service.ShortsSubscriptionServ;

import java.util.Map;

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
            DatabaseManager.getInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void configureServices(){
        try {
            serviceRegistry = new ServiceRegistry();
            serviceRegistry.registerService("shorts_subscription", new ShortsSubscriptionServ());
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
