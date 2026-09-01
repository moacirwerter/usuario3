package com.projeto3.repository;


import com.projeto3.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
    //optional, tem a funcao de evitar o retorno de informações nulas.


    @Transactional
    void deleteByEmail(String email);

}


//extends JpaRepository: É aqui que a mágica acontece. Ao estender o JpaRepository,
// seu repositório herda automaticamente dezenas
// de métodos prontos para manipulação de dados (CRUD)

    // preciso desse comando, pois atraves dele, eu informo que 2 usuários
    // não pode se cadastrar usando o mesmo e-mail
    //O Long na declaração JpaRepository<Usuario, Long> define o tipo de dado do
    // identificador único (ID)
    // da entidade Usuario.
    //O boolean define o tipo de retorno do método existsByEmail.
    // Ele indica que o Spring Data JPA deve responder à
    // consulta apenas com verdadeiro (true) ou falso (false).
    //boolean (O Retorno): É a resposta que você vai receber.
    // Só existem duas opções: true (verdadeiro,
    // o e-mail já existe) ou false (falso, o e-mail não existe).
    //existsByEmail (A Pergunta): É o nome que o Spring Boot usa para
    // entender o que fazer. Ele lê isso como:
    // "Verifique se existe (exists) um registro pelo (By) campo Email (Email)".
    //String email) (O Dado): É o e-mail de texto que você vai passar para ele
    // testar (ex: "teste@email.com").



