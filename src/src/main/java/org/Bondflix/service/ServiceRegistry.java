package org.Bondflix.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private final Map<String, Service> services = new HashMap<>();
    private final Map<String, String> serviceAddresses = new HashMap<>();

    public void registerService(String serviceName, Service service, String serviceAddress) {
        services.put(serviceName, service);
        serviceAddresses.put(serviceName, serviceAddress);
    }

    public Service getService(String serviceName) {
        return services.get(serviceName);
    }

    public String getServiceAddress(String serviceName) {
        return serviceAddresses.get(serviceName);
    }

    public Map<String, Service> getRegisteredServices(){
        return services;
    }
}
