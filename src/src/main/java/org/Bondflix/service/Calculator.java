package org.Bondflix.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class Calculator {

    @WebMethod
    public double add(double a, double b){
        return a + b;
    }
}
