package org.Bondflix.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;

public class PaymentServ extends Service{
    @WebMethod
    public boolean processPayment(@WebParam(name="userId") int userId, @WebParam(name="paymentValue") int paymentValue) throws Exception {
        this.log(userId);
        // Imagine ini suatu validasi pembayaran berhasil melalui payment gateway SOAP ini awkoawk
        return paymentValue > 100000;
    }
}
