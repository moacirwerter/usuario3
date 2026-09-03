package com.projeto3.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor // (Construtor Sem Argumentos)
@AllArgsConstructor //@AllArgsConstructor: Cria um construtor com todos os atributos
// da classe automaticamente (gerado pelo Lombok).
@Setter //@Setter: Cria automaticamente os métodos set
// para alterar os valores dos atributos
@Getter //@Getter: Cria automaticamente os métodos get
// para ler os valores dos atributos.
@Entity //: Indica ao JPA que esta classe é uma
// entidade mapeada para uma tabela do banco de dados.
@Table(name = "telefone") //@Table: Define configurações da tabela
// (como o nome físico no banco, que por padrão será "usuario
@Builder
public class Telefone {
@id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "ddd", length = 3)
    private String ddd;
}
