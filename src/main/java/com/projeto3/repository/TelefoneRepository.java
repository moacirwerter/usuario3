package com.projeto3.repository;

//import com.projeto1.aprendendospring.infrastructure.entity.Telefone;
import com.projeto3.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
