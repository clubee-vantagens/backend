package com.clubeevantagens.authmicroservice.app.repository;
import com.clubeevantagens.authmicroservice.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    boolean existsByUserEmail(String email);
    Optional<Client> findByUserEmail(String email);

    boolean existsByCpf(String cpf);
}
