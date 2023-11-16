package org.Bondflix.service;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private final Map<String, Service> services = new HashMap<>();
    private final Map<String, String> serviceAddresses = new HashMap<>();

    public void registerService(String serviceName, Service service) {
        services.put(serviceName, service);
        String serviceAddress;
        if (isDevEnvironment()){
            serviceAddress = "http://localhost:8081/" + serviceName;
        } else {
            Dotenv dotenv = Dotenv.load();
            String serverHost = dotenv.get("SERVER_HOST");
            String serverPort = dotenv.get("SERVER_PORT");
            serviceAddress = "http://" + serverHost + ":" + serverPort +"/" + serviceName;
        }

        serviceAddresses.put(serviceName, serviceAddress);
    }
    public String getServiceAddress(String serviceName) {
        return serviceAddresses.get(serviceName);
    }

    public Map<String, Service> getRegisteredServices(){
        return services;
    }

    public boolean isDevEnvironment() {
        Dotenv dotenv = Dotenv.load();
        String environment = dotenv.get("ENVIRONMENT");
        assert environment != null;
        return environment.equals("DEV");
    }
}
