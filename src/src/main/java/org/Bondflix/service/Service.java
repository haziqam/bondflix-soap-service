package org.Bondflix.service;

import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.spi.http.HttpExchange;
import org.Bondflix.model.Log;
import org.Bondflix.repository.LogRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public abstract class Service {

    @Resource
    WebServiceContext ctx;

    protected void logClient(String endpoint, String bodyRequest, String ip) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String ts_string = ts.toString().split("\\.")[0];
        String client = this.getClient();

        Log log = new Log(bodyRequest, ip, endpoint, ts_string, client);
        LogRepository.getInstance().create(log);
    }

    //TODO:Find this httpExchangeKey
    private String getRemoteAddr() {
        MessageContext mc = ctx.getMessageContext();
        String httpExchangeKey = "jakarta.xml.ws.http.exchange";
        HttpExchange httpExchange = (HttpExchange) mc.get(httpExchangeKey);

        return "test";
    }

    //TODO:Implement this
    private String getClient() {
        return "test";
    }

    protected void validateAndRecord(Object ...params) throws Exception {
        var ptrTrace = Thread.currentThread().getStackTrace()[2];
        String endpoint = ptrTrace.getClassName() + "." + ptrTrace.getMethodName();
        this.logClient(endpoint, Arrays.toString(params), this.getRemoteAddr());
    }

}
