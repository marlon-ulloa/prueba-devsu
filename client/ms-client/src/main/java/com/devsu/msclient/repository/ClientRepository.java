package com.devsu.msclient.repository;

import com.devsu.msclient.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByIdentificacion(String identificacion);
}
