package org.Bondflix.service;

import com.sun.net.httpserver.HttpExchange;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.Bondflix.model.ApiKey;
import org.Bondflix.model.Log;
import org.Bondflix.repository.ApiKeyRepository;
import org.Bondflix.repository.LogRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Service {

    @Resource
    WebServiceContext ctx;

    protected void logClient(String endpoint, String bodyRequest, String ip) throws Exception {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String ts_string = ts.toString().split("\\.")[0];
        String client = this.getClient();
        Log log = new Log(bodyRequest, ip, endpoint, ts_string, client);
        LogRepository.getInstance().create(log);
    }

    private String getIP() {
        MessageContext mc = ctx.getMessageContext();
        String httpExchangeKey = "com.sun.xml.ws.http.exchange";
        HttpExchange httpExchange = (HttpExchange) mc.get(httpExchangeKey);

        return httpExchange.getRemoteAddress().toString().replace("/", "");
    }

    private String getClient() throws Exception {
        MessageContext mc = ctx.getMessageContext();

        Map<String, Object> requestHeader = (Map<String, Object>) mc.get(mc.HTTP_REQUEST_HEADERS);
        String apiKey = ((List<String>) requestHeader.get("api-key")).get(0);

        List<ApiKey> validApiKeys = ApiKeyRepository.getInstance().findAll();
        for (ApiKey validApiKey : validApiKeys) {
            if (validApiKey.getKey().equals(apiKey)) {
                return validApiKey.getClient();
            }
        }
        throw new Exception("Key is not registered");
    }

    protected void log(Object ...params) throws Exception {
        var ptrTrace = Thread.currentThread().getStackTrace()[2];
        String endpoint = ptrTrace.getClassName() + "." + ptrTrace.getMethodName();
        this.logClient(endpoint, Arrays.toString(params), this.getIP());
    }

}
