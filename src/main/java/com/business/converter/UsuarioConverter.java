package com.business.converter;

import com.business.dto.EnderecoDTO;
import com.business.dto.TelefoneDTO;
import com.business.dto.usuarioDTO; // Mantido conforme seu pacote físico, mas ideal é usar U maiúsculo no arquivo original
import com.projeto3.entity.Endereco;
import com.projeto3.entity.Telefone;
import com.projeto3.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    // Transforma DTO para Entidade
    public Usuario paraUsuario(usuarioDTO usuarioDTO) {
        if (usuarioDTO == null) return null;
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOs) {
        if (enderecoDTOs == null) return new ArrayList<>();
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecoDTOs) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) return null;
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOs) {
        if (telefoneDTOs == null) return new ArrayList<>();
        return telefoneDTOs.stream()
                .map(this::paraTelefone)
                .toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        if (telefoneDTO == null) return null;
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // CORRIGIDO: Transforma Entidade (Usuario) para DTO (usuarioDTO)
    public usuarioDTO paraUsuarioDTO(Usuario usuario) {
        if (usuario == null) return null;
        return usuarioDTO.builder() // Mudado para chamar o builder do DTO correto
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    // CORRIGIDO: Tipagem corrigida para EnderecoDTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        if (enderecos == null) return new ArrayList<>();
        List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecoDTOs.add(paraEnderecoDTO(endereco));
        }
        return enderecoDTOs;
    }

    // CORRIGIDO: Estava construindo Endereco.builder() em vez de EnderecoDTO.builder()
    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;
        return EnderecoDTO.builder() // Mudado para construir o DTO
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefones) {
        if (telefones == null) return new ArrayList<>();
        return telefones.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    // CORRIGIDO: Estava construindo Telefone.builder() em vez de TelefoneDTO.builder()
    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        if (telefone == null) return null;
        return TelefoneDTO.builder() // Mudado para construir o DTO
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }
}



