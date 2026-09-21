package com.projeto3.business.converter;

import com.projeto3.business.dto.EnderecoDTO;
import com.projeto3.business.dto.TelefoneDTO;
import com.projeto3.business.dto.usuarioDTO; // Mantido com 'u' minúsculo conforme seu pacote
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

    // Transforma Entidade (Usuario) para DTO (usuarioDTO)
    public usuarioDTO paraUsuarioDTO(Usuario usuario) {
        if (usuario == null) return null;
        return usuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        if (enderecos == null) return new ArrayList<>();
        List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecoDTOs.add(paraEnderecoDTO(endereco));
        }
        return enderecoDTOs;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;
        return EnderecoDTO.builder()
                .id(endereco.getId())
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

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        if (telefone == null) return null;
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    // NOVO MÉTODO ATUALIZAR: Corrigido com as chaves corretas e tipo 'usuarioDTO'
    public Usuario updateUsuario(usuarioDTO usuarioDTO, Usuario entity) {
        if (usuarioDTO == null) return entity;
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }
    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua()!= null ? dto.getRua(): entity.getRua())
                                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() !=null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() !=null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .build();

    }
    public  Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd()!= null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                        .build();
    }
}
