package com.devsu.msclient.service;

import com.devsu.msclient.dto.ClientDTO;
import com.devsu.msclient.entity.Client;
import com.devsu.msclient.exception.ResourceNotFoundException;
import com.devsu.msclient.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceClientTest {
    private ClientRepository clientRepository;
    private ServiceClient serviceClient;

    @BeforeEach
    public void setup() {
        clientRepository = Mockito.mock(ClientRepository.class);
        serviceClient = new ServiceClient(clientRepository);
    }

    @Test
    public void testCreateClient() {
        Client client = new Client();
        client.setId(1);
        client.setIdentificacion("123456780");
        client.setNombre("Prueba Integracion");
        client.setGenero("Masculino");
        client.setEdad(50);
        client.setTelefono("2258971");
        client.setContrasena("passowrd");
        client.setDireccion("Direccion de prueba");
        client.setEstado(true);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1);
        clientDTO.setIdentificacion("123456780");
        clientDTO.setNombre("Prueba Integracion");
        clientDTO.setGenero("Masculino");
        clientDTO.setEdad(50);
        clientDTO.setTelefono("2258971");
        clientDTO.setContrasena("passowrd");
        clientDTO.setDireccion("Direccion de prueba");
        clientDTO.setEstado(true);

        ClientDTO created = serviceClient.createClient(clientDTO);
        assertEquals(1, created.getId());

        // Capturar el objeto que realmente se guarda en el repositorio
        ArgumentCaptor<Client> accountCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository, times(1)).save(accountCaptor.capture());

        Client savedClient = accountCaptor.getValue();
        assertEquals(1L, savedClient.getId());

    }

    @Test
    public void testGetClientById_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceClient.getClientById(1L);
        });
    }
}