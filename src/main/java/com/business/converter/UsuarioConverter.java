package com.business.converter;

import com.business.dto.EnderecoDTO;
import com.business.dto.TelefoneDTO;
import com.business.dto.usuarioDTO; // Alterado para 'u' minúsculo
import com.projeto3.entity.Endereco;
import com.projeto3.entity.Telefone;
import com.projeto3.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(usuarioDTO usuarioDTO) { // Alterado o tipo para usuarioDTO
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOs) {
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
        return  telefoneDTOs.stream()
                .map(this::paraTelefone)
                .toList();

    }
    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()

                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(usuario usuarioDTO) { // Alterado o tipo para usuarioDTO
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;

    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO) {
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
    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOs) {
        return  telefoneDTOs.stream()
                .map(this::paraTelefoneDTO)
                .toList();

    }
    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
        return Telefone.builder()

                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
}


