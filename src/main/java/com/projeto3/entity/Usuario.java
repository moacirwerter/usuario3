package com.projeto3.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Ele usa o @Data, que junta os Getters,
// Setters e Construtores em uma única palavra
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
@Table(name = "usuario")
//@Table: Define configurações da tabela
// (como o nome físico no banco, que por padrão será "usuario

@Builder
public class Usuario implements UserDetails {
    @Id //@Id: Define o atributo id como a chave primária da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // I@GeneratedValue(strategy = GenerationType.IDENTITY) avisa ao Hibernate
    // e ao Java que o próprio banco de dados vai gerar e incrementar o ID de forma
    // automática toda
    // vez que um novo registro for salvo
    //----
    private Long id;
    @Column(name ="nome",length = 100) //@Column(name = "nome", length = 100):
    // Mapeia o atributo nome para a coluna correspondente no banco,
    // limitando seu tamanho máximo a 100 caracteres.
    private String nome;
    @Column(name ="email",length = 100)
    private String email;
    @Column(name ="senha",length = 100)
    private String senha;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id",referencedColumnName = "id")
    private List<Endereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id" , referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return "";
    }

    // @JoinColumn Ela é utilizada sempre que você cria relações do
    // tipo @OneToOne (Um para Um) ou @ManyToOne (Muitos para Um).
    //Nomeia a coluna: Ela diz explicitamente ao banco de dados
    // qual será o nome da coluna que guardará
    // a referência da outra tabela (ex: usuario_id).
    //Define restrições: Permite configurar se o campo pode ser nulo (nullable = false)
    // ou se deve ser único (unique = true)
    //  @JoinColumn(name = "usuario_id",referencedColumnName = "id") nomE da colunA
//----
}
//@OneMany um usuário para muitos endereços, ou seja, um para muitos.
//@OneToOne um usuario para um endereço só, ou seja, um para um.
//-------------
//private String nome;, email;,
//senha;: São as propriedades do usuário que se transformarão em colunas na tabela.
//------
//@NoArgsConstructor (Construtor Sem Argumentos)
// Cria um construtor vazio, ou seja, sem nenhum parâmetro.
// Ele é obrigatório para a maioria dos frameworks
// (como o Hibernate/JPA que gerencia o banco de dados)
// para que eles consigam criar uma instância
// vazia da classe e preenchê-la depois.
//----
//@AllArgsConstructor (Construtor Com Todos os Argumentos)Cria um construtor
// que recebe todos os atributos da classe como parâmetros.
// Isso ajuda muito na hora de criar um usuário novo rapidamente
//em apenas uma linha de código.

