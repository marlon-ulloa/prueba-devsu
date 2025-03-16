package com.devsu.msclient.messaging;

import com.devsu.msclient.entity.Client;
import com.devsu.msclient.exception.ResourceNotFoundException;
import com.devsu.msclient.service.ServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * CLase que se encuentra a la escucha de os mensajes en el topic de client-reques de kafka
 * para saber cuando enviar la informacion del cliente a kafka para el microservicio de cuentas
 */
@Service
public class AsyncService {
    @Autowired
    private ServiceClient serviceClient;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Metodo que escucha los eventos del topic de kafka
     * @param clienteId
     * @throws Exception
     */
    @KafkaListener(topics = "client-request", groupId = "devsu")
    public void consumeClienteRequest(String clienteId) throws Exception {
        // Obtener datos del cliente desde la base de datos
        try {
            Client cliente = serviceClient.getClientByIdentification(clienteId);
            if (cliente == null) {
                throw new ResourceNotFoundException("Cliente no encontrado: " + clienteId);
            }

            // Serializar el cliente a JSON
            String clienteJson = objectMapper.writeValueAsString(cliente);

            // Enviar respuesta a través de Kafka
            kafkaTemplate.send("client-response", clienteJson);
        }catch(ResourceNotFoundException ex){
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            error.put("identificacion", clienteId);
            String errorResponse = objectMapper.writeValueAsString(error);
            kafkaTemplate.send("client-response", errorResponse);
        }
    }
}
