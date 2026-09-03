package com.business;

import com.business.converter.UsuarioConverter;
import com.business.dto.usuarioDTO;
import com.projeto3.entity.Usuario; // ESTE IMPORT DA ENTIDADE É OBRIGATÓRIO
import com.projeto3.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public usuarioDTO salvaUsuario(usuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioConverter.paraUsuarioDTO(usuarioSalvo);
    }
}
