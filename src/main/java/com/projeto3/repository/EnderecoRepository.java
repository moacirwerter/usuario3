package com.projeto3.repository;

import com.projeto1.aprendendospring.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository  extends JpaRepository<Endereco,Long> {

}

//public class EnderecoRepository  extends JpaRepository<Endereco,Long> {
//public interface EnderecoRepository  extends JpaRepository<Endereco,Long> {

//A principal diferença é que uma classe define o comportamento (como fazer), enquanto
// uma interface define um contrato (o que deve ser feito).
//No ecossistema do Spring Data JPA, você deve usar obrigatoriamente public
// interface para o seu repositório
//-----

//O Repository é uma interface responsável por fazer a comunicação direta com o banco de dados.Ele funciona como um intermediário: você conversa com ele usando código Java simples e ele
//traduz tudo para os comandos SQL que o banco de dados entende.