package org.Bondflix.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;

@WebService
public class Calculator {

    @WebMethod
    public double add(@WebParam(name ="arg1") double a, @WebParam(name = "arg2") double b){
        return a + b;
    }
}
