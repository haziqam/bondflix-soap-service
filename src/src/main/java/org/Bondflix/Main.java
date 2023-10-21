package org.Bondflix;

import jakarta.xml.ws.Endpoint;
import org.Bondflix.service.Calculator;

public class Main {
    public static void main(String[] args) {
        String address = "http://localhost:8080/hello";
        Endpoint.publish(address, new Calculator());
    }
}