package org.Bondflix.service;

import io.github.cdimascio.dotenv.Dotenv;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebService
public class PaymentServ extends Service{
    @WebMethod
    public boolean processPayment(@WebParam(name="userId") int userId, @WebParam(name="paymentValue") int paymentValue) throws Exception {
        this.log(userId, paymentValue);

        if (paymentValue > 100000) {
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("SUB-API-KEY");
            URI newURI = new URI(Objects.requireNonNull(dotenv.get("UPDATE_SUBSCRIPTION_ROUTE")));

            String formData = "user_id=" + URLEncoder.encode(String.valueOf(userId), StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(newURI)
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        }
        return false;
    }
}
