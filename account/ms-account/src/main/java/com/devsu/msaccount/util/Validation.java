package com.devsu.msaccount.util;

import com.devsu.msaccount.exception.ResourceNotFoundException;
import com.devsu.msaccount.messaging.AsyncConsumerService;
import com.devsu.msaccount.messaging.AsyncProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Clase que se encarga de realizar las validaciones necesarias
 */
@Component
public class Validation {


    @Autowired
    private AsyncProducerService asyncProducerService;
    @Autowired
    private AsyncConsumerService asyncConsumerService;
    public Validation(){}

    public Long validateClientExists(String identification){
        asyncProducerService.sendClienteRequest(identification);
        Map<String, Object> client = asyncConsumerService.obtenerRespuesta(identification);
        if (client.containsKey("error")) {
            throw new ResourceNotFoundException(client.get("error").toString());
        }

        String clientId= client.get("id").toString();
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        return Long.parseLong(clientId);
    }
}
