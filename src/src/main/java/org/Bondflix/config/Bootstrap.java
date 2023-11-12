package org.Bondflix.config;


import org.Bondflix.database.DatabaseManager;
import org.Bondflix.service.PaymentServ;
import org.Bondflix.service.Service;
import org.Bondflix.service.ServiceRegistry;
import org.Bondflix.service.ShortsSubscriptionServ;
import org.Bondflix.utils.SendEmail;

import javax.mail.MessagingException;
import javax.xml.ws.Endpoint;
import java.util.Map;

public class Bootstrap {
    private ServiceRegistry serviceRegistry;
    private Bootstrap() {

        try {
            SendEmail newSandEmail = new SendEmail();
            newSandEmail.sendEmail("nicholasliem01@gmail.com", "hehehe", "hehe");
            System.out.println("Success");
        } catch (MessagingException e) {
            System.out.println("Error" + e.getMessage());
        }
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
            serviceRegistry.registerService("payment_gateway", new PaymentServ());
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
