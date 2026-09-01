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
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero",length = 10)
    private String numero;
    @Column(name ="ddd", length = 3)
    private String ddd;
}
//A linha @GeneratedValue(strategy = GenerationType.IDENTITY) diz
// ao banco de dados para gerar a chave primária (ID) automaticamente
// de forma sequencial (1, 2, 3...)
// toda vez que um novo registro for salvo.
//------
//Existe uma diferença crucial entre Long (objeto) e long
// (tipo primitivo com "l" minúsculo) em Java:long
// (primitivo): Não pode ser nulo. Se você não definir um valor,
// ele assume o padrão 0.
//Long (objeto wrapper): Pode receber o valor null.
