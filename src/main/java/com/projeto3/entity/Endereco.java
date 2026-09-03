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
@Table(name = "endereco") //@Table: Define configurações da tabela
@Builder

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento", length = 50)
    private String complemento;
    @Column(name = "cidade", length = 150)
    private String cidade;
    @Column(name =  "estado",length = 2)
    private String estado;
    @Column(name = "cep", length = 14)
    private String cep;
    //@OneToMany(cascade =  CascadeType.ALL)
    //@JoinColumn(name = "usuario_id" , referencedColumnName = "id")
    //private List<Telefone> telefones;
    //A linha @GeneratedValue(strategy = GenerationType.IDENTITY)
    // define que o banco de dados será o único responsável por gerar e
    // incrementar o valor da chave primária (ID) de forma automática.
}
